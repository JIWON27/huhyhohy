package com.jiwon.huhyhohy.web.controller.api;

import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.service.BoardService;
import com.jiwon.huhyhohy.service.CommentService;
import com.jiwon.huhyhohy.service.UserService;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentDeleteRequestDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentResponseDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;

    // 댓글 등록
    @PostMapping("/{boardId}")
    public ResponseEntity<Void> createComment(
            @PathVariable Long boardId,
            @RequestBody CommentSaveRequestDto CommentSaveRequestDto
    ) {
        commentService.save(boardId, CommentSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable Long boardId
    ) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findByBoard(boardId);
        return ResponseEntity.ok(commentResponseDtoList);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public Long delete(
            @PathVariable Long commentId,
            @RequestBody CommentDeleteRequestDto deleteRequestDto
    ) {
        return commentService.delete(commentId, deleteRequestDto);
    }

    @PatchMapping("/{commentId}")
    public void update(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto updateRequestDto
    ) {
        commentService.update(commentId, updateRequestDto);
    }

    // 댓글 조회 - "내가 쓴 댓글 보기"를 클릭한 경우
    @GetMapping("/myComments")
    public List<BoardResponseDto> replies(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
//        UserResponseDto loginUser = userService.findByNickname(user.getNickname());

        return commentService.findMyReplies(user.getUserId());
    }
}