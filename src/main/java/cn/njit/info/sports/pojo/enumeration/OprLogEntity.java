/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: OprLogEntity
 * Author:   Administrator
 * Date:     2019/5/14 14:43
 */
package cn.njit.info.sports.pojo.enumeration;

/**
 * 日志
 *
 * @author Liuzw
 * @since 2019/5/14
 */
public enum OprLogEntity {
  USER_ID("USER_ID"),
  USER_NAME("USER_NAME"),
  OPERATION("OPERATION"),
  METHOD("METHOD"),
  PARAMS("PARAMS"),
  CREATE_DATE("CREATE_DATE"),
  OPR_MODULE("OPR_MODULE");


  OprLogEntity(String code) {
    this.code = code;
  }

  private String code;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}