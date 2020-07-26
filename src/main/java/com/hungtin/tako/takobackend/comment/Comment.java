package com.hungtin.tako.takobackend.comment;

import com.hungtin.tako.takobackend.post.model.Post;
import com.hungtin.tako.takobackend.user.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String content;

  @ManyToOne()
  private User user;

  @ManyToOne()
  private Post post;
}
