package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.Enrollment;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.EnrollmentRepository;
import com.jiwon.huhyhohy.web.dto.EnrollmentResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
  private final EnrollmentRepository enrollmentRepository;

  // 크루 가입 신청
  public void enroll(User user, Crew crew){
    Enrollment enrollment = Enrollment.builder()
        .user(user)
        .crew(crew)
        .build();
    enrollmentRepository.save(enrollment);
  }

  // 크루장이 가입 신청 수락버튼을 누르면 -> 크루장만 보이는 버튼이 있을꺼라 크루장인지 검증하는 로직은 따로 구현하지 않음.
  public void acceptEnrollment(Long enrollmentId) {
    Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid enrollmentId"));
    enrollmentRepository.delete(enrollment); //-> 수락하면 없애버리기
  }

  // 크루  ACCEPT 회원들 가져오는 메서드 -> crew.getUsers()

  // 크루에 신청한 회원들(ACCEPT, WAIT 모두) 가져오는 메서드
  public List<EnrollmentResponseDto> getApplyUsers(Long crewId){
    List<Enrollment> enrollments = enrollmentRepository.findApplyUsersByCrewId(crewId);
    List<EnrollmentResponseDto> crewEnrollments = enrollments
        .stream()
        .map(EnrollmentResponseDto::new)
        .collect(Collectors.toList());

    return crewEnrollments;
  }
}
