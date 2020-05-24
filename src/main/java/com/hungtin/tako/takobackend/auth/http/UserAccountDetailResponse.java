package com.hungtin.tako.takobackend.auth.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserAccountDetailResponse {

  private final String username;
  private final String email;
  private final String phone;
}
