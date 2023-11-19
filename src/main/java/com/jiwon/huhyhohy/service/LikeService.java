package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.Like;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.CrewRepository;
import com.jiwon.huhyhohy.repository.LikeRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
  private final LikeRepository likeRepository;
  private final CrewRepository crewRepository;
  private final UserRepository userRepository;

  @Transactional
  public void likeCrew(Long crewId, String userId){
    User user = userRepository.findUserByUserId(userId).orElseThrow(IllegalArgumentException::new);
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);

    // 이미 좋아요한 경우에는 취소
    if(user.getLikes().stream().anyMatch(like -> like.getCrew().equals(crew))){
      likeRepository.deleteByUserAndCrew(user,crew);
    } else {
      likeRepository.save(Like.builder().crew(crew).user(user).build());
    }
  }
}
