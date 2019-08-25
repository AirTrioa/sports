/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseRecDTO
 * Author:   Administrator
 * Date:     2019/5/19 19:22
 */
package cn.njit.info.sports.pojo.DTO;

import cn.njit.info.sports.pojo.entity.AppBaseEntity;

/**
 * 推荐课程DTO
 *
 * @author Liuzw
 * @since 2019/5/19
 */
public class CourseRecDTO extends AppBaseEntity {
  /**
   * 课程名字
   */
  private String courseName;
  /**
   * 分组名字
   */
  private String groupName;

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}