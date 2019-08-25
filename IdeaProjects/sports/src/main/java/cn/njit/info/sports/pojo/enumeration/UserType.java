/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserType
 * Author:   Administrator
 * Date:     2019/5/1 21:50
 */
package cn.njit.info.sports.pojo.enumeration;

/**
 * 用户类型
 * @author Liuzw
 * @since 2019/5/1
 */

public enum UserType {
  NORMAL("normal","会员"),
  ADMIN("admin","管理员"),
  COMPANY("company","企业"),
  COACH("coach","教练");
  private String code;
  private String name;

  UserType(String code, String name) {
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

  public static UserType fromCode(String code) {
    for (UserType healthStatus : UserType.values()) {
      if (healthStatus.code.equals(code)) {
        return healthStatus;
      }
    }
    return null;
  }
}