package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.BoardService;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.board.BoardSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.board.BoardUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.board.PageBoardResponseDto;
import com.jiwon.huhyhohy.web.dto.reply.ReplySaveRequestDto;
import com.jiwon.huhyhohy.web.dto.reply.ReplyUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardViewController {

  private final BoardService boardService;
  private final UserService userService;


  // 글 등록 폼 보여주기
  @GetMapping("/board/form")
  public String boardWrite(Model model){
    model.addAttribute("boardSaveRequestDto", new BoardSaveRequestDto());
    return "board/form";
  }

  // 글 등록
  @PostMapping("/board/form")
  public String boardSave(@ModelAttribute BoardSaveRequestDto boardSaveRequestDto,
                          HttpSession session) throws IOException {
    User loginUser = (User) session.getAttribute("loginUser");

    boardService.save(boardSaveRequestDto, loginUser.getNickname());
    return  "redirect:/boards";
  }

  // 글 조회 - 단건 = 상세 조회
  @GetMapping("/boards/{id}")
  public String boards(@PathVariable Long id, Model model, HttpSession session) {
    User loginUser = (User) session.getAttribute("loginUser");
    UserResponseDto user = userService.findByNickname(loginUser.getNickname());

    BoardResponseDto board = boardService.findById(id);

    model.addAttribute("loginUser", user);
    model.addAttribute("replySaveRequestDto", new ReplySaveRequestDto()); // 댓글 입력
    model.addAttribute("replyUpdateRequestDto", new ReplyUpdateRequestDto()); // 댓글 수정

    model.addAttribute("board", board); // 화면에 게시글 정보 뿌리기위해
    return "board/detailBoard";
  }
  // 글 조회 - 전체 - 페이징
  @GetMapping("/boards")
  public String boards(Model model,
                       @PageableDefault(size = 5, sort = "createdDate",
                           direction = Sort.Direction.DESC) Pageable pageable) {
    //Page<PageBoardResponseDto> boards = boardService.findAll(pageable);
    //model.addAttribute("boards", boards);
    return "board/main";

  }
  // 글 삭제
  @DeleteMapping("/boards/{id}")
  public String delete(@PathVariable Long id){
    boardService.delete(id);
    return "redirect:/boards";
  }

  // 글 수정 폼
  @GetMapping("/boards/{id}/edit")
  public String updateForm(@PathVariable Long id, Model model){
    BoardResponseDto board = boardService.findById(id);
    BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto();
    boardUpdateRequestDto.setTitle(board.getTitle());
    boardUpdateRequestDto.setContent(board.getContent());

    model.addAttribute("boardUpdateRequestDto", new BoardUpdateRequestDto());
    return "board/modifyForm";
  }
  // 글 수정
  @PutMapping("/boards/{id}/edit")
  public String update(@PathVariable Long id,
                       @ModelAttribute BoardUpdateRequestDto boardUpdateRequestDto) throws IOException {
    boardService.update(id, boardUpdateRequestDto);
    return "redirect:/boards/"+id;
  }

  // 내가 쓴 게시물
  @GetMapping("boards/myBoards")
  public String myBoards(RedirectAttributes redirectAttributes, HttpSession session){
    User user = (User)session.getAttribute("loginUser");
    UserResponseDto loginUser = userService.findByNickname(user.getNickname());

    List<BoardResponseDto> myBoards = boardService.findMyBoards(user.getNickname());
    redirectAttributes.addFlashAttribute("myBoards", myBoards);
    return "redirect:/my-page";
  }

}
