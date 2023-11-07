package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LikeController {
  private final LikeService likeService;

  @PostMapping("/crews/{id}/like")
  public String likeCrew(@PathVariable Long id, HttpSession session){
    User loginUser = (User) session.getAttribute("loginUser");
    likeService.likeCrew(id, loginUser.getNickname());
    return "redirect:/crews/"+id;
  }
}
