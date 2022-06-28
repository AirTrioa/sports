/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: MathUtils
 * Author:   Administrator
 * Date:     2019/5/1 22:05
 */
package cn.njit.info.sports.utils;

import java.util.Random;

/**
 * 数学类型工具类
 *
 * @author Liuzw
 * @since 2019/5/1
 */
public class MathUtils {
  private MathUtils() {
  }

  /**
   * 随机生成userId
   *
   * @return userId
   */
  public static String createUserId() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      Random random = new Random();
      int anInt = random.nextInt(9);
      sb.append(anInt + 1);
    }
    return sb.toString();
  }

  /**
   * 随机生成6位验证码
   *
   * @return authCode
   */
  public static String createAuthCode() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      Random random = new Random();
      int anInt = random.nextInt(9);
      sb.append(anInt + 1);
    }
    return sb.toString();
  }

}