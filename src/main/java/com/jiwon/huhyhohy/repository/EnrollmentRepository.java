package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.Enrollment;
import com.jiwon.huhyhohy.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  @Query("SELECT e FROM Enrollment e where e.crew.id=:crewId")
  List<Enrollment> findApplyUsersByCrewId(@Param("crewId") Long crewId);

}
