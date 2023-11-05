package com.jiwon.huhyhohy.web.dto.reply;

import com.jiwon.huhyhohy.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequestDto {
  private String comment;

  public Reply toEntity(){
    return Reply.builder()
        .comment(comment)
        .build();
  }
}
