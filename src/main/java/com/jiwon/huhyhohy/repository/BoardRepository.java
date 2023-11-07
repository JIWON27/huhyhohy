package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

  @Override
  @EntityGraph(attributePaths = {"replies","files", "user"}) //{}안에 있는 것들을 fetch join해서 가져오겠다.
  List<Board> findAll();

  Page<Board> findAll(Pageable pageable);

  List<Board> findByUser(User user); // 내가 쓴 게시물 보기
}
