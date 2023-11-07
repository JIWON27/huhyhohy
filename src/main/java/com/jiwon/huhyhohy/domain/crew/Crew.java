package com.jiwon.huhyhohy.domain.crew;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.Like;
import com.jiwon.huhyhohy.domain.tag.CrewTag;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.web.dto.crew.CrewUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Crew extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user; // 크루장

  private String name; // 크루이름
  private boolean type; //  true-온라인, false-오프라인
  private boolean cost; // true-유료탑승, false-무료탑승
  private boolean isRecruiting; // true - 모집중, false - 모집X,
  private boolean isPublished; // true - 공개O, false - 공개X
  private boolean isClosed; // true - 종료

  @OneToOne(mappedBy = "crew", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Banner banner; //크루 배너 이미지

  @Lob // varchar보다 클 경우 사용하는 어노테이션
  private String description; // 항해 목적

  @Lob // varchar보다 클 경우 사용하는 어노테이션
  private String wisher; // 원하는 선원

  @Lob // varchar보다 클 경우 사용하는 어노테이션
  private String plan; // 크루즈 설명

  @OneToMany(mappedBy = "crew")
  private Set<CrewTag> tags = new HashSet<>(); // tag를 중복으로 가질 수 없으니 Set 자료형으로 함.

  @ManyToMany// 크루원
  @JoinTable(
      name = "user_crew",
      joinColumns = @JoinColumn(name = "crew_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> users = new ArrayList<>();

  @OneToMany(mappedBy = "crew")
  private List<Like> likes = new ArrayList<>();

  public void updateBanner(Banner newBanner) {
    if (newBanner == null) {
      this.banner = null;
    }else {
      this.banner = newBanner;
      newBanner.setCrew(this);
    }
  }
  // 크루 가입
  public void addUser(User user){
    if(!this.users.contains(user)) {
      this.users.add(user);
    }
  }
  // 크루 탈퇴
  public void removeUser(User user) {
    if (this.users.contains(user)) {
      this.users.remove(user);
    }
  }

  // 크루에 가입이 가능한지
  public boolean isJoinable(User user) { // (1)
    return this.isPublished && this.isRecruiting && !this.users.contains(user) && !this.isClosed;
  }
  // 크루 멤버인지
  public boolean isMember(User user) { // (2)
    if (this.user.equals(user)){ // 크루장인 경우
      return true;
    }
    return this.users.contains(user); // 크루원인 경우
  }

  // 크루장
  public void setUser(User user){
    this.user = user;
  }

  public void update(CrewUpdateRequestDto crewUpdateRequestDto){
    this.name = crewUpdateRequestDto.getName();
    this.type = crewUpdateRequestDto.isType();
    this.cost = crewUpdateRequestDto.isCost();
    this.description = crewUpdateRequestDto.getDescription();
    this.wisher = crewUpdateRequestDto.getWisher();
    this.plan = crewUpdateRequestDto.getPlan();
  }

  public void setPublished(){
    if (this.isPublished) {
      this.isPublished = false;
    } else {
      this.isPublished = true;
    }

  }
  public void setRecruited(){
    if (this.isRecruiting) {
      this.isRecruiting = false;
    } else {
      this.isRecruiting = true;
    }
  }
  public void setClosed(){
    if (this.isClosed) {
      this.isClosed = false;
    } else {
      this.isClosed = true;
    }
  }

}

