package com.jiwon.huhyhohy.domain.tag;

import com.jiwon.huhyhohy.domain.crew.Crew;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Entity @Builder
public class CrewTag {
  // Crew-CrewTag-tag , 다대다 관계를 일대다 다대일 관계로 풀어내기 위한 중간 테이블
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "crew_id")
  private Crew crew;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="tag_id")
  private Tag tag;
}
