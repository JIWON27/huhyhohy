package com.jiwon.huhyhohy.web.controller.api;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.BoardService;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.board.BoardSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.board.BoardUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/boards") // API 엔드포인트 루트 경로
@RequiredArgsConstructor
@CrossOrigin //디폴트값으로 다 됨
public class BoardApiController {

  private final BoardService boardService;
  private final UserService userService;

  @PostMapping("/create")
  public ResponseEntity<Void> createBoard(@RequestBody BoardSaveRequestDto boardSaveRequestDto, HttpSession session) throws IOException {
    User loginUser = (User) session.getAttribute("loginUser");
    boardService.save(boardSaveRequestDto, loginUser.getNickname());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id, HttpSession session) {
    User loginUser = (User) session.getAttribute("loginUser");
    UserResponseDto user = userService.findByNickname(loginUser.getNickname());
    BoardResponseDto board = boardService.findById(id);

    return ResponseEntity.ok(board);
  }

  @GetMapping("/all")
  public ResponseEntity<Page<BoardResponseDto>> getAllBoards(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy){
    Page<BoardResponseDto> boards = boardService.findAll(pageNo,pageSize,sortBy);
    return new ResponseEntity<>(boards, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
    boardService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/edit")
  public ResponseEntity<Void> updateBoard(@PathVariable Long id,
                                          @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) throws IOException {
    boardService.update(id, boardUpdateRequestDto);
    return ResponseEntity.noContent().build();
  }
}

