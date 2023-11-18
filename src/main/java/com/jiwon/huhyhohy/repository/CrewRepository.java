package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.crew.Category;
import com.jiwon.huhyhohy.domain.crew.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CrewRepository extends JpaRepository<Crew, Long> {

  Page<Crew> findAll(Pageable pageable);

  @Query("SELECT c FROM Crew c " +
      "ORDER BY SIZE(c.likes) DESC")
  Page<Crew> findHotCrews(Pageable pageable);

  @Query("SELECT c FROM Crew c WHERE c.category=:category")//WHERE c.category =: category 공백떄문에 검색안되서 공백 제거함..
  Page<Crew> findAllByCategory(@Param("category") Category category, Pageable pageable);

}
