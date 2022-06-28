/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: MailController
 * Author:   Administrator
 * Date:     2019/4/12 23:48
 */
package cn.njit.info.sports.controller.open_api;

import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.MailService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuzw
 * @since 2019/4/12
 */
@RestController
@RequestMapping("/mail")
public class MailController {
  private final MailService meService;

  @Autowired
  public MailController(MailService meService) {
    this.meService = meService;
  }

  /**
   * 邮件验证接口
   *
   * @param to      发送到
   * @param subject 主题
   * @param content 正文
   * @return Success
   */
  @GetMapping("/{to}/{subject}/{content}")
  public RestResult sendMail(@PathVariable String to,
                             @PathVariable String subject,
                             @PathVariable String content) {
    meService.sendSimpleMail(to, subject, content);
    return RestResultUtils.returnSuccess();
  }

}