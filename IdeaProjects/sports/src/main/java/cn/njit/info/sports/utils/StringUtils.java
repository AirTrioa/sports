/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: StringUtils
 * Author:   Administrator
 * Date:     2019/3/25 23:14
 */
package cn.njit.info.sports.utils;

/**
 * 字符串工具类
 *
 * @author Liuzw
 * @since 2019/3/25
 */
public class StringUtils {
  /**
   * 下划线命名转为驼峰命名
   *
   * @param para 下划线命名的字符串
   */
  public static String underlineToHump(String para) {
    StringBuilder result = new StringBuilder();
    String a[] = para.split("_");
    for (String s : a) {
      if (!para.contains("_")) {
        result.append(s);
        continue;
      }
      if (result.length() == 0) {
        result.append(s.toLowerCase());
      } else {
        result.append(s.substring(0, 1).toUpperCase());
        result.append(s.substring(1).toLowerCase());
      }
    }
    return result.toString();
  }


  /**
   * 驼峰命名转为下划线命名
   *
   * @param para 驼峰命名的字符串
   * @return
   */
  public static String humpToUnderline(String para) {
    StringBuilder sb = new StringBuilder(para);
    int temp = 0;//定位
    if (!para.contains("_")) {
      for (int i = 0; i < para.length(); i++) {
        if (Character.isUpperCase(para.charAt(i))) {
          sb.insert(i + temp, "_");
          temp += 1;
        }
      }
    }
    return sb.toString().toUpperCase();
  }

  /**
   * 类名转换成表名
   *
   * @param className 类名
   * @return 表名
   */
  public static String classNameToTableName(String className) {
    String name = humpToUnderline(className);
    return name.substring(1, name.length());
  }

  /**
   * 判断是否为空
   *
   * @param str 字符串
   * @return true/false
   */
  public static boolean isBlank(String str) {
    return null == str || "".equals(str.trim());
  }

  public static boolean isNotBlank(String str) {
    return null != str && !"".equals(str.trim());
  }
}