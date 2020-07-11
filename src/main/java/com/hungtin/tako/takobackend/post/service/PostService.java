package com.hungtin.tako.takobackend.post.service;

import com.hungtin.tako.takobackend.post.http.CreatePostRequest;
import com.hungtin.tako.takobackend.post.http.PostResponse;
import com.hungtin.tako.takobackend.post.http.UpdatePostRequest;
import com.hungtin.tako.takobackend.post.http.mapping.PostMapper;
import com.hungtin.tako.takobackend.post.model.Post;
import com.hungtin.tako.takobackend.post.repo.PostRepo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

  private final PostRepo postRepo;
  private final PostMapper postMapper;

  @Transactional
  public PostResponse create(CreatePostRequest request) {
    return postMapper.transform(postRepo.save(postMapper.transform(request)));
  }

  public void delete(Long id) {
    postRepo.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAll() {
    return postRepo.findAll().stream().map(postMapper::transform).collect(Collectors.toList());
  }

  @Transactional
  public PostResponse update(UpdatePostRequest request) {
    Post post = postMapper.transform(request);
    return postMapper.transform(postRepo.save(post));
  }
}
