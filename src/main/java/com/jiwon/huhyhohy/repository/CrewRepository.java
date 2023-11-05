package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.crew.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew, Long> {

  Page<Crew> findAll(Pageable pageable);

}
