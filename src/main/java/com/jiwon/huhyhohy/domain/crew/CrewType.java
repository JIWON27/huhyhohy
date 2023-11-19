package com.jiwon.huhyhohy.domain.crew;

import lombok.Data;
import lombok.Getter;

@Getter
public enum CrewType {
  ONLINE("온라인"),
  OFFLINE("오프라인"),
  MIX("온/오프 혼합");

  private String type;

  CrewType(String type){
    this.type = type;
  }

  @Data // DTO 분리할지 말지 고민중
  public static class CrewTypeResponse{
    private String key; //ONLINE
    private String value; //온라인

    public CrewTypeResponse(String key, CrewType crewType){
      this.key = key;
      this.value = crewType.getType();
    }
  }

  public static CrewType fromString(String type) {
    for (CrewType crewType : CrewType.values()) {
      if (crewType.type.equals(type)) {
        return crewType;
      }
    }
    throw new IllegalArgumentException("No enum constant for type: " + type);
  }

}
