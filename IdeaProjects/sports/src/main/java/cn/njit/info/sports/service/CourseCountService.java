/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseCountService
 * Author:   Administrator
 * Date:     2019/5/19 0:27
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.DTO.CourseEvaDTO;
import cn.njit.info.sports.pojo.DTO.CourseRecDTO;
import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.entity.CourseCount;
import cn.njit.info.sports.pojo.model.TreeGroup;

import java.util.List;

/**
 * 课程统计服务
 *
 * @author Liuzw
 * @since 2019/5/19
 */
public interface CourseCountService extends AppBaseService<CourseCount> {
  /**
   * 根据用户Id获取树的值
   *
   * @param userId 用户Id
   * @return 树
   */
  List<TreeGroup> getTreeData(String userId);

  CourseEvaDTO getByCourseId(Integer courseId,String userId);

  List<CourseRecDTO> getRecommendCourse(String userId);

  List<Integer> getGradeByCoachId(Integer coachId);

  String getAvgRank(Integer coachId);

  List<Integer> getAvgGrade();
}