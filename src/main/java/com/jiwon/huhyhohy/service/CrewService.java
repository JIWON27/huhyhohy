package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.Like;
import com.jiwon.huhyhohy.domain.crew.Category;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.CrewRepository;
import com.jiwon.huhyhohy.repository.LikeRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.EnrollmentResponseDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.crew.PageCrewResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CrewService {
  private final CrewRepository crewRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository; // 크루 참가하면 관심있어요 삭제
  private final EnrollmentService enrollmentService;

  // 크루 저장
  public Long save(CrewSaveRequestDto crewSaveRequestDto, Long userId){
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    Crew crew = crewSaveRequestDto.toEntity();

    crew.setUser(user);
    Crew savedCrew = crewRepository.save(crew);
    return savedCrew.getId();
  }
  // 크루 전체 조회 - API
  public PageCrewResponseDto findAllApi(int pageNo, int pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<Crew> crewsPage = crewRepository.findAll(pageable);

    List<Crew> crewsContent = crewsPage.getContent();
    List<CrewResponseDto> crews = crewsContent.stream().map(CrewResponseDto::new).collect(Collectors.toList());

    return PageCrewResponseDto.builder()
        .content(crews)
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(crewsPage.getTotalElements())
        .last(crewsPage.isLast())
        .first(crewsPage.isFirst())
        .build();
  }
  // 크루 전체 조회 - API -> 약간 코드 중복이 많다. 어쩌지?
  public PageCrewResponseDto findHotCrewsAPi(int pageNo, int pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<Crew> crewsPage = crewRepository.findHotCrews(pageable);

    List<Crew> crewsContent = crewsPage.getContent();
    List<CrewResponseDto> crews = crewsContent.stream().map(CrewResponseDto::new).collect(Collectors.toList());

    return PageCrewResponseDto.builder()
        .content(crews)
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(crewsPage.getTotalElements())
        .last(crewsPage.isLast())
        .first(crewsPage.isFirst())
        .build();
  }
  // 크루 전체 조회 - API -> 약간 코드 중복이 많다. 어쩌지?
  public PageCrewResponseDto findAllByCategory(int pageNo, int pageSize, String sortBy, String category) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<Crew> crewsPage = crewRepository.findAllByCategory(Category.fromString(category), pageable);

    List<Crew> crewsContent = crewsPage.getContent();
    List<CrewResponseDto> crews = crewsContent.stream().map(CrewResponseDto::new).collect(Collectors.toList());

    return PageCrewResponseDto.builder()
        .content(crews)
        .pageNo(pageNo)
        .pageSize(pageSize)
        .totalElements(crewsPage.getTotalElements())
        .last(crewsPage.isLast())
        .first(crewsPage.isFirst())
        .build();
  }
  // 크루 전체 조회 - MVC
  public Page<CrewResponseDto> findAll(Pageable pageable) {
    Page<CrewResponseDto> crews = crewRepository.findAll(pageable).map(CrewResponseDto::new);
    return crews;
  }
  // 크루 전체 조회 - hot 10 기준은 관심있어요가 많은 크루 순 - MVC
  public Page<CrewResponseDto> findHotCrews(Pageable pageable) {
    Page<CrewResponseDto> crews = crewRepository.findHotCrews(pageable).map(CrewResponseDto::new);
    return crews;
  }
  // 크루 상세 조회
  public CrewResponseDto findById(Long id){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return new CrewResponseDto(crew);
  }

  // 크루 삭제 (크루장만 삭제 가능)
  public void delete(Long id){
    crewRepository.deleteById(id);
  }

  public void apply(Long crewId, Long userId) {
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    if (crew.isJoinable(user)) {
      enrollmentService.enroll(user,crew);
    }
  }

  // 크루 가입신청 수락 -> 프론트에서 userId도 함께 URL에 넣어서 줄 수 있나..?
  public void acceptEnrollment(Long crewId, Long acceptId, Long userId) {
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

    enrollmentService.acceptEnrollment(acceptId);
    crew.addUser(user);
    // 좋아요 취소하기 로직 해야함.
    if (user.getLikes().stream().anyMatch(like -> like.getCrew().equals(crew))) {
      likeRepository.deleteByUserAndCrew(user, crew);
    }
  }
  public List<EnrollmentResponseDto> getApplyUsers(Long crewId){
    List<EnrollmentResponseDto> crewEnrollments = enrollmentService.getApplyUsers(crewId);
    return crewEnrollments;
  }
  // API
  public void leaveCrew(Long crewId, Long userId){
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

    if (crew.isMember(user)) {
      crew.removeUser(user);
    }
  }
  // MVC
  public void leaveCrew(Long crewId, String userId){
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    User user = userRepository.findUserByUserId(userId).orElseThrow(IllegalArgumentException::new);

    if (crew.isMember(user)) {
      crew.removeUser(user);
    }
  }

  public void update(Long id, CrewUpdateRequestDto crewUpdateRequestDto){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    crew.update(crewUpdateRequestDto); // 이렇게 엔티티로 Dto를 넘겨도 되나?
  }

  // 크루 세팅 - 크루 공개 여부
  public void setPublished(Long id){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    crew.setPublished();
  }

  // 크루 세팅 - 크루원 모집 여부
  public void setRecruited(Long id){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    crew.setRecruited();
  }
  // 크루 세팅 - 크루 종료 여부
  public void setClosed(Long id){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    crew.setClosed();
  }

  public void likeCrew(Long crewId, Long userId){
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);

    // 이미 좋아요한 경우에는 취소
    if(user.getLikes().stream().anyMatch(like -> like.getCrew().equals(crew))){
      likeRepository.deleteByUserAndCrew(user,crew);
    } else {
      likeRepository.save(Like.builder().crew(crew).user(user).build());
    }
  }

  public List<UserResponseDto> getUsers(Long crewId) {
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    List<UserResponseDto> users = crew.getUsers()
        .stream()
        .map(UserResponseDto::new)
        .collect(Collectors.toList());
    return users;
  }
}
