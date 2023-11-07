package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.LoginService;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.LoginDto;
import com.jiwon.huhyhohy.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserAccountController {
  // 회원 계정 관련 컨트롤러
  private final UserService userService;
  private final LoginService loginService;

  // 회원 가입 - GET
  @GetMapping("/sign-up")
  public String signUpForm(Model model){
    model.addAttribute("userSaveRequestDto", new UserSaveRequestDto());
    return "account/signup";
  }
  // 회원 가입 - POST
  @PostMapping("/sign-up")
  public String signUpSubmit(@Valid @ModelAttribute UserSaveRequestDto requestDto,
                             BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return "account/signup";
    }
    // 문제는 이메일이 먼저 뜨고 닉네임이 그 다음에 뜸. 둘이 동시에 뜨지 않아서,, 어떻게 해야하지?
    // 우선 넘어감.
    if (userService.checkEmail(requestDto.getEmail())){
      bindingResult.rejectValue("email", null, "이미 사용중인 이메일입니다.");
      return "account/signup";
    }
    if (userService.checkNickName(requestDto.getNickname())){
      bindingResult.rejectValue("nickname", null, "이미 사용중인 닉네임입니다.");
      return "account/signup";
    }
    // 회원 가입 처리 로직 더 구현해야함.
    userService.save(requestDto);
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String loginForm(Model model){
    model.addAttribute("loginDto", new LoginDto());
    return "account/login";
  }
  @PostMapping("/login")
  public String login(@ModelAttribute LoginDto loginDto, HttpSession session){
    // 로그인 처리 로직
    User loginUser = loginService.login(loginDto.getEmail(), loginDto.getPassword());
    session.setAttribute("loginUser", loginUser);
    return "redirect:/crews";
  }

  @PostMapping("/logout") // 로그아웃을 왜 Post로 했더라? 뭔 이유가 있었던거 같은데,..
  public String logout(HttpServletRequest request){
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/crews";
  }
}
