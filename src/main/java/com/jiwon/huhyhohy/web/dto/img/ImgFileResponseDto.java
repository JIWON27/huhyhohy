package com.jiwon.huhyhohy.web.dto.img;

import com.jiwon.huhyhohy.domain.file.ImgFile;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ImgFileResponseDto {

  private String storeFilename;
  private LocalDateTime createdDate;

  public ImgFileResponseDto(ImgFile imgFile) {
    this.storeFilename = imgFile.getStoreFilename();
    this.createdDate = imgFile.getCreatedDate();
  }

}
