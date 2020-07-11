package com.hungtin.tako.takobackend.auth.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {

  private String username;
  private String password;
  private String email;
}
