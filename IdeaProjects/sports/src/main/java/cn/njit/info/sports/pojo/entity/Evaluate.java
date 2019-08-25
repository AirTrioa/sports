/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: Evaluate
 * Author:   Administrator
 * Date:     2019/5/19 0:01
 */
package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * 评价
 *
 * @author Liuzw
 * @since 2019/5/19
 */
@TableName("EVALUATE")
public class Evaluate extends AppBaseEntity {
  /**
   * 用户Id
   */
  private String userId;
  /**
   * 课程id
   */
  private Integer courseId;
  /**
   * 教练Id
   */
  private Integer coachId;
  /**
   * 评价等级
   */
  private String rank;
  /**
   * 评价内容
   */
  private String contents;

  /**
   * 是否私有
   */
  private Boolean isPri;


  public Boolean getPri() {
    return isPri;
  }

  public void setPri(Boolean pri) {
    isPri = pri;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

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

  /**
   * 通过rate来获取rank
   *
   * @param rate 前台传递过来的分数
   * @return rank
   */
  public static String buildRank(Integer rate) {
    String rank;
    switch (rate) {
      case 0:
      case 1:
        rank = "D";
        break;
      case 2:
        rank = "C";
        break;
      case 3:
        rank = "B";
        break;
      case 4:
        rank = "A";
        break;
      case 5:
        rank = "S";
        break;
      default:
        rank = "D";
        break;
    }
    return rank;
  }

  /**
   * 把rank转换成rate
   *
   * @param rank 等级
   * @return 评分
   */
  public static Integer convertRate(String rank) {
    Integer rate;
    switch (rank) {
      case "D":
        rate = 1;
        break;
      case "C":
        rate = 2;
        break;
      case "B":
        rate = 3;
        break;
      case "A":
        rate = 4;
        break;
      case "S":
        rate = 5;
        break;
      default:
        rate = 0;
        break;
    }
    return rate;
  }

  /**
   * 提取rank平均分
   *
   * @param evaluateList 列表
   * @return 平均分
   */
  public static Integer getAvgRank(List<Evaluate> evaluateList) {
    int count = 0;
    int xyz = 0;
    for (Evaluate evaluate : evaluateList) {
      Integer index = convertRate(evaluate.getRank());
      count = count + index;
      xyz++;
    }
    if (xyz == 0)
      return 0;
    return count / xyz;
  }
}