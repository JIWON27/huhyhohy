package com.jiwon.huhyhohy.web.dto.tag;

import com.jiwon.huhyhohy.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {
  private Long id; // 뭔가 id쓸꺼같음 ㅎ
  private String tagName;

  public TagResponseDto(Tag tag){
    this.id = tag.getId();
    this.tagName = tag.getTagName();
  }
}
