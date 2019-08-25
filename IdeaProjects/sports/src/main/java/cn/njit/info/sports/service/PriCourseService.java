/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourseService
 * Author:   Administrator
 * Date:     2019/5/18 21:56
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.DTO.CourseDTO;
import cn.njit.info.sports.pojo.entity.PriCourse;
import cn.njit.info.sports.pojo.model.TreeGroup;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 私教课程服务
 *
 * @author Liuzw
 * @since 2019/5/18
 */
public interface PriCourseService extends AppBaseService<PriCourse> {

  /**
   * 获取所有的私教课程
   *
   * @return all
   */
  List<TreeGroup> getTreeData();

  /**
   * 根据教练Id获取私教课程
   *
   * @param coachId 教练Id
   * @return 私教课程
   */
  List<TreeGroup> getTreeData(Integer coachId);

  /**
   * 获取私教课程表
   *
   * @param coachId 教练Id
   * @return 课程表
   */
  List<CourseDTO> getPriCourseTable(Integer coachId);

  void deleteByName(String courseName,Integer coachId);

  int choosePriCourse(String params);
}