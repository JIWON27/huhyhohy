package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.comment.Comment;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.CustomReplyRepository;
import com.jiwon.huhyhohy.repository.ReplyRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.ReplySaveRequestDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
  private final ReplyRepository replyRepository;
  private final CustomReplyRepository customReplyRepository;
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  private final EntityManager em;

  // 댓글 등록
  // 댓글 등록 시 사용자 닉네임보다는 아이디를 사용하는 것에 대해... 어떻게 생각하세요?
  public void save(Long boardId, String nickname, ReplySaveRequestDto replySaveRequestDto){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

    Comment comment = replySaveRequestDto.toEntity();
    comment.setBoard(board);
    comment.setUser(user);

    // 수정하면서 느낀건데 ReplySaveRequestDto랑 ReplayUpdateRequestDto를 하나로 합칠 수 있을 것 같아요
    Comment parent;
    if (replySaveRequestDto.getParentId() != null) {
      parent = replyRepository.findById(replySaveRequestDto.getParentId()).
              orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다.")); // 맞는 에러 처리?
      comment.updateParent(parent);
    }
    replyRepository.save(comment);
  }

  // 댓글 조회 (전체) , 댓글 단건 조회는 딱히,, "내가 쓴 댓글 보기"에서 사용할 메서드
  @Transactional(readOnly = true)
  public List<CommentResponseDto> findByBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

    List<CommentResponseDto> replies = customReplyRepository.findByBoard(board);

    return replies;
  }

  // 댓글 삭제
  public Long delete(Long id){
    // 여기서 postID를 반환하게 ..
    Long boardId = em.createQuery("select r.board.id from Comment r where r.id=:id ", Long.class)
        .setParameter("id", id)
        .getSingleResult();
    replyRepository.deleteById(id);
    return boardId;
  }
  // 댓글 수정
  @Transactional
  public Long update(Long id, CommentUpdateRequestDto updateRequestDto){
    Long boardId = em.createQuery("select r.board.id from Comment r where r.id=:id ", Long.class)
        .setParameter("id", id)
        .getSingleResult();

    Comment comment = replyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    comment.update(updateRequestDto);
    return boardId;
  }

  //내가 쓴 댓글 보기 - 내가 댓글을 쓴 게시물 보여주기
  public List<BoardResponseDto> findMyReplies(String nickname){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    List<Comment> myReplies = replyRepository.findByUser(user);

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
