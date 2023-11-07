package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.CrewRepository;
import com.jiwon.huhyhohy.repository.LikeRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrewService {
  private final CrewRepository crewRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository; // 크루 참가하면 관심있어요 삭제

  // 크루 저장
  public Long save(CrewSaveRequestDto crewSaveRequestDto, String nickname){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    Crew crew = crewSaveRequestDto.toEntity();

    crew.setUser(user);
    Crew savedCrew = crewRepository.save(crew);
    return savedCrew.getId();
  }
  // 크루 전체 조회
  public Page<CrewResponseDto> findAll(Pageable pageable) {
    Page<CrewResponseDto> crews = crewRepository.findAll(pageable).map(CrewResponseDto::new);
    return crews;
  }
  // 크루 전체 조회 - hot 10 기준은... 1. 관심있어요가 많은 크루 순
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

  // 크루 참가
  public void addUser(Long id, String nickname){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);

    if (crew.isJoinable(user)){
      crew.addUser(user);
      // user.getLikes().removeIf(like -> like.getCrew().equals(crew)); 이렇게 쓸 수도 있음. removeIf() -> Java8
      // 크루 가입하고 만약에 관심있어요 눌러져있으면 삭제
      if(user.getLikes().stream().anyMatch(like -> like.getCrew().equals(crew))) {
        likeRepository.deleteByUserAndCrew(user, crew);
      }
    }
  }

  public void leaveCrew(Long id, String nickname){
    Crew crew = crewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);

    if (crew.isMember(user)) {
      crew.removeUser(user);
    }
  }

  @Transactional
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


}
