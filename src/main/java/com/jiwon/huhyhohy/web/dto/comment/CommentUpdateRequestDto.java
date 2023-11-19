package com.jiwon.huhyhohy.web.dto.comment;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentUpdateRequestDto {
  private String content;
  private String writer;
}
