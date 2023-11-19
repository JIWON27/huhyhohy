package com.jiwon.huhyhohy.domain.reply;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.web.dto.reply.CommentUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {
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
  private Comment parent;

  @OneToMany(mappedBy = "parent", orphanRemoval = true)
  private List<Comment> replies = new ArrayList<>();

  public void update(CommentUpdateRequestDto updateRequestDto) {
    this.comment = updateRequestDto.getComment();
  }

  public void setUser(User user){
    this.user = user;
  }

  public void updateParent(Comment parent){
    this.parent = parent;
    this.parent.getReplies().add(this);
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
