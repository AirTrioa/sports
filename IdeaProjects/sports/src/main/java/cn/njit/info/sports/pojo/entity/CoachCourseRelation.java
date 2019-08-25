/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachCourseRelation
 * Author:   Administrator
 * Date:     2019/5/9 11:09
 */
package cn.njit.info.sports.pojo.entity;

import cn.njit.info.sports.pojo.DTO.CoachDTO;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  教练课程关系
 * @author Liuzw
 * @since 2019/5/9
 */
@TableName("COACH_COURSE_RELATION")
public class CoachCourseRelation extends AppBaseEntity{
  /**
   * 教练Id
   */
  private Integer coachId;
  /**
   * 课程Id
   */
  private Integer courseId;
  /**
   * 课程名字
   */
  private String courseName;

  public CoachCourseRelation() {
  }

  public CoachCourseRelation(CoachDTO dto) {
    this.coachId = dto.getId();
    this.courseId = dto.getCourseId();
    this.courseName = dto.getCourseName();
  }

  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }


}