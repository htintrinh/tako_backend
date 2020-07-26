package com.hungtin.tako.takobackend.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

  List<Comment> findAllByPost_Id(Long postId);
}
