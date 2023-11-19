package com.jiwon.huhyhohy.domain.board;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.file.ImgFile;
import com.jiwon.huhyhohy.domain.comment.Comment;
import com.jiwon.huhyhohy.domain.user.User;
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
public class Board extends BaseTimeEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Lob
  private String content;

  @ManyToOne(fetch = FetchType.LAZY) // 얘도 LAZY로 바꿀까 말까.. 엔티티 그래프만 "user" 추가해주면 됨.
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 글이 삭제되면 댓글 모두 삭제
  @OrderBy("createdDate")
  private List<Comment> replies = new ArrayList<>();

  @OneToMany(mappedBy = "board",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ImgFile> imgFiles = new ArrayList<>(); // 나는 이미지 파일들만 올릴 수 있게해서 그냥 List<>만 구현

  public void setUser(User user){
    this.user = user;
  }

  public void setImgFile(List<ImgFile> imgFiles){
    this.imgFiles = imgFiles;
    for (ImgFile file : imgFiles) {
      file.setBoard(this);
    }
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

  @Builder
  public Board(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
