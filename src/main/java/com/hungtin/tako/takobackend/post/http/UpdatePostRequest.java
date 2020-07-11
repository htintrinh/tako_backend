package com.hungtin.tako.takobackend.post.http;

import lombok.Data;

@Data
public class UpdatePostRequest {

  private final Long id;

  private final String title;

  private final String body;
}
