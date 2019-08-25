/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: SQLUtilsTest
 * Author:   Administrator
 * Date:     2019/3/25 23:25
 */
package cn.njit.info.sports.utils;

import cn.njit.info.sports.pojo.entity.User;
import org.junit.Test;


/**
 * SQL拼接工具类
 * @author Liuzw
 * @since 2019/3/25
 */
public class SQLUtilsTest {
  @Test
  public void testFindOne(){
    String sql = SQLUtils.getSQLInFindOne(User.class, "id","id");
    System.out.println("拼接后的SQL语句为"+sql);
  }

}