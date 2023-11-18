package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.crew.Banner;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.file.FileStore;
import com.jiwon.huhyhohy.domain.file.ImgFile;
import com.jiwon.huhyhohy.domain.user.Profile;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
  private final FileStore fileStore;
  private final FileRepository fileRepository; //cascadeType.All이어서 따로 저장할 필요는 없는거같음..
  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final CrewRepository crewRepository;
  private final BannerRepository bannerRepository;


  public List<ImgFile> transferImgFile(List<MultipartFile> imgFile) throws IOException {
    List<ImgFile> imgFiles = fileStore.storeFiles(imgFile);
    return imgFiles;
  }

  public void deleteBeforeFile(Board board){
    if (!board.getImgFiles().isEmpty()) { // 이거 아래랑 왜 중복이지,,, 중복 제거하기,,
      // 이전 첨부파일 삭제
      if (!board.getImgFiles().isEmpty()) {
        for (ImgFile imgFile : board.getImgFiles()) {
          // 파일 시스템에서 실제 이미지 파일 삭제
          File imgFileOnDisk = new File(fileStore.getFullPath(imgFile.getStoreFilename()));
          if (imgFileOnDisk.exists()) {
            imgFileOnDisk.delete();
          }
          // 데이터베이스에서 이미지 파일 레코드 삭제
          this.delete(imgFile);
          // this.delete(imgFile); 지우고 그냥 fileRepository.delete(imgFile); 바로 써도되지않나? 해보고 확인하기.
        }
      }
    }
  }
  public void delete(ImgFile imgFile){
    fileRepository.delete(imgFile);
  }

  // 프로필 이미지 변경
  @Transactional
  public Profile transferProfile(MultipartFile profile, String nickname) throws IOException {
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    Profile profileImg = fileStore.profileImgStore(profile);
    user.updateProfile(profileImg);
    return profileImg;
  }

  public void deleteBeforeProfile(String nickname) {
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);

    if (user.getProfile() != null) {
      Profile profile = user.getProfile();

      File imgFileOnDisk = new File(fileStore.getProfileFullPath(profile.getStoreFilename()));
      if (imgFileOnDisk.exists()) {
        imgFileOnDisk.delete();
      }
      user.updateProfile(null);
      profileRepository.delete(profile);
    }
  }

  // 크루 배너 변경
  @Transactional
  public Banner transferBanner(MultipartFile banner, Long crewId) throws IOException {
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    Banner bannerImg = fileStore.bannerImgStore(banner);
    crew.updateBanner(bannerImg);
    return bannerImg;
  }

  public void deleteBeforeBanner(Long crewId) {
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);

    if (crew.getBanner() != null) {
      Banner bannerImg = crew.getBanner();

      File imgFileOnDisk = new File(fileStore.getBannerFullPath(bannerImg.getStoreFilename()));
      if (imgFileOnDisk.exists()) {
        imgFileOnDisk.delete();
      }
      crew.updateBanner(null);
      bannerRepository.delete(bannerImg);
    }
  }


}