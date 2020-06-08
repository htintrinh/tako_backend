package com.hungtin.tako.takobackend.mail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailNotification {

  private String subject;
  private String body;
  private String recipient;
}
