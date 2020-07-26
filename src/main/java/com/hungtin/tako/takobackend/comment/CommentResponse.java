package com.hungtin.tako.takobackend.comment;

import com.hungtin.tako.takobackend.user.http.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

  public Long id;
  public String content;
  public UserResponse user;
}
