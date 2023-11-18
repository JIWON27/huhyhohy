package com.jiwon.huhyhohy.web.controller.api;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // 반환타입을 자동으로 JSON으로 변환해줌
@RequiredArgsConstructor
public class UserApiController {
  private final UserService userService;

  @PostMapping("/api/users/create")
  public Long saveUser(@RequestBody UserSaveRequestDto saveRequestDto){
    Long userId = userService.save(saveRequestDto);
    return userId;
  }

}
