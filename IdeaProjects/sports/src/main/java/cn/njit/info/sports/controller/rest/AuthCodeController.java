/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: AuthCodeController
 * Author:   Administrator
 * Date:     2019/5/15 11:04
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.utils.AuthCodeUtils;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 验证码控制器
 *
 * @author Liuzw
 * @since 2019/5/15
 */
@RestController
@RequestMapping("sports/auth_code")
public class AuthCodeController {

  @GetMapping(name = "获取验证码", value = "/img")
  public String createAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setDateHeader("Expires", 0);
    // 禁止图像缓存
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.setHeader("Pragma", "no-cache");
    response.setContentType("image/jpeg");

    OutputStream os = response.getOutputStream();
    //返回验证码和图片的map
    Map<String, Object> map = AuthCodeUtils.getImageCode(86, 37, os);
    String simpleCaptcha = "simpleCaptcha";
    request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
    request.getSession().setAttribute("codeTime", System.currentTimeMillis());
    try {
      ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
    } catch (IOException e) {
      return "";
    } finally {
      if (os != null) {
        os.flush();
        os.close();
      }
    }
    return null;
  }

  @GetMapping(name = "验证验证码", value = "")
  public RestResult checkCode(HttpServletRequest request,
                              HttpSession session,
                              @RequestParam(value = "checkCode", required = false) String checkCode) {

    // 获得验证码对象
    Object cko = session.getAttribute("simpleCaptcha");
    if (cko == null) {
      return RestResultUtils.returnError(("请输入验证码！"));
    }
    String captcha = cko.toString();
    // 判断验证码输入是否正确
    if (StringUtils.isEmpty(checkCode) || !(checkCode.equalsIgnoreCase(captcha))) {
      return RestResultUtils.returnError(("验证码错误，请重新输入！"));
    }
    return RestResultUtils.returnMessage("验证通过！");
  }

}