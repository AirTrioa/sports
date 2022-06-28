/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseEvaDTO
 * Author:   Administrator
 * Date:     2019/5/19 13:52
 */
package cn.njit.info.sports.pojo.DTO;

import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.entity.PriCourse;

import java.util.Date;

/**
 * 课程_评价DTO
 *
 * @author Liuzw
 * @since 2019/5/19
 */
public class CourseEvaDTO extends Course {
  /**
   * 教练名字
   */
  private String coachName;
  /**
   * 评价等级
   */
  private String rank;
  /**
   * 评价内容
   */
  private String contents;

  public CourseEvaDTO(String coachId, String rank, String contents) {
    this.coachName = coachId;
    this.rank = rank;
    this.contents = contents;
  }

  public CourseEvaDTO(Integer id, String name, Integer lesson, Date date, String description, String timeFrame, String roomId, String coachId, String rank, String contents) {
    super(id, name, lesson, date, description, timeFrame, roomId);
    this.coachName = coachId;
    this.rank = rank;
    this.contents = contents;
  }

  public CourseEvaDTO(Course course, String coachId, String rank, String contents) {
    this.setTimeFrame(course.getTimeFrame());
    this.setRoomName(course.getRoomName());
    this.setDate(course.getDate());
    this.setId(course.getId());
    this.setDescription(course.getDescription());
    this.setName(course.getName());
    this.setLesson(course.getLesson());
    this.coachName = coachId;
    this.rank = rank;
    this.contents = contents;
  }

  public CourseEvaDTO(PriCourse course, String coachId, String rank, String contents) {
    this.setTimeFrame(course.getTimeFrame());
    this.setRoomName(course.getRoomName());
    this.setDate(course.getDate());
    this.setId(course.getId());
    this.setDescription(course.getDescription());
    this.setName(course.getName());
    this.setLesson(course.getLesson());
    this.coachName = coachId;
    this.rank = rank;
    this.contents = contents;
  }

  public CourseEvaDTO(Course course) {
    super(course);
  }


  public String getCoachName() {
    return coachName;
  }

  public void setCoachName(String coachName) {
    this.coachName = coachName;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }
}