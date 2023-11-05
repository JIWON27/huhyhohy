package com.jiwon.huhyhohy.web.dto.meeting;

import com.jiwon.huhyhohy.domain.crew.Meeting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingSaveRequestDto {
  public String title; // 제목
  public String intro; // 소개

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  public LocalDateTime date; // 만남일
  public int capacity; // 참여 가능 인원수
  public String location; // 위치

  public Meeting toEntity() {
    return Meeting.builder()
        .title(title)
        .intro(intro)
        .date(date)
        .capacity(capacity)
        .location(location)
        .build();
  }
}
