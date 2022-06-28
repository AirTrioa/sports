/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: MD5Utils
 * Author:   Administrator
 * Date:     2019/3/26 22:37
 */
package cn.njit.info.sports.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author Liuzw
 * @since 2019/3/26
 */
public class MD5Utils {
  private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

  private MD5Utils() {
  }

  /**
   * 加密算法
   *
   * @param plainText 加密前的str
   * @return 加密后的str
   */
  public static String encrypt(String plainText) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(plainText.getBytes());
      byte[] b = md.digest();
      int i;

      StringBuilder builder = new StringBuilder();
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset];
        if (i < 0)
          i += 256;
        if (i < 16)
          builder.append("0");
        builder.append(Integer.toHexString(i));
      }
      //32位加密
      return builder.toString();
      // 16位的加密
      //return buf.toString().substring(8, 24);
    } catch (NoSuchAlgorithmException e) {
      logger.error("出现异常:{}", e);
      return null;
    }
  }
}