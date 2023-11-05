package com.jiwon.huhyhohy.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequestDto {

  private String nickname;
  private String password;
}
