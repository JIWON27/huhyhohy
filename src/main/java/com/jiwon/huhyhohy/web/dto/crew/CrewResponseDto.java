package com.jiwon.huhyhohy.web.dto.crew;

import com.jiwon.huhyhohy.domain.crew.Banner;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CrewResponseDto {

  private Long id; // 크루 조회할 때 사용
  private String leader; // 크루장
  private String name; // 크루명
  //private boolean type; //  true-온라인, false-오프라인
  //private boolean cost; // true-유료탑승, false-무료탑승
  private String crewType;
  private String cost;
  private String description; // 크루즈 설명
  private String wisher; // 원하는 선원
  private String plan; // 크루즈 설명
  private boolean isRecruit; // true - 모집중, false - 모집X
  private boolean isPublished; // true - 공개O, false - 공개X
  private boolean isClosed;
  private Banner banner;
  private List<UserResponseDto> users;
  private int likeCnt;

  public CrewResponseDto(Crew crew) {
    this.id = crew.getId();
    this.leader = crew.getUser().getNickname();
    this.name = crew.getName();
    this.crewType = crew.getCrewType().getType();
    this.cost = crew.getCost().getCost();
    this.description = crew.getDescription();
    this.wisher = crew.getWisher();
    this.plan = crew.getPlan();
    this.isRecruit = crew.isRecruiting();
    this.isPublished = crew.isPublished();
    this.isClosed = crew.isClosed();
    this.banner = crew.getBanner();
    this.likeCnt = crew.getLikes().size();
    this.users = crew.getUsers().stream().map(UserResponseDto::new).collect(Collectors.toList());
  }
}
