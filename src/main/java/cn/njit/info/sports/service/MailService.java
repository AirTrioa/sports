/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: MailService
 * Author:   Administrator
 * Date:     2019/4/12 23:36
 */
package cn.njit.info.sports.service;

/**
 * 邮件服务
 * @author Liuzw
 * @since 2019/4/12
 */
public interface MailService {
  void sendSimpleMail(String to, String subject, String content);


}