package com.jiwon.huhyhohy.web.dto.tag;

import com.jiwon.huhyhohy.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagSaveRequestDto {

  private String tagName;

  public Tag toEntity(){
    return Tag.builder()
        .tagName(tagName)
        .build();
  }
}
