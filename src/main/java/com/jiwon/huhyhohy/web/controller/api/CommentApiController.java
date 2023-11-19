package com.jiwon.huhyhohy.web.controller.api;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.BoardService;
import com.jiwon.huhyhohy.service.CommentService;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.comment.CommentResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class ReplyApiController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;

    // 댓글 등록
    @PostMapping("/create/{boardId}")
    public ResponseEntity<Void> createComment(
            @PathVariable Long boardId,
            @ModelAttribute CommentSaveRequestDto CommentSaveRequestDto,
            HttpSession session
    ) {
        User loginUser = (User) session.getAttribute("loginUser");
        commentService.save(boardId, loginUser.getNickname(), CommentSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable Long boardId
    ) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findByBoard(boardId);
        return ResponseEntity.ok(commentResponseDtoList);
    }

//    // 댓글 삭제
//    @DeleteMapping("/boards/reply/{replyId}")
//    public String delete(@PathVariable Long replyId) {
//        // 게시판의 아이디가 아니라 댓글의 아이디 여야함.
//        // board로 어떻게 보내지,, -> replyId를 사용해서 쿼리문 조인해서 보드 아이디 가져오게함. 지금은 생각나는 방법이 이거밖에 없음..
//        Long boardId = replyService.delete(replyId);
//        return "redirect:/boards/" + boardId;
//    }
//
//    // 댓글 수정
//    @PostMapping("/boards/edit/reply/{replyId}")
//    public String update(@PathVariable Long replyId, @ModelAttribute ReplyUpdateRequestDto updateRequestDto) {
//        Long boardId = replyService.update(replyId, updateRequestDto);
//        return "redirect:/boards/" + boardId;
//    }
//
//    // 댓글 조회 - "내가 쓴 댓글 보기"를 클릭한 경우
//    @GetMapping("/reply/myReplies")
//    public String replies(RedirectAttributes redirectAttributes, HttpSession session) {
//        User user = (User) session.getAttribute("loginUser");
//        UserResponseDto loginUser = userService.findByNickname(user.getNickname());
//
//        List<BoardResponseDto> CommentedBoards = replyService.findMyReplies(user.getNickname()); // 댓글 단 글
//        redirectAttributes.addFlashAttribute("commentedBoards", CommentedBoards);
//        return "redirect:/my-page";
//    }
}