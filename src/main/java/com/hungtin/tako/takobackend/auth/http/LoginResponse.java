package com.hungtin.tako.takobackend.auth.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LoginResponse {

  private final String tokenValue;

}
