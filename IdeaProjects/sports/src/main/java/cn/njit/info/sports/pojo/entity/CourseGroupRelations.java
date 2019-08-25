/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupRelations
 * Author:   Administrator
 * Date:     2019/5/7 11:26
 */
package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 课程分组关系
 *
 * @author Liuzw
 * @since 2019/5/7
 */
@TableName("COURSE_GROUP_RELATIONS")
public class CourseGroupRelations extends AppBaseEntity {
  /**
   * 课程Id
   */
  private Integer courseId;
  /**
   * 分组Id
   */
  private Integer groupId;

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }
}