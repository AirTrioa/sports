/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: HotNum
 * Author:   Administrator
 * Date:     2019/5/20 22:15
 */
package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 热度
 * @author Liuzw
 * @since 2019/5/20
 */
@TableName("HOT_NUM")
public class HotNum{
  private Integer id;
  private Integer userNum;
  private String coachName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserNum() {
    return userNum;
  }

  public void setUserNum(Integer userNum) {
    this.userNum = userNum;
  }

  public String getCoachName() {
    return coachName;
  }

  public void setCoachName(String coachName) {
    this.coachName = coachName;
  }
}