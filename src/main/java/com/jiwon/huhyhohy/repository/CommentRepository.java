package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.comment.Comment;
import com.jiwon.huhyhohy.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Page<Comment> findAll(Pageable pageable);

  List<Comment> findByUser(User user); // 내가 쓴 댓글 보기
}
