/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourseInfo
 * Author:   Administrator
 * Date:     2019/5/20 15:24
 */
package cn.njit.info.sports.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 私教选课信息
 *
 * @author Liuzw
 * @since 2019/5/20
 */
@TableName("PRI_COURSE_INFO")
public class PriCourseInfo extends AppBaseEntity {
  private String userId;
  private Integer priCourseId;
  private String priCourseName;
  private Integer coachId;

  public PriCourseInfo() {
  }

  public PriCourseInfo(Integer id, String userId, Integer priCourseId, String priCourseName, Integer coachId) {
    this.setId(id);
    this.userId = userId;
    this.priCourseId = priCourseId;
    this.priCourseName = priCourseName;
    this.coachId = coachId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getPriCourseId() {
    return priCourseId;
  }

  public void setPriCourseId(Integer priCourseId) {
    this.priCourseId = priCourseId;
  }

  public String getPriCourseName() {
    return priCourseName;
  }

  public void setPriCourseName(String priCourseName) {
    this.priCourseName = priCourseName;
  }

  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }

  public static PriCourseInfo read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("ID");
    String userId = jsonObject.getString("userId");
    Integer priCourseId = jsonObject.getInteger("priCourseId");
    String priCourseName = jsonObject.getString("priCourseName");
    Integer coachId = jsonObject.getInteger("coachId");
    return new PriCourseInfo(id, userId, priCourseId, priCourseName, coachId);
  }
}