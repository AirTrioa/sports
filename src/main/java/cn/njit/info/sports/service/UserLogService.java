/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogService
 * Author:   Administrator
 * Date:     2019/5/10 20:59
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.entity.UserLogEntity;
import cn.njit.info.sports.pojo.model.TreeGroup;

import java.util.List;

/**
 * 日志服务
 *
 * @author Liuzw
 * @since 2019/5/10
 */
public interface UserLogService extends AppBaseService<UserLogEntity> {

  int createEs(UserLogEntity userLogEntity);

  int create(UserLogEntity userLogEntity);

  Integer getMaxId();

  List<TreeGroup> getTree();

  List<UserLogEntity> getLogsByUserId(String id);

  /**
   * 分页查询
   *
   * @param params 查询参数
   * @return list
   */
  List<UserLogEntity> pageEs(String params);

  /**
   * 更具userId查询操作记录
   *
   * @param id id
   * @return list
   */
  List<UserLogEntity> getLogsByUserIdEs(String id);

  /**
   * 全局搜索
   *
   * @param params 搜索参数
   * @return list
   */
  List<UserLogEntity> search(String params);

  /**
   * 获取七天访问量
   *
   * @return 七天访问量
   */
  List<Integer> getPageViewNum();

  List<UserLogEntity> getAllByMethod(String method);
}