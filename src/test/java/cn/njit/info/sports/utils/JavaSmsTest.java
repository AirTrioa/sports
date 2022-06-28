/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: JavaSmsTest
 * Author:   Administrator
 * Date:     2019/3/30 14:27
 */
package cn.njit.info.sports.utils;

import org.junit.Test;

/**
 *
 * @author Liuzw
 * @since 2019/3/30
 */
public class JavaSmsTest {
  @Test
  public void testSms(){
    String phone="15195963003";
    String name = "螺纹";
    String value = "123555";
    JavaSmsUtils.sendMessage(phone,name,value);
  }
}