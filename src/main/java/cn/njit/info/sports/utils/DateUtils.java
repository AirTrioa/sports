/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: DateUtils
 * Author:   Administrator
 * Date:     2019/5/6 14:02
 */
package cn.njit.info.sports.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Liuzw
 * @since 2019/5/6
 */
public class DateUtils {
  private DateUtils() {
  }

  public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

  public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /**
   * 将短时间格式时间转换为字符串 yyyy-MM-dd
   */
  public static String dateToStr(Date dateDate) {
    SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);
    return formatter.format(dateDate);
  }

  public static Date dateToDefaultDate(Date dateDate) {
    String str = dateToStr(dateDate);
    return strToDate(str);
  }

  public static String dateToPreStr(Date dateDate) {
    SimpleDateFormat formatter = new SimpleDateFormat(TIME_PATTERN);
    return formatter.format(dateDate);
  }

  public static Date dateToPreDate(Date dateDate) {
    String str = dateToPreStr(dateDate);
    return strToDate(str, TIME_PATTERN);
  }

  /**
   * 将短时间格式字符串转换为时间 yyyy-MM-dd
   */
  public static Date strToDate(String strDate) {
    return strToDate(strDate, DEFAULT_PATTERN);
  }

  public static Date strToDate(String strDate, String pattern) {
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    ParsePosition pos = new ParsePosition(0);
    return formatter.parse(strDate, pos);
  }

  public static long getDay(Date date) {
    int year = date.getYear();
    int month = date.getMonth();
    int date1 = date.getDate();
    return 365 * year + 30 * month + date1;
  }

  /**
   * 获取现在天数和参数天数的差
   */
  public static long getDiffNow(Date date) {
    Date now = new Date();
    return getDay(now) - getDay(date);
  }
}