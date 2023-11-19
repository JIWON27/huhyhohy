package com.jiwon.huhyhohy.domain.user;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.Like;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.web.dto.user.UserUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String userId;
  private String nickname;
  private String password;
  private String email;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Profile profile;

  @OneToMany(mappedBy = "user")
  private List<Crew> leaderCrews = new ArrayList<>(); // 내가 크루장인 크루

  @ManyToMany(mappedBy = "users")
  private List<Crew> crews = new ArrayList<>(); // 참여중인 크루

  @OneToMany(mappedBy = "user")
  private List<Like> likes = new ArrayList<>();

  public void updateProfile(Profile newProfile) {
    if (newProfile == null) {
      this.profile = null;
    }else {
      this.profile = newProfile;
      newProfile.setUser(this);
    }

  }

  public void update(UserUpdateRequestDto updateDto) {
    // 갑자기 궁금한게 회원정보를 닉네임만 변경하던가 비번만 변경할 수 있는데 이럴경우 업데이트 메서드를 따로 분리해야하나?
    this.nickname = updateDto.getNickname();
    this.password = updateDto.getPassword();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId()) && Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getNickname(), user.getNickname()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getEmail(), user.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserId(), getNickname(), getPassword(), getEmail());
  }
}
