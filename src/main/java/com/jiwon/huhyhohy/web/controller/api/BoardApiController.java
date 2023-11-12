package com.jiwon.huhyhohy.web.controller.api;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.BoardService;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.board.BoardSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.board.BoardUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.board.PageBoardResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/boards") // API 엔드포인트 루트 경로
@RequiredArgsConstructor
@CrossOrigin //디폴트값으로 다 됨 -> CORS 해결
public class BoardApiController {

  private final BoardService boardService;
  @PostMapping("/{userId}/form") // API URL 수정
  public ResponseEntity<Void> createBoard(@RequestBody BoardSaveRequestDto boardSaveRequestDto,
                                          @PathVariable Long userId) throws IOException {
    boardService.save_api(boardSaveRequestDto, userId);
    return ResponseEntity.status(HttpStatus.OK).build(); // OK - code 200
  }

  @GetMapping("/{boardId}") // 상세조회
  public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {

    BoardResponseDto board = boardService.findById(boardId);
    return ResponseEntity.status(HttpStatus.OK).body(board);
  }

  @GetMapping //여기선 타이틀이랑 id, 글 작성자만 있는 BoardResponseDto를 반환해주기위해 PageBoardResponseDto 생성.
  public ResponseEntity<Page<PageBoardResponseDto>> getAllBoards(
      @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

    Page<PageBoardResponseDto> boards = boardService.findAll(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(boards);
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
    boardService.delete(boardId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  // 로그인 한 사용자만 수정 버튼이 보인다는 가정하에, 그래서 userId가 path에 없음.
  @PutMapping("/{boardId}/edit")
  public ResponseEntity<Void> updateBoard(@PathVariable Long boardId,
                                          @RequestBody BoardUpdateRequestDto boardUpdateRequestDto)
      throws IOException {
    boardService.update(boardId, boardUpdateRequestDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}

