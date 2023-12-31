package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.reply.Reply;
import com.jiwon.huhyhohy.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  Page<Reply> findAll(Pageable pageable);

  List<Reply> findByUser(User user); // 내가 쓴 댓글 보기
}
