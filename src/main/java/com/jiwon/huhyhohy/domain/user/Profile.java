package com.jiwon.huhyhohy.domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originFilename; // 업로드된 파일 이름
  private String storeFilename; // 저장한 파일명 ex) 132-5sdf-23451-1as.png -> UUID로 식별자 생성 + 확장자

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public void setUser(User user) {
    this.user = user;
  }
}
