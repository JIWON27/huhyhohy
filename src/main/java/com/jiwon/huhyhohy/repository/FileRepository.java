package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.file.ImgFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<ImgFile, Long> {

}
