package com.jiwon.huhyhohy.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ProfileUpdateDto {
  private MultipartFile profile;
}
