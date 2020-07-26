package com.hungtin.tako.takobackend.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

  private Long postId;
  private String content;
}
