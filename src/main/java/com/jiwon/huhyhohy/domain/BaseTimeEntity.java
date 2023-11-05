package com.jiwon.huhyhohy.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate; // 등록일

  @LastModifiedDate
  private LocalDateTime lastModifiedDate; // 수정일

  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    createdDate = now;
    lastModifiedDate = now;
  }
  @PreUpdate
  public void preUpdate(){
    LocalDateTime now = LocalDateTime.now();
    lastModifiedDate = now;
  }

  /*@CreatedBy
  @Column(updatable = false)
  private String createBy; // 등록자

  @LastModifiedBy
  private String lastModifiedBy; // 수정자*/

}