package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.reply.Reply;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.ReplyRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.reply.ReplyResponseDto;
import com.jiwon.huhyhohy.web.dto.reply.ReplySaveRequestDto;
import com.jiwon.huhyhohy.web.dto.reply.ReplyUpdateRequestDto;
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
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  private final EntityManager em;

  // 댓글 등록
  public void save(Long boardId, String nickname, ReplySaveRequestDto replySaveRequestDto){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

    Reply reply = replySaveRequestDto.toEntity();
    reply.setBoard(board);
    reply.setUser(user);
    replyRepository.save(reply);
  }
  // 댓글 조회 (전체) , 댓글 단건 조회는 딱히,, "내가 쓴 댓글 보기"에서 사용할 메서드
  // 댓글 삭제
  public Long delete(Long id){
    // 여기서 postID를 반환하게 ..
    Long boardId = em.createQuery("select r.board.id from Reply r where r.id=:id ", Long.class)
        .setParameter("id", id)
        .getSingleResult();
    replyRepository.deleteById(id);
    return boardId;
  }
  // 댓글 수정
  @Transactional
  public Long update(Long id, ReplyUpdateRequestDto updateRequestDto){
    Long boardId = em.createQuery("select r.board.id from Reply r where r.id=:id ", Long.class)
        .setParameter("id", id)
        .getSingleResult();

    Reply reply = replyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    reply.update(updateRequestDto);
    return boardId;
  }

  //내가 쓴 댓글 보기 - 내가 댓글을 쓴 게시물 보여주기
  public List<BoardResponseDto> findMyReplies(String nickname){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    List<Reply> myReplies = replyRepository.findByUser(user);

    List<Board> myBoards = myReplies.stream()
        .map(Reply::getBoard)
        .collect(Collectors.toList());

    // Board 엔티티를 BoardResponseDto로 변환
    List<BoardResponseDto> boardResponseDto = myBoards.stream()
        .map(BoardResponseDto::new)
        .collect(Collectors.toList());

    return boardResponseDto;
  }
}
