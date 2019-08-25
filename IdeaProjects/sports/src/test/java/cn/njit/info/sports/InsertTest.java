/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: InsertTest
 * Author:   Administrator
 * Date:     2019/4/28 23:07
 */
package cn.njit.info.sports;

import cn.njit.info.sports.dao.UserDao;
import cn.njit.info.sports.pojo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试类
 * @author Liuzw
 * @since 2019/4/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

  @Autowired
  UserDao userDao;

  @Test
  public void saveTest(){
    User user = new User();
    user.setUsername("123456");
    user.setPassword("123456");
    user.setEmail("123459");
    user.setPhone("123456");
    userDao.insert(user);
  }

  @Test
  public void getTest(){
    System.out.println(userDao.selectList(null));
  }

}