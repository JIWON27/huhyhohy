package com.jiwon.huhyhohy.web.dto.meeting;

import com.jiwon.huhyhohy.domain.crew.Meeting;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeetingResponseDto {
  private Long id;
  private String title; // 제목
  private String intro; // 소개

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date; // 만남일
  private int capacity; // 참여 가능 인원수
  private String location; // 위치

  public MeetingResponseDto(Meeting meeting) {
    this.id = meeting.getId();
    this.title = meeting.getTitle();
    this.intro = meeting.getIntro();
    this.date = meeting.getDate();
    this.capacity = meeting.getCapacity();
    this.location = meeting.getLocation();
  }
}
