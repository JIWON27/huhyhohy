package com.jiwon.huhyhohy.web.dto;

import com.jiwon.huhyhohy.domain.Enrollment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnrollmentResponseDto {
  private Long enrollmentId;
  private Long userId; // userId -> 크루 참가 수락 경로에 쓰기 위해서
  private String username; // user 닉네임

  public EnrollmentResponseDto(Enrollment enrollment){
    this.enrollmentId = enrollment.getId();
    this.userId = enrollment.getUser().getId();
    this.username = enrollment.getUser().getNickname();
  }
}
