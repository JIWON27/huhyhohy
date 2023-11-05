package com.jiwon.huhyhohy.web.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardUpdateRequestDto {
  private String title;
  private String content;
  private List<MultipartFile> imgFiles;
}
