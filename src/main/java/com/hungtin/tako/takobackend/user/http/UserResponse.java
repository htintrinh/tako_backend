package com.hungtin.tako.takobackend.user.http;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

  private Long id;
  private String name;
  private Date dob;
  private String gender;
  private String phone;
}
