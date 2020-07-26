package com.hungtin.tako.takobackend.comment;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CommentService {

  private final CommentRepo commentRepo;
  private final CommentMapper commentMapper;

  @Transactional
  public CommentResponse create(CreateCommentRequest request) {
    Comment comment = commentMapper.transform(request);
    comment = commentRepo.save(comment);
    return commentMapper.transform(comment);
  }

  @Transactional
  public List<CommentResponse> list() {
    return commentRepo.findAll().stream()
        .map(commentMapper::transform)
        .collect(toList());
  }

  @Transactional
  public List<CommentResponse> listByPostId(Long postId) {
    return commentRepo.findAllByPost_Id(postId).stream()
        .map(commentMapper::transform)
        .collect(toList());
  }
}
