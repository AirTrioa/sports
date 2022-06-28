/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseInfoService
 * Author:   Administrator
 * Date:     2019/5/18 10:45
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.entity.CourseInfo;

/**
 * 选课服务
 *
 * @author Liuzw
 * @since 2019/5/18
 */
public interface CourseInfoService extends AppBaseService<CourseInfo> {

  /**
   * 批量创建选课关系
   *
   * @param userId    用户Id
   * @param courseIds 课程Id字符串
   * @return int
   */
  int createLists(String userId, String courseIds);
}