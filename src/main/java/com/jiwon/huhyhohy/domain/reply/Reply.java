package com.jiwon.huhyhohy.domain.reply;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.web.dto.reply.ReplyUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Lob
  private String comment;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "board_id")
  private Board board;

  public void update(ReplyUpdateRequestDto updateRequestDto) {
    this.comment = updateRequestDto.getComment();
  }

  public void setUser(User user){
    this.user = user;
  }
  // 연관관계 편의 메서드
  public void setBoard(Board board){
    if(this.board != null) {
      this.board.getReplies().remove(this);
    }
    this.board = board;
    this.board.getReplies().add(this);
  }
}
