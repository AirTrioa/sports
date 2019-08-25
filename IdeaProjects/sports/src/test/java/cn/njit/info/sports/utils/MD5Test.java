/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: MD5Test
 * Author:   Administrator
 * Date:     2019/3/26 22:41
 */
package cn.njit.info.sports.utils;

import org.junit.Test;

/**
 * test
 * @author Liuzw
 * @since 2019/3/26
 */
public class MD5Test {
  @Test
  public void testMd5(){
    String password="123456";
    String encrypt = MD5Utils.encrypt(password);
    String encrypt2 = MD5Utils.encrypt(password);
    System.out.println(encrypt);
    System.out.println(encrypt2);
  }
}