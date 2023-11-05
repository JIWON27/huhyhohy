package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
