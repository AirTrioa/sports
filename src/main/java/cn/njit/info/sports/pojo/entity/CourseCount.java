/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseCount
 * Author:   Administrator
 * Date:     2019/5/18 23:45
 */
package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 课程统计实体类
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@TableName("COURSE_COUNT")
public class CourseCount extends AppBaseEntity {
  /**
   * 课程Id
   */
  private Integer courseId;
  /**
   * 教练Id
   */
  private Integer coachId;
  /**
   * 会员Id
   */
  private String userId;
  /**
   * 房间Id
   */
  private Integer roomId;
  /**
   * 课程最大人数(房间的人数限制),私教的该数值为1
   */
  private Integer maxUserNum;

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getRoomId() {
    return roomId;
  }

  public void setRoomId(Integer roomId) {
    this.roomId = roomId;
  }

  public Integer getMaxUserNum() {
    return maxUserNum;
  }

  public void setMaxUserNum(Integer maxUserNum) {
    this.maxUserNum = maxUserNum;
  }
}