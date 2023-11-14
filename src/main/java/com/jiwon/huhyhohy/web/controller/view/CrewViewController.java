package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.CrewService;
import com.jiwon.huhyhohy.web.dto.crew.BannerUpdateDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CrewViewController {
  private final CrewService crewService;

  // 크루 생성 폼
  @GetMapping("/crews/form")
  public String crewForm(Model model) {
    model.addAttribute("crewSaveRequestDto", new CrewSaveRequestDto());
    return "crew/form";
  }

  // 크루 생성
  @PostMapping("/crews/form")
  public String crewSave(@ModelAttribute CrewSaveRequestDto crewSaveRequestDto,
                         HttpSession session) {
    User loginUser = (User) session.getAttribute("loginUser");
    Long id = crewService.save(crewSaveRequestDto, loginUser.getId());
    return "redirect:/crews/" + id;
  }

  // 크루 조회 - 상세 조회
  @GetMapping("/crews/{id}") // 서비스 기본화면
  public String crew(@PathVariable Long id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("loginUser");
    CrewResponseDto crewResponseDto = crewService.findById(id);

    UserResponseDto loginUser = new UserResponseDto(user);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("crewResponseDto", crewResponseDto);
    return "crew/intro";
  }

  // 크루 전체 조회 - 현재 모집 중인 크루즈
  @GetMapping("/crews") // 서비스 기본화면
  public String crews(Model model,
                      @PageableDefault(size = 5, sort = "createdDate",
                          direction = Sort.Direction.DESC) Pageable pageable,
                      @SessionAttribute(name = "loginUser", required = false) User loginUser) {
    Page<CrewResponseDto> crews = crewService.findAll(pageable);
    Page<CrewResponseDto> hotCrews = crewService.findHotCrews(pageable);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("crews", crews); // 현재 모집 중인 크루즈를 나타낼 때 사용
    model.addAttribute("hotCrews", hotCrews); // top10크루 정보를 담고 있음 -> 쿼리를 생성해서 가져와야할듯
    return "main";
  }

  @DeleteMapping("/crews/{id}/delete")
  public String deleteCrew(@PathVariable Long id){
    crewService.delete(id);
    return "redirect:/crews";
  }

  // 크루 참가 신청 -> 관심있어요 삭제
  @GetMapping("/crews/{crewId}/join")
  public String joinCrew(HttpSession session, @PathVariable Long crewId) {
    User loginUser = (User) session.getAttribute("loginUser");
    crewService.addUser(crewId, loginUser.getNickname());
    return "redirect:/crews/" + crewId;
  }

  // 크루 탈퇴
  @GetMapping("/crews/{id}/leave")
  public String leaveCrew(HttpSession session, @PathVariable Long id) {
    User loginUser = (User) session.getAttribute("loginUser");
    crewService.leaveCrew(id, loginUser.getNickname());
    return "redirect:/crews/" + id;
  }

  // 크루 detailView

  @GetMapping("/crews/{id}/setting") // 설정 -> Crew Update
  public String crewSetting(@PathVariable Long id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("loginUser");
    CrewResponseDto crewResponseDto = crewService.findById(id);
    UserResponseDto loginUser = new UserResponseDto(user);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("crewResponseDto", crewResponseDto);
    model.addAttribute("bannerUpdateDto", new BannerUpdateDto());
    model.addAttribute("crewUpdateRequestDto", new CrewUpdateRequestDto());
    return "crew/setting";
  }

  @PostMapping("/crews/{id}/setting") // 설정 -> Crew Update
  public String crewSetting(@PathVariable Long id,
                            @ModelAttribute CrewUpdateRequestDto crewUpdateRequestDto) {
    crewService.update(id, crewUpdateRequestDto);
    return "redirect:/crews/" + id;
  }

  // 크루 공개 여부
  @PostMapping("/crews/{id}/published")
  public String publishedCrew(@PathVariable Long id) {
    crewService.setPublished(id);
    return "redirect:/crews/" + id + "/setting";
  }
  // 크루 인원 모집 여부
  @PostMapping("/crews/{id}/recruit")
  public String recruitCrew(@PathVariable Long id) {
    crewService.setRecruited(id);
    return "redirect:/crews/" + id + "/setting";
  }

  // 크루 종료 여부
  @PostMapping("/crews/{id}/close")
  public String closeCrew(@PathVariable Long id) {
    crewService.setClosed(id);
    return "redirect:/crews/" + id + "/setting";
  }

}
