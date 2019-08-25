/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserService
 * Author:   Administrator
 * Date:     2019/4/2 21:17
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.entity.User;
import cn.njit.info.sports.pojo.model.RestResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * user
 *
 * @author Liuzw
 * @since 2019/4/2
 */
public interface UserService extends AppBaseService<User> {


  boolean isUser(Integer id, String password);

  /**
   * 查询详细信息
   *
   * @param id       id
   * @param password 密码
   * @return User
   */
  RestResult details(Integer id, String password);

  /**
   * 全部信息
   *
   * @return all
   */
  List<User> page();

  int create(User user);

  boolean update(Integer id, String params);

  boolean update(String userId,String params);

  /**
   * 读取Excel
   *
   * @param file 文件
   */
  void readExcel(MultipartFile file);

  /**
   * 使用Email找回密码
   *
   * @param email email地址
   * @return true/false
   */
  boolean validEmail(String email);

  /**
   * 重置密码
   *
   * @param email email地址
   * @param user  用户
   */
  void resetPwd(String email, User user);

  /**
   * 验证手机号码
   *
   * @param phone 手机号码
   * @return true/false
   */
  boolean validMessage(String phone);

  /**
   * 重置密码
   *
   * @param obj json字符串
   */
  void resetPwd(String obj);

  /**
   * 修改头像
   *
   * @param userId       用户Id
   * @param urlParam url参数
   * @return true/false
   */
  boolean changePicUrl(String userId, String urlParam);

  User getUserByUserId(String userId);
}