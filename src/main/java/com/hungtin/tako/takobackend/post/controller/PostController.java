package com.hungtin.tako.takobackend.post.controller;

import com.hungtin.tako.takobackend.comment.CommentResponse;
import com.hungtin.tako.takobackend.comment.CommentService;
import com.hungtin.tako.takobackend.post.http.CreatePostRequest;
import com.hungtin.tako.takobackend.post.http.PostResponse;
import com.hungtin.tako.takobackend.post.service.PostService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class PostController {

  private final PostService postService;
  private final CommentService commentService;

  @PostMapping("/posts")
  public ResponseEntity<PostResponse> create(@RequestBody CreatePostRequest request) {
    return ResponseEntity.ok(postService.create(request));
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    postService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAll() {
    return ResponseEntity.ok(postService.getAll());
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<PostResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getById(id).get());
  }

  @GetMapping("/posts/{id}/comments")
  public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable("id") Long postId) {
    return ResponseEntity.ok(commentService.listByPostId(postId));
  }
}
