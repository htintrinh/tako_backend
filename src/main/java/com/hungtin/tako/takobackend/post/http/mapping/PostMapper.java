package com.hungtin.tako.takobackend.post.http.mapping;

import com.hungtin.tako.takobackend.post.http.CreatePostRequest;
import com.hungtin.tako.takobackend.post.http.PostResponse;
import com.hungtin.tako.takobackend.post.http.UpdatePostRequest;
import com.hungtin.tako.takobackend.post.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

  public PostResponse transform(Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .body(post.getBody())
        .author(post.getUser().getName())
        .build();
  }

  public Post transform(CreatePostRequest request) {
    return Post.builder().title(request.getTitle()).body(request.getBody()).build();
  }

  public Post transform(UpdatePostRequest request) {
    return Post.builder()
        .title(request.getTitle())
        .id(request.getId())
        .body(request.getBody())
        .build();
  }
}
