package com.jiwon.huhyhohy.web.dto.reply;

import com.jiwon.huhyhohy.domain.reply.Comment;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String comment;
  private UserResponseDto user;
  private LocalDateTime createdDate;
  private List<CommentResponseDto> replies = new ArrayList<>();

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.comment = comment.getComment();
    this.user = new UserResponseDto(comment.getUser());
    this.createdDate = comment.getCreatedDate();
  }
}
