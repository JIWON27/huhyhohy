package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
