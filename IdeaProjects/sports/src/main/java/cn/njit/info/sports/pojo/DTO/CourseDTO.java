/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseDTO
 * Author:   Administrator
 * Date:     2019/5/9 17:50
 */
package cn.njit.info.sports.pojo.DTO;

import java.util.List;

/**
 * 课程数据传输对象
 *
 * @author Liuzw
 * @since 2019/5/9
 */
public class CourseDTO {
  /**
   * 教练Id
   */
  private Integer coachId;
  /**
   * 教练名字
   */
  private String coachName;
  /**
   * 时段
   */
  private String timeFrame;
  /**
   * 教授课程
   */
  private String courseName;
  private String monday;
  private String tuesday;
  private String wednesday;
  private String thursday;
  private String friday;
  private String saturday;

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }

  public String getCoachName() {
    return coachName;
  }

  public void setCoachName(String coachName) {
    this.coachName = coachName;
  }

  public String getTimeFrame() {
    return timeFrame;
  }

  public void setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
  }

  public String getMonday() {
    return monday;
  }

  public void setMonday(String monday) {
    this.monday = monday;
  }

  public String getTuesday() {
    return tuesday;
  }

  public void setTuesday(String tuesday) {
    this.tuesday = tuesday;
  }

  public String getWednesday() {
    return wednesday;
  }

  public void setWednesday(String wednesday) {
    this.wednesday = wednesday;
  }

  public String getThursday() {
    return thursday;
  }

  public void setThursday(String thursday) {
    this.thursday = thursday;
  }

  public String getFriday() {
    return friday;
  }

  public void setFriday(String friday) {
    this.friday = friday;
  }

  public String getSaturday() {
    return saturday;
  }

  public void setSaturday(String saturday) {
    this.saturday = saturday;
  }


  public static CourseDTO buildDTO(String timeFrame, List<CourseDTO> resultList) {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setTimeFrame(timeFrame);
    for (CourseDTO dto : resultList) {
      if (timeFrame.equals(dto.getTimeFrame())) {
        if (null != dto.getMonday())
          courseDTO.setMonday(dto.getMonday());
        if (null != dto.getTuesday())
          courseDTO.setTuesday(dto.getTuesday());
        if (null != dto.getWednesday())
          courseDTO.setWednesday(dto.getWednesday());
        if (null != dto.getTuesday())
          courseDTO.setTuesday(dto.getTuesday());
        if (null != dto.getFriday())
          courseDTO.setFriday(dto.getFriday());
        if (null != dto.getSaturday())
          courseDTO.setSaturday(dto.getSaturday());
      }
    }
    return courseDTO;
  }
}