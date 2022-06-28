/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: mailServiceImpl
 * Author:   Administrator
 * Date:     2019/4/12 23:37
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Liuzw
 * @since 2019/4/12
 */
@Service
public class MailServiceImpl implements MailService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final JavaMailSender mailSender;

  @Autowired
  public MailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Value("${mail.fromMail.addr}")
  private String from;

  @Override
  public void sendSimpleMail(String to, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);

    try {
      mailSender.send(message);
      logger.info("简单邮件已经发送。");
    } catch (Exception e) {
      logger.error("发送简单邮件时发生异常！", e);
    }

  }
}