package com.jiwon.huhyhohy.domain.crew;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Category {

  MEETING("모임"), //여기에 맛집, 술, 등등 다 합친겨
  GAME("게임"),
  SPORTS("운동"),
  STUDY("스터디"),
  VOLUNTEER_WORK("봉사"),
  TRAVEL("여행"),
  ETC("기타");

  Category(String category) {
    this.category = category;
  }

  private String category;

  @Data // DTO 분리할지 말지 고민중
  public static class CrewCategoryResponse{
    private String key; //FREE
    private String value; //무료

    public CrewCategoryResponse(String key, Category category){
      this.key = key;
      this.value = category.getCategory();
    }
  }
  public static Category fromString(String value) {
    for (Category category : Category.values()) {
      if (category.category.equals(value)) {
        return category;
      }
    }
    throw new IllegalArgumentException("No enum constant for type: " + value);
  }

}
