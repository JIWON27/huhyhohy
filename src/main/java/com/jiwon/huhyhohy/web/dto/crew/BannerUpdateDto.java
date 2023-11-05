package com.jiwon.huhyhohy.web.dto.crew;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class BannerUpdateDto {
  private MultipartFile banner;
}
