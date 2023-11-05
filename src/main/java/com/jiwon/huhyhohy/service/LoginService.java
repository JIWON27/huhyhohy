package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final UserRepository userRepository;

  public User login(String email, String password){
    Optional<User> user = userRepository.findUserByEmail(email);
    if (user.get() != null && user.get().getPassword().equals(password)){
      return user.get();
    }
    return null;
  }
}
