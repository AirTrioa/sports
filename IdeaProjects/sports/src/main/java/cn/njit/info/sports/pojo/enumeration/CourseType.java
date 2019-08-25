/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseType
 * Author:   Administrator
 * Date:     2019/5/5 19:12
 */
package cn.njit.info.sports.pojo.enumeration;

/**
 * 课程类型
 *
 * @author Liuzw
 * @since 2019/5/5
 */
public enum CourseType {
  BASIC_THEORY("BASIC_THEORY", "基础理论"),
  PHYSICAL_TRAINING("PHYSICAL_TRAINING", "体能训练"),
  GROUP_AEROBICS("GROUP_AEROBICS", "团体操"),
  RE_HEALTHY_TRAINING("RE_HEALTHY_TRAINING", "康复训练"),
  LOSE_WEIGHT("LOSE_WEIGHT", "减肥训练");
  private String code;
  private String name;

  CourseType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static CourseType fromCode(String code) {
    for (CourseType type : CourseType.values()) {
      if (type.code.equals(code)) {
        return type;
      }
    }
    return null;
  }

}