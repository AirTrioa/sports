/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: SmsController
 * Author:   Administrator
 * Date:     2019/3/30 14:41
 */
package cn.njit.info.sports.controller.open_api;

import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.utils.JavaSmsUtils;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用短信api
 *
 * @author Liuzw
 * @since 2019/3/30
 */
@RequestMapping("/sports/message")
@RestController
public class SmsController {
  /**
   * Phone
   */
  private static final String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

  /**
   * 验证码（6位数）
   */
  private static final String VALUE_REG = "^\\d{6}$";

  @GetMapping("/{number}/{value}")
  public RestResult sendMessage(@PathVariable String number,
                                @PathVariable String value) {
    boolean num = number.matches(PHONE_NUMBER_REG);
    if (!num) {
      return RestResultUtils.returnError("号码格式不对!");
    }
    boolean valueReg = value.matches(VALUE_REG);
    if (!valueReg) {
      return RestResultUtils.returnError("验证码格式为6位数字!");
    }
    String message = JavaSmsUtils.sendMessage(number, "用户", value);
    return RestResultUtils.returnSuccess(message);
  }
}