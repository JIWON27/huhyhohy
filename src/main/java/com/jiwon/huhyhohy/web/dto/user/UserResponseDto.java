package com.jiwon.huhyhohy.web.dto.user;

import com.jiwon.huhyhohy.domain.user.Profile;
import com.jiwon.huhyhohy.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private String name; // 본명
  private String nickname; // 별명
  private Profile profile; // 프로필 사진


  public UserResponseDto(User user){
    this.name = user.getName();
    this.nickname = user.getNickname();
    this.profile = user.getProfile();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserResponseDto that = (UserResponseDto) o;
    return Objects.equals(getName(), that.getName()) && Objects.equals(getNickname(), that.getNickname());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getNickname());
  }
}
