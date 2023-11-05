package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsUserByEmail(String email); // 이메일 중복 확인
  boolean existsUserByNickname(String nickname); // 닉네임 중복 확인
  Optional<User> findUserByEmail(String email); // 로그인한 유저가 존재하는지 아닌지 이메일로 확인
  Optional<User> findUserByNickname(String nickname);
}
