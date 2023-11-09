package com.jiwon.huhyhohy.web.dto.board;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.file.ImgFile;
import com.jiwon.huhyhohy.web.dto.img.ImgFileResponseDto;
import com.jiwon.huhyhohy.web.dto.reply.ReplyResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
  private Long id; // 글 조회할 때 사용
  private String nickname; // 글 작성자를 나타낼 때 사용
  private String title;
  private String content;
  private List<ReplyResponseDto> replies;
  private List<ImgFileResponseDto> imageFiles;

  // 디비에 있는 엔티티를 가지고 dto로 반들어야함
  public BoardResponseDto(Board board){
    this.id = board.getId();
    this.nickname = board.getUser().getNickname();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.imageFiles = board.getImgFiles().stream().map(ImgFileResponseDto::new).collect(Collectors.toList());
    this.replies = board.getReplies().stream().map(ReplyResponseDto::new).collect(Collectors.toList()); //이게 맞나..
  }
}
