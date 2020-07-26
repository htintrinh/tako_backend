package com.hungtin.tako.takobackend.comment;

import com.hungtin.tako.takobackend.auth.service.AuthService;
import com.hungtin.tako.takobackend.post.model.Post;
import com.hungtin.tako.takobackend.user.mapper.UserMapper;
import javax.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapper {

  private final UserMapper userMapper;
  private final AuthService authService;
  private final EntityManager entityManager;

  public Comment transform(CreateCommentRequest request) {
    return Comment.builder()
        .content(request.getContent())
        .user(authService.getCurrentUser().getUser())
        .post(entityManager.getReference(Post.class, request.getPostId()))
        .build();
  }

  public CommentResponse transform(Comment comment) {
    return CommentResponse.builder()
        .content(comment.getContent())
        .id(comment.getId())
        .user(userMapper.transform(comment.getUser()))
        .build();
  }
}
