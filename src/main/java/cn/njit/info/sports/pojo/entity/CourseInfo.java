/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseInfo
 * Author:   Administrator
 * Date:     2019/5/18 10:38
 */
package cn.njit.info.sports.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 选课信息表
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@TableName("COURSE_INFO")
public class CourseInfo extends AppBaseEntity {
  private Integer courseId;
  private String userId;

  public CourseInfo() {
  }

  public CourseInfo(Integer id, Integer courseId, String userId) {
    this.setId(id);
    this.courseId = courseId;
    this.userId = userId;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public static CourseInfo read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("id");
    Integer courseId = jsonObject.getInteger("courseId");
    String userId = jsonObject.getString("userId");
    return new CourseInfo(id, courseId, userId);
  }
}