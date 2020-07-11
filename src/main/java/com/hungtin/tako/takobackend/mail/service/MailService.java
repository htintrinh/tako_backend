package com.hungtin.tako.takobackend.mail.service;

import com.hungtin.tako.takobackend.mail.MailContentBuilder;
import com.hungtin.tako.takobackend.mail.model.MailNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

  private final JavaMailSender mailSender;
  private final MailContentBuilder mailContentBuilder;

  @Value("${email.from}")
  private String fromAddress;

  public MailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
    this.mailSender = mailSender;
    this.mailContentBuilder = mailContentBuilder;
  }

  @Async
  public void sendMail(MailNotification mailNotification) {
    MimeMessagePreparator preparator =
        mimeMessage -> {
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
          helper.setFrom(fromAddress);
          helper.setSubject(mailNotification.getSubject());
          helper.setTo(mailNotification.getRecipient());
          helper.setText(mailContentBuilder.build(mailNotification.getBody()));
        };
    try {
      mailSender.send(preparator);
      log.info("Send verification email successfully for email " + mailNotification.getRecipient());
    } catch (MailException e) {
      log.error("Send verification email failed: " + e.getMessage());
      throw new RuntimeException("Send verification email failed: " + e.getMessage());
    }
  }
}
