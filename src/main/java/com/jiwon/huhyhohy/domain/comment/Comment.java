package com.jiwon.huhyhohy.domain.comment;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.DeleteStatus;
import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.web.dto.comment.CommentUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE comment SET delete_status = 'DELETED' WHERE id = ?")
public class Comment extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Lob
  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "board_id")
  private Board board;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private DeleteStatus deleteStatus = DeleteStatus.ACTIVE;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Comment parent;

  @OneToMany(mappedBy = "parent", orphanRemoval = true)
  private List<Comment> replies = new ArrayList<>();

  public void setParent(Comment parent){
    this.parent = parent;
  }

  public void update(CommentUpdateRequestDto updateRequestDto) {
    this.content = updateRequestDto.getContent();
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
