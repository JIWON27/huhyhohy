package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.crew.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
