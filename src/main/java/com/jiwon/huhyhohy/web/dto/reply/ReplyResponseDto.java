package com.jiwon.huhyhohy.web.dto.reply;

import com.jiwon.huhyhohy.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {
  private Long id;
  private String comment;
  private String nickname;
  private LocalDateTime lastModifiedDate; // 댓글 수정 시간, 등록일 수정일 둘다 해야하나?

  public ReplyResponseDto(Reply reply) {
    this.id = reply.getId();
    this.comment = reply.getComment();
    this.nickname = reply.getUser().getNickname();
    this.lastModifiedDate = reply.getLastModifiedDate();
  }
}
