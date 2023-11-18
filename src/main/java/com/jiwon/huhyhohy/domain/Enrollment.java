package com.jiwon.huhyhohy.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment extends BaseTimeEntity { // 대기 리스트
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "crew_id")
  private Crew crew;


  @Builder
  public Enrollment(User user, Crew crew) {
    this.user = user;
    this.crew = crew;
  }

}
