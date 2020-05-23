package com.hungtin.tako.takobackend.auth.model;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String value;

  @ManyToOne
  private UserAccount userAccount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public UserAccount getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
  }
}
