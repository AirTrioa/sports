/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourse
 * Author:   Administrator
 * Date:     2019/5/18 21:14
 */
package cn.njit.info.sports.pojo.entity;

import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 私教课程
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@TableName("PRI_COURSE")
public class PriCourse extends Course {
  /**
   * 执教教练id
   */
  private Integer coachId;

  private Integer week;

  public Integer getWeek() {
    return week;
  }

  public void setWeek(Integer week) {
    this.week = week;
  }

  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }

  public PriCourse() {
  }

  public PriCourse(Integer id,
                   String name,
                   Integer lesson,
                   Date date,
                   String description,
                   String timeFrame,
                   Integer coachId,
                   String roomId,
                   Integer week) {
    super(id, name, lesson, date, description, timeFrame, roomId);
    this.coachId = coachId;
    this.week = week;
  }

  public static PriCourse read(String params) {
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
    Integer coachId = jsonObject.getInteger("coachId");
    Integer week = jsonObject.getInteger("week");
    return new PriCourse(id, name, lesson, date, description, timeFrame, coachId, roomName, week);
  }

}