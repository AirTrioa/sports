/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: VerifyUser
 * Author:   Administrator
 * Date:     2019/4/29 21:19
 */
package cn.njit.info.sports.verify;

import cn.njit.info.sports.dao.UserDao;
import cn.njit.info.sports.pojo.entity.User;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户验证
 *
 * @author Liuzw
 * @since 2019/4/29
 */
@Service
public class VerifyUser {
  private static final String USER_ID = "USER_ID";

  private final UserDao userDao;

  @Autowired
  public VerifyUser(UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * user 是否可用(主要判断userId是否可用)
   *
   * @param user obj
   * @return true/false
   */
  public boolean validUserId(User user) {
    QueryWrapper<User> qw2 = new QueryWrapper<>();
    qw2.eq(USER_ID, user.getUserId());
    return null == userDao.selectOne(qw2);
  }

  public boolean validPhone(User user) {
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.eq("PHONE", user.getPhone());
    return null == userDao.selectOne(qw);
  }

  public boolean validEmail(User user) {
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.eq("EMAIL", user.getEmail());
    return null == userDao.selectOne(qw);
  }

}