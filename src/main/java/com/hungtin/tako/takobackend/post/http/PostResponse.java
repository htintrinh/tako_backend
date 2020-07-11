package com.hungtin.tako.takobackend.post.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {

  private String title;
  private String body;
  private String author;
}
