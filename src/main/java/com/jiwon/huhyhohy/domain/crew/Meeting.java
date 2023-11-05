package com.jiwon.huhyhohy.domain.crew;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Meeting extends BaseTimeEntity {

  @Id
  @GeneratedValue
  private Long id;
  public String title; // 제목
  public String intro; // 소개

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  public LocalDateTime date; // 만남일 -> 만남일을 지나면 자동삭제 -> 어떻게 구현하지? 실시간 구현..? 아니면 모임 목록을 누를때마다 리프레시..?
  public int capacity; // 참여 가능 인원수
  public int cnt; // 참여 -> cnt + 1
  private String location;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "crew_id")
  private Crew crew;

  public void setCrew(Crew crew){
    if(this.crew != null) {
      this.crew.getMeetings().remove(this);
    }
    this.crew = crew;
    this.crew.getMeetings().add(this);
  }

  public void plusCnt(){
    cnt += 1;
  }
}
