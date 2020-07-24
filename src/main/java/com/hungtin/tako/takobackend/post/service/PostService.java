package com.hungtin.tako.takobackend.post.service;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.service.AuthService;
import com.hungtin.tako.takobackend.post.http.CreatePostRequest;
import com.hungtin.tako.takobackend.post.http.PostResponse;
import com.hungtin.tako.takobackend.post.http.UpdatePostRequest;
import com.hungtin.tako.takobackend.post.http.mapping.PostMapper;
import com.hungtin.tako.takobackend.post.model.Post;
import com.hungtin.tako.takobackend.post.repo.PostRepo;
import com.hungtin.tako.takobackend.user.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

  private final AuthService authService;
  private final PostRepo postRepo;
  private final PostMapper postMapper;

  @Transactional
  public PostResponse create(CreatePostRequest request) {
    Post post = postMapper.transform(request);
    post.setUser(authService.getCurrentUser().getUser());

    post = postRepo.save(post);

    return postMapper.transform(post);
  }

  @Transactional
  public void delete(Long id) {
    // check if the current user is the author of the post

    if (isCurrentUserOwnPost(id)) {
      throw new IllegalArgumentException("Post id is not valid for delete by current user: " + id);
    }
    postRepo.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAll() {
    return postRepo.findAll().stream().map(postMapper::transform).collect(Collectors.toList());
  }

  @Transactional
  public PostResponse update(UpdatePostRequest request) {
    if (isCurrentUserOwnPost(request.getId())) {
      throw new IllegalArgumentException(
          "Post id is not valid for delete by current user: " + request.getId());
    }

    Post post = postMapper.transform(request);
    return postMapper.transform(postRepo.save(post));
  }


  @Transactional(readOnly = true)
  public boolean isCurrentUserOwnPost(Long postId) {
    UserAccount userAccount = authService.getCurrentUser();
    Optional<Long> optUserId = postRepo.findById(postId)
        .map(Post::getUser)
        .map(User::getId);

    return optUserId.isEmpty()
        || !optUserId.get().equals(userAccount.getUser().getId());
  }
}
