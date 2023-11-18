package com.jiwon.huhyhohy.web.dto.reply;

import com.jiwon.huhyhohy.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {
  private Long id;
  private String comment;
  // UserDto로 변경하는 건 어떨까요? 프로필 같은 것들은 댓글에 표기되기도 하니까요
  private String nickname;
  // 주로 등록일이 표기되고 (수정됨)을 자율적으로 표기하는 것 같아요
  // 만약 수정일이 명시된다면 더 빨리 달린 댓글이 수정될 시 최근 일자의 댓글이 위에 표기되는 게 조금 거시기 할 것 같아요
  private LocalDateTime lastModifiedDate; // 댓글 수정 시간, 등록일 수정일 둘다 해야하나?
  private List<ReplyResponseDto> reply = new ArrayList<>();

  public ReplyResponseDto(Reply reply) {
    this.id = reply.getId();
    this.comment = reply.getComment();
    this.nickname = reply.getUser().getNickname();
    this.lastModifiedDate = reply.getLastModifiedDate();
  }
}
