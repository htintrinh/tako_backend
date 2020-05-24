package com.hungtin.tako.takobackend.auth.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {

  private String username;
  private String password;
}
