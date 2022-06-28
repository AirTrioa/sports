/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: Course
 * Author:   Administrator
 * Date:     2019/5/5 19:51
 */
package cn.njit.info.sports.pojo.entity;

import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 课程对象
 *
 * @author Liuzw
 * @since 2019/5/5
 */
@TableName("COURSE")
public class Course extends AppBaseEntity {
  /**
   * 课程名称
   */
  private String name;
  /**
   * 课程数目
   */
  private Integer lesson;
  /**
   * 时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date date;
  /**
   * 上课时段
   */
  private String timeFrame;
  /**
   * 描述
   */
  private String description;
  /**
   * 开课房间
   */
  private String roomName;

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public Course() {
  }

  public Course(Integer id,
                String name,
                Integer lesson,
                Date date,
                String description,
                String timeFrame,
                String roomId) {
    this.setId(id);
    this.name = name;
    this.lesson = lesson;
    this.date = date;
    this.description = description;
    this.timeFrame = timeFrame;
    this.roomName = roomId;
  }

  public Course(Course course) {
    this.setId(course.getId());
    this.name = course.getName();
    this.lesson = course.getLesson();
    this.date = course.getDate();
    this.description = course.getDescription();
  }

  public String getTimeFrame() {
    return timeFrame;
  }

  public void setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLesson() {
    return lesson;
  }

  public void setLesson(Integer lesson) {
    this.lesson = lesson;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static Course read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("id");
    String name = jsonObject.getString("name");
    Integer lesson = jsonObject.getInteger("lesson");
    Date date = jsonObject.getDate("date");
    String description = jsonObject.getString("description");
    String timeFrame = jsonObject.getString("timeFrame");
    if (EntityProperties.Course.AM.equals(timeFrame)) {
      timeFrame = "上午";
    }
    if (EntityProperties.Course.PM.equals(timeFrame)) {
      timeFrame = "下午";
    }
    if (EntityProperties.Course.NIGHT.equals(timeFrame)) {
      timeFrame = "晚上";
    }
    String roomName = jsonObject.getString("roomName");
    return new Course(id, name, lesson, date, description, timeFrame, roomName);
  }


}