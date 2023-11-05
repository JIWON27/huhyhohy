package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  Page<Reply> findAll(Pageable pageable);
}
