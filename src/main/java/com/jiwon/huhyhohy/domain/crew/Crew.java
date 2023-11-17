package com.jiwon.huhyhohy.domain.crew;

import com.jiwon.huhyhohy.domain.BaseTimeEntity;
import com.jiwon.huhyhohy.domain.Like;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.web.dto.crew.CrewUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

  @Enumerated(EnumType.STRING)
  private CrewType crewType;

  @Enumerated(EnumType.STRING)
  private Cost cost;

  @Enumerated(EnumType.STRING)
  private Category category;

  // 인원수 -> 인원수 = 회원수이면 프론트에서 크루 가입 버튼 안보이게하기. 그리고 isRecruiting false로 설정하 -> 어떻게?
  private int capacity;

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
    // 수정하지 않은 부분들은 이전의 입력값들이 넘어오게 해야함.
    this.name = crewUpdateRequestDto.getName();
    this.crewType = crewUpdateRequestDto.getCrewType();
    this.cost = crewUpdateRequestDto.getCost();
    this.category = crewUpdateRequestDto.getCategory();
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

