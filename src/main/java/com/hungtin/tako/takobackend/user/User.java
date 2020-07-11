package com.hungtin.tako.takobackend.user;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private Date dob;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @OneToOne private UserAccount userAccount;

  public enum Gender {
    MALE,
    FEMALE;
  }
}
