package com.hungtin.tako.takobackend.post.controller;

import com.hungtin.tako.takobackend.post.http.CreatePostRequest;
import com.hungtin.tako.takobackend.post.http.PostResponse;
import com.hungtin.tako.takobackend.post.http.mapping.PostMapper;
import com.hungtin.tako.takobackend.post.service.PostService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@Slf4j
@AllArgsConstructor
public class PostController {

  private final PostService postService;
  private final PostMapper postMapper;

  @PostMapping("/post")
  public ResponseEntity<PostResponse> create(CreatePostRequest request) {
    return ResponseEntity.ok(postService.create(request));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAll() {
    return ResponseEntity.ok(postService.getAll());
  }
}
