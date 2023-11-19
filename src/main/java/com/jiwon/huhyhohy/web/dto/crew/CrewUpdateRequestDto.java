package com.jiwon.huhyhohy.web.dto.crew;

import com.jiwon.huhyhohy.domain.crew.Category;
import com.jiwon.huhyhohy.domain.crew.Cost;
import com.jiwon.huhyhohy.domain.crew.CrewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewUpdateRequestDto {
  private String name; // 크루이름
  private String crewType;
  private String cost;
  private int capacity;
  private String category;
  private String description; // 크루즈 설명
  private String wisher; // 원하는 선원
  private String plan; // 크루즈 설명

  public CrewType getCrewType() {
    return CrewType.fromString(crewType); // String -> Enum
  }
  public Cost getCost() {
    return Cost.fromString(cost); // String -> Enum
  }
  public Category getCategory() {
    return Category.fromString(category); // String -> Enum
  }
}
