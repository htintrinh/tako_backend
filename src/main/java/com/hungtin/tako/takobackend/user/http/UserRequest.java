package com.hungtin.tako.takobackend.user.http;

import com.hungtin.tako.takobackend.user.User;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

  private String name;
  private Date dob;
  private User.Gender gender;
  private String phone;
}
