package com.hungtin.tako.takobackend.auth.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String value;

  private Instant expireAt;

  @ManyToOne private UserAccount userAccount;
}
