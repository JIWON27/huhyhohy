package com.jiwon.huhyhohy.web.dto.board;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.web.dto.img.ImgFileResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
  private Long id; // 글 조회할 때 사용
  private UserResponseDto user;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private List<ImgFileResponseDto> imageFiles;

  // 디비에 있는 엔티티를 가지고 dto로 반들어야함
  public BoardResponseDto(Board board){
    this.id = board.getId();
    this.user = new UserResponseDto(board.getUser());
    this.title = board.getTitle();
    this.content = board.getContent();
    this.createdDate = board.getCreatedDate();
    this.imageFiles = board.getImgFiles().stream().map(ImgFileResponseDto::new).collect(Collectors.toList());
  }
}
