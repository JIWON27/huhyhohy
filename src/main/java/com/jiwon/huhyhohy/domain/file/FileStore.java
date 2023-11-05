package com.jiwon.huhyhohy.domain.file;

import com.jiwon.huhyhohy.domain.crew.Banner;
import com.jiwon.huhyhohy.domain.user.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
  // 자유게시판 이미지 처리 로직
  @Value("${file.dir}") // application.yaml or application.properties에 지정한 파일 경로
  private String fileDir;

  // 파일 저장 경로 Full
  public String getFullPath(String filename){
    return fileDir + filename;
  }

  public ImgFile storeFile(MultipartFile multipartFile) throws IOException {
    if (multipartFile.isEmpty()){
      return null;
    }
    String originalFileName = multipartFile.getOriginalFilename();
    String ext = extractedExt(originalFileName);
    String storeFileName = createStoreFileName(ext);
    multipartFile.transferTo(new File(getFullPath(storeFileName)));

    return ImgFile.builder()
        .originFilename(originalFileName)
        .storeFilename(storeFileName)
        .build();
  }
  // 여러 개 업로드 하는 경우
  public List<ImgFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException{
    List<ImgFile> files = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFiles) {
      if (multipartFile.isEmpty()) {
        return null;
      }
      files.add(storeFile(multipartFile));
    }
    return files;
  }

  // 프로필 이미지
  @Value("${profile.dir}")
  private String profileDir;

  // 파일 저장 경로 Full
  public String getProfileFullPath(String filename){
    return profileDir + filename;
  }

  public Profile profileImgStore(MultipartFile multipartFile) throws IOException {
    if (multipartFile.isEmpty()){
      return null;
    }
    String originalFileName = multipartFile.getOriginalFilename();
    String ext = extractedExt(originalFileName);
    String storeFileName = createStoreFileName(ext);
    multipartFile.transferTo(new File(getProfileFullPath(storeFileName)));

    return Profile.builder()
        .originFilename(originalFileName)
        .storeFilename(storeFileName)
        .build();
  }

  // 크루 배너 사진
  // 프로필 이미지
  @Value("${banner.dir}")
  private String bannerDir;

  // 파일 저장 경로 Full
  public String getBannerFullPath(String filename){
    return bannerDir + filename;
  }

  public Banner bannerImgStore(MultipartFile multipartFile) throws IOException {
    if (multipartFile.isEmpty()){
      return null;
    }
    String originalFileName = multipartFile.getOriginalFilename();
    String ext = extractedExt(originalFileName);
    String storeFileName = createStoreFileName(ext);
    multipartFile.transferTo(new File(getBannerFullPath(storeFileName)));

    return Banner.builder()
        .originFilename(originalFileName)
        .storeFilename(storeFileName)
        .build();
  }


  // UUID를 활용해 저장용 파일명
  private String createStoreFileName(String ext) {
    String uuid = UUID.randomUUID().toString();
    String storedFileName = uuid + "." + ext;
    return storedFileName;
  }

  // 파일 확장자 추출
  private String extractedExt(String originalFileName) {
    int pos = originalFileName.lastIndexOf(".");
    String ext = originalFileName.substring(pos + 1);
    return ext;
  }
}
