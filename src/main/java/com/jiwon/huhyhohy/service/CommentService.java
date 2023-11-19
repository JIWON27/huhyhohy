package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.comment.Comment;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.CustomCommentRepository;
import com.jiwon.huhyhohy.repository.CommentRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentDeleteRequestDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final CustomCommentRepository customCommentRepository;
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  private final EntityManager em;

  public void save(Long boardId, CommentSaveRequestDto commentSaveRequestDto){
    User user = userRepository.findUserByUserId(commentSaveRequestDto.getWriter()).orElseThrow(
            () -> new RuntimeException("해당 유저가 없습니다."));
//            IllegalArgumentException::new);
    Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new RuntimeException("해당 게시글이 없습니다."));

    Comment comment = commentSaveRequestDto.toEntity();
    comment.setBoard(board);
    comment.setUser(user);

    Comment parent;
    if (commentSaveRequestDto.getParentId() != null) {
      parent = commentRepository.findById(commentSaveRequestDto.getParentId()).
              orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다.")); // 맞는 에러 처리?
      comment.updateParent(parent);
    } else {
      comment.setParent(null);
    }
    commentRepository.save(comment);
  }

  // 댓글 조회 (전체) , 댓글 단건 조회는 딱히,, "내가 쓴 댓글 보기"에서 사용할 메서드
  @Transactional(readOnly = true)
  public List<CommentResponseDto> findByBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

    List<CommentResponseDto> replies = customCommentRepository.findByBoard(board);

    return replies;
  }

  // 댓글 삭제
  @Transactional
  public Long delete(Long id, CommentDeleteRequestDto deleteRequestDto) {
    Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    // deleteRequestDto에서 유저를 꺼내 코멘트 작성자와 동일한지 검증 로직 추가 필요

    commentRepository.delete(comment);
    return comment.getBoard().getId();
  }

  // 댓글 수정
  @Transactional
  public void update(Long id, CommentUpdateRequestDto updateRequestDto){
    Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    // updateRequestDto에서 유저를 꺼내 코멘트 작성자와 동일한지 검증 로직 추가 필요

    comment.update(updateRequestDto);
  }

  //내가 쓴 댓글 보기 - 내가 댓글을 쓴 게시물 보여주기
  public List<BoardResponseDto> findMyReplies(String userId){
    User user = userRepository.findUserByUserId(userId).orElseThrow(IllegalArgumentException::new);
    List<Comment> myReplies = commentRepository.findByUser(user);

    List<Board> myBoards = myReplies.stream()
        .map(Comment::getBoard)
        .collect(Collectors.toList());

    // Board 엔티티를 BoardResponseDto로 변환
    List<BoardResponseDto> boardResponseDto = myBoards.stream()
        .map(BoardResponseDto::new)
        .collect(Collectors.toList());

    return boardResponseDto;
  }
}
