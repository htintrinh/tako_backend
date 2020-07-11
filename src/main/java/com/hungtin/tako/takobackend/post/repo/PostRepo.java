package com.hungtin.tako.takobackend.post.repo;

import com.hungtin.tako.takobackend.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {}
