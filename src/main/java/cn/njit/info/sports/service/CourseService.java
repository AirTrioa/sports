/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseService
 * Author:   Administrator
 * Date:     2019/5/5 20:30
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.DTO.CourseDTO;
import cn.njit.info.sports.pojo.entity.Course;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程服务
 *
 * @author Liuzw
 * @since 2019/5/5
 */
public interface CourseService extends AppBaseService<Course> {
  /**
   * 分页查询
   *
   * @param params 参数
   * @return CourseList
   */
  List<Course> page(String params);

  /**
   * 解析Excel文件
   *
   * @param file MultipartFile
   */
  void readExcel(MultipartFile file);

  /**
   * 根据GroupId获取全部
   *
   * @return 课程List
   */
  List<Course> getAllByGroupId(Integer groupId);

  /**
   * 根据Ids获取List
   *
   * @param ids ids
   * @return List
   */
  List<Course> getAllByIds(List<Integer> ids);

  /**
   * 获取所有未挂载关系的课程
   *
   * @return 课程列表
   */
  List<Course> getCoursesWithoutRelations();

  /**
   * 根据教练名字获取所有课程
   *
   * @param coachName 课程名字
   * @return courseList
   */
  List<Course> getAllByCoachName(String coachName);


  /**
   * 根据教练名字构建课程表
   *
   * @param coachName 教练Id
   * @return List<CourseDTO>
   */
  List<CourseDTO> buildCoachCourseDataByCoachName(String coachName);

  /**
   * 根据名字获取课程
   *
   * @param name 课程名
   * @return 课程
   */
  Course getCourseByName(String name);


  Course getCourseByCoachId(Integer coachId);

  /**
   * 构建私教课程表
   *
   * @param coachId 教练Id
   * @return 课程表
   */
  List<CourseDTO> getPrivateCourseTable(Integer coachId);
}