package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.CrewService;
import com.jiwon.huhyhohy.service.MeetingService;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.meeting.MeetingResponseDto;
import com.jiwon.huhyhohy.web.dto.meeting.MeetingSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MeetingController {
  private final CrewService crewService;
  private final MeetingService meetingService;

  @GetMapping("/crews/{id}/meeting") // 전채 조회, 상세까지 함께 보이게 함 그냥
  public String crewMeetingList(@PathVariable Long id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("loginUser");
    CrewResponseDto crewResponseDto = crewService.findById(id);
    UserResponseDto loginUser = new UserResponseDto(user);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("crewResponseDto", crewResponseDto);

    List<MeetingResponseDto> meetingList = meetingService.findAll();
    model.addAttribute("meetingList", meetingList);
    return "crew/meeting";
  }

  @GetMapping("/crews/{crewId}/meeting/form") // 모임 생성 폼
  public String crewMeetingSaveForm(@PathVariable Long crewId,
                                    Model model,
                                    HttpSession session) {
    User user = (User) session.getAttribute("loginUser");

    CrewResponseDto crewResponseDto = crewService.findById(crewId);
    UserResponseDto loginUser = new UserResponseDto(user);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("crewResponseDto", crewResponseDto);
    model.addAttribute("meetingSaveRequestDto", new MeetingSaveRequestDto());

    return "crew/meetingForm";
  }

  @PostMapping("/crews/{crewId}/meeting/form") // 모임 생성
  public String crewMeetingSave(@PathVariable Long crewId,
                                @ModelAttribute MeetingSaveRequestDto meetingSaveRequestDto) {
    meetingService.save(crewId, meetingSaveRequestDto);
    return "redirect:/crews/"+crewId+"/meeting";
  }
  // 모임 삭제
}
