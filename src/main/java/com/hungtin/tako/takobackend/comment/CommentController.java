package com.hungtin.tako.takobackend.comment;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/comments")
  public ResponseEntity<CommentResponse> createComment(
      @RequestBody CreateCommentRequest request) {
    return ResponseEntity.ok(commentService.create(request));
  }

  @GetMapping("/comments")
  public ResponseEntity<List<CommentResponse>> list() {
    return ResponseEntity.ok(commentService.list());
  }
}
