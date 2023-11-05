package com.jiwon.huhyhohy.domain.file;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.board.Board;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 이거 추가해주니까 되네... 이유가..
public class ImgFile extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originFilename; // 업로드된 파일 이름
  private String storeFilename; // 저장한 파일명 ex) 132-5sdf-23451-1as.png -> UUID로 식별자 생성 + 확장자

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @Builder
  public ImgFile(String originFilename, String storeFilename) {
    this.originFilename = originFilename;
    this.storeFilename = storeFilename;
  }

  public void setBoard(Board board) {
    this.board = board;
  }
}