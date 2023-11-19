package com.jiwon.huhyhohy.web.dto.comment;

import com.jiwon.huhyhohy.domain.comment.Comment;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequestDto {
  private String content;
  private Long parentId;
  private String writer;

  public Comment toEntity(){
    return Comment.builder()
        .content(content)
        .build();
  }
}
