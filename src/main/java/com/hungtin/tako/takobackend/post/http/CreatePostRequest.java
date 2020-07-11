package com.hungtin.tako.takobackend.post.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRequest {

  private final String title;
  private final String body;
}
