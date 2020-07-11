package com.hungtin.tako.takobackend.auth.http;

import com.hungtin.tako.takobackend.user.User;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserDetailResponse {

  private final String username;
  private final String email;
  private final String phone;

  private final String name;
  private final Date dob;
  private final User.Gender gender;
}
