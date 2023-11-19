package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.user.ProfileUpdateDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;


  @GetMapping("/my-page")
  public String myPage(Model model, HttpSession session){
    User loginUser = (User) session.getAttribute("loginUser");

    UserResponseDto user = userService.findByUserId(loginUser.getUserId());// 영속성 컨텍스트에 올리고

    model.addAttribute("loginUser", user);

    model.addAttribute("profileUpdateDto", new ProfileUpdateDto());

    // 크루장인 크루즈
    List<CrewResponseDto> leaderCrew = userService.leaderCrew(loginUser.getNickname());
    model.addAttribute("leaderCrew", leaderCrew);

    // "관심있어요" 크루즈
    List<CrewResponseDto> likeCrews = userService.LikeCrew(loginUser.getNickname());
    model.addAttribute("likeCrews", likeCrews);

    // 내가 참여한 크루즈
    List<CrewResponseDto> joinCrews = userService.joinCrew(loginUser.getNickname());
    model.addAttribute("joinCrews", joinCrews);
    return "/user/my-page";
  }

}
