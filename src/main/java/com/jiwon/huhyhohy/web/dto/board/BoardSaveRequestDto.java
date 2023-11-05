package com.jiwon.huhyhohy.web.dto.board;

import com.jiwon.huhyhohy.domain.board.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveRequestDto {
  private String title;
  private String content;
  private List<MultipartFile> imgFiles;

  public Board toEntity() {
    return Board.builder()
        .title(title)
        .content(content)
        .imgFiles(new ArrayList<>())
        .build();
  }
}
