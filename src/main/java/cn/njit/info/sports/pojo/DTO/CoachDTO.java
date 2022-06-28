/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachDTO
 * Author:   Administrator
 * Date:     2019/5/9 12:24
 */
package cn.njit.info.sports.pojo.DTO;

import cn.njit.info.sports.pojo.entity.Coach;
import com.alibaba.fastjson.JSONObject;

/**
 * 教练DTO(数据传输对象)
 *
 * @author Liuzw
 * @since 2019/5/9
 */
public class CoachDTO extends Coach {
  /**
   * 带学院人数
   */
  private Integer userNum;
  /**
   * 课程Id
   */
  private Integer courseId;
  /**
   * 课程名字
   */
  private String courseName;

  public CoachDTO() {
  }


  public CoachDTO(Coach coach) {
    this.setSex(coach.getSex());
    //Dto的Id就是数据的Id
    this.setId(coach.getId());
    this.setName(coach.getName());
    this.setAge(coach.getAge());
  }

  public Integer getUserNum() {
    return userNum;
  }

  public void setUserNum(Integer userNum) {
    this.userNum = userNum;
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

  public static CoachDTO read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer userNum = jsonObject.getInteger("userNum");
    String courseName = jsonObject.getString("courseName");
    Integer courseId = jsonObject.getInteger("courseId");
    Coach coach = Coach.read(params);
    CoachDTO dto = new CoachDTO(coach);
    dto.setCourseId(courseId);
    dto.setUserNum(userNum);
    dto.setCourseName(courseName);
    return dto;
  }
}