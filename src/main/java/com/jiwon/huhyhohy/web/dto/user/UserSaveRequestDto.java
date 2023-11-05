package com.jiwon.huhyhohy.web.dto.user;

import com.jiwon.huhyhohy.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

  @NotBlank
  private String name; // 본명

  @NotBlank
  @Length(min = 2, max = 10)
  @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,10}$")
  private String nickname; // 별명

  @NotBlank
  @Length(min = 8, max = 20)
  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
      message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
  private String password;

  @Email // 이메일 형식인지 확인하
  @NotBlank
  private String email;

  public User toEntity(){
      return User.builder()
          .name(name)
          .nickname(nickname)
          .password(password)
          .email(email)
          .build();
  }
}
