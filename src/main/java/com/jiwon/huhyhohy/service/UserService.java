package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.exception.blogException;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 키워드 DI
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  // 회원 가입
  public Long save(UserSaveRequestDto request){
    User user = request.toEntity();
    User savedUser = userRepository.save(user);
    return savedUser.getId();
  }
  // 회원 조회 - 단건
  public UserResponseDto findById(Long id){
    User user = userRepository.findById(id).orElseThrow(()->new blogException("해당 회원은 존재하지 않습니다."));
    return new UserResponseDto(user);
  }

  // 회원 조회 - 단건
  public UserResponseDto findByNickname(String nickname){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(()->new blogException("해당 회원은 존재하지 않습니다."));
    return new UserResponseDto(user);
  }

  // 회원 조회 - 단건, UserId
    public UserResponseDto findByUserId(String userId){
        User user = userRepository.findUserByUserId(userId).orElseThrow(()->new blogException("해당 회원은 존재하지 않습니다."));
        return new UserResponseDto(user);
    }

  // 회원 조회 - 전체
  public List<UserResponseDto> findAll() {
    List<UserResponseDto> users = userRepository.findAll()
        .stream()
        .map(UserResponseDto::new)
        .collect(Collectors.toList());
    return users;
  }
  // 회원 삭제
  public void delete(Long id){
    userRepository.deleteById(id);
  }

  // 회원 정보 수정
  @Transactional
  public Long update(Long id, UserUpdateRequestDto request){
    User user = userRepository.findById(id).orElseThrow(() -> new blogException("해당 회원은 존재하지 않습니다."));
    user.update(request); // 단순 텍스트 정보
    //  고민이다. save()를 할까 아니면 이렇게 그냥 update() 메서드를 통해서 update를 진행할까..
    return user.getId();
  }

  public boolean checkUserId(String userId){
    return userRepository.existsUserByUserId(userId);
  }

  public boolean checkEmail(String email){
    return userRepository.existsUserByEmail(email);
  }
  public boolean checkNickName(String nickName){
    return userRepository.existsUserByNickname(nickName);
  }

  // 관심 있는 크루
  public List<CrewResponseDto> LikeCrew(String userId){
    User user = userRepository.findUserByUserId(userId)
        .orElseThrow(() -> new blogException("해당 회원은 존재하지 않습니다."));

    List<CrewResponseDto> crews = user.getLikes().stream()
        .map(like -> new CrewResponseDto(like.getCrew()))
        .collect(Collectors.toList());
    return crews;
  }
  // 참여하고 있는 크루
  public List<CrewResponseDto> joinCrew(String userId){
    User user = userRepository.findUserByUserId(userId)
        .orElseThrow(() -> new blogException("해당 회원은 존재하지 않습니다."));

    List<CrewResponseDto> crews = user.getCrews().stream()
        .map(CrewResponseDto::new)
        .collect(Collectors.toList());
    return crews;
  }
  // 크루장인 크루
  public List<CrewResponseDto> leaderCrew(String userId){
    User user = userRepository.findUserByUserId(userId)
        .orElseThrow(() -> new blogException("해당 회원은 존재하지 않습니다."));

    List<CrewResponseDto> crews = user.getLeaderCrews().stream()
        .filter(crew -> crew.getUser().equals(user))
        .map(CrewResponseDto::new)
        .collect(Collectors.toList());
    return crews;
  }

  // 활동종료 크루 (크루원일 때)
  public List<CrewResponseDto> closeCrew(String userId){
    User user = userRepository.findUserByUserId(userId)
        .orElseThrow(() -> new blogException("해당 회원은 존재하지 않습니다."));

    List<CrewResponseDto> crews = user.getCrews().stream()
        .filter(crew -> crew.isClosed())
        .map(CrewResponseDto::new)
        .collect(Collectors.toList());
    return crews;
  }

}
