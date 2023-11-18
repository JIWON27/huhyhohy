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
import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Reply parent;

  // 이 변수명을 좀 검색해봤는데
  // 댓글(comment)는 주로 게시된 글에 대한 답변이라는 의미로 사용되고
  // 답글(reply)은 댓글에 대한 답변이라는 의미로 사용된다고 하네요
  // 변수명의 변경이 조금 필요할 것 같아요
  @OneToMany(mappedBy = "parent", orphanRemoval = true)
  private List<Reply> children = new ArrayList<>();

  public void update(ReplyUpdateRequestDto updateRequestDto) {
    this.comment = updateRequestDto.getComment();
  }

  public void setUser(User user){
    this.user = user;
  }

  public void updateParent(Reply parent){
    this.parent = parent;
    this.parent.getChildren().add(this);
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
