package com.jiwon.huhyhohy.domain;

import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "likes")
public class Like extends BaseTimeEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "crew_id")
  private Crew crew;
}
