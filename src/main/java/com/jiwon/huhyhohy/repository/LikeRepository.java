package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.Like;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
  void deleteByUserAndCrew(User user, Crew crew);




}
