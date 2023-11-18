package com.jiwon.huhyhohy.domain.crew;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Cost {

  FREE("무료"),
  PAID("유료"),
  MIX("유료/무료 혼합");

  private String cost;

  Cost(String type){
    this.cost = type;
  }

  @Data // DTO 분리할지 말지 고민중
  public static class CrewCostResponse{
    private String key; //FREE
    private String value; //무료

    public CrewCostResponse(String key, Cost cost){
      this.key = key;
      this.value = cost.getCost();
    }
  }
  public static Cost fromString(String pay) {
    for (Cost cost : Cost.values()) {
      if (cost.cost.equals(pay)) {
        return cost;
      }
    }
    throw new IllegalArgumentException("No enum constant for type: " + pay);
  }
}
