package com.jiwon.huhyhohy.web.dto;

import com.jiwon.huhyhohy.domain.reply.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequestDto {
  private String comment;
  private Long parentId;

  public Comment toEntity(){
    return Comment.builder()
        .comment(comment)
        .build();
  }
}
