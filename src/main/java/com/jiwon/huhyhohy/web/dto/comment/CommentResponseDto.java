package com.jiwon.huhyhohy.web.dto.comment;

import com.jiwon.huhyhohy.domain.comment.Comment;
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
  private String content;
  private UserResponseDto user;
  private LocalDateTime createdDate;
  private String deleteStatus;
  private List<CommentResponseDto> replies = new ArrayList<>();

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.deleteStatus = comment.getDeleteStatus().toString();
    this.user = new UserResponseDto(comment.getUser());
    this.createdDate = comment.getCreatedDate();
  }
}
