package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.file.FileStore;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.CrewService;
import com.jiwon.huhyhohy.service.FileService;
import com.jiwon.huhyhohy.web.dto.crew.BannerUpdateDto;
import com.jiwon.huhyhohy.web.dto.user.ProfileUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class ImgController {
  private final FileStore fileStore;
  private final FileService fileService;
  private final CrewService crewService;


  // 게시판 이미지 조회할 떄 사용
  // 보안에 취약함. 좀 더 여러가지 체크해주는 로직을 작성해야함.
  @ResponseBody
  @GetMapping("/images/{filename}")
  public Resource downloadImage(@PathVariable String filename)
      throws MalformedURLException {
    return new UrlResource("file:" + fileStore.getFullPath(filename));
  }

  @PostMapping("/profile/update") // 프로필 사진 변경과 삭제
  public String profileUpdate(@ModelAttribute ProfileUpdateDto profileUpdateDto,
                              HttpSession session) throws IOException {
    User loginUser = (User) session.getAttribute("loginUser");

    if (profileUpdateDto.getProfile().isEmpty()) { // 사진 등록 안할 경우 null이 아님. 기본적으로 multipart 빈 객체를 만듬.
      fileService.deleteBeforeProfile(loginUser.getNickname());
    } else {
      fileService.transferProfile(profileUpdateDto.getProfile(), loginUser.getNickname());
    }

    return "redirect:/my-page";
  }
  // 프로필 이미지 보여주기
  @ResponseBody
  @GetMapping("/profile/{filename}")
  public Resource downloadProfile(@PathVariable String filename)
      throws MalformedURLException {
    return new UrlResource("file:" + fileStore.getProfileFullPath(filename));
  }

  // Crew 배너 설정
  @PostMapping("/crews/{id}/banner/update")
  public String updateBanner(@PathVariable Long id, @ModelAttribute BannerUpdateDto bannerUpdateDto)
      throws IOException {
    if (bannerUpdateDto.getBanner().isEmpty()) { //
      fileService.deleteBeforeBanner(id); // 배너가 비어있는 경우 기본 이미지로
    } else {
      fileService.transferBanner(bannerUpdateDto.getBanner(), id); // crewId 넘김
    }
    return "redirect:/crews/"+id;
  }
  // banner 이미지 보여주기
  @ResponseBody
  @GetMapping("/banner/{filename}")
  public Resource downloadBanner(@PathVariable String filename)
      throws MalformedURLException {
    return new UrlResource("file:" + fileStore.getBannerFullPath(filename));
  }
}
