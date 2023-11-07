package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.crew.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CrewRepository extends JpaRepository<Crew, Long> {

  Page<Crew> findAll(Pageable pageable);

  @Query("SELECT c FROM Crew c " +
      "ORDER BY SIZE(c.likes) DESC")
  Page<Crew> findHotCrews(Pageable pageable);

}
