package com.jiwon.huhyhohy.domain.tag;

import com.jiwon.huhyhohy.domain.user.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Entity @Builder
public class UserTag {
  // user - UserTag - tag
  // User는 관심 주제를 Tag로 설정해서 알림을 받을 수 있도록함.
  // 그래서 user와 tag는 다대다 관계여서 일대다, 다대일 관계로 풀어주기 위한 중간 테이블 생성
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name="tag_id")
  private Tag tag;
}
