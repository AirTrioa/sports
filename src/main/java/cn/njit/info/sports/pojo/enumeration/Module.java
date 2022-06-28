/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: Module
 * Author:   Administrator
 * Date:     2019/5/11 10:24
 */
package cn.njit.info.sports.pojo.enumeration;

/**
 * 项目模块
 *
 * @author Liuzw
 * @since 2019/5/11
 */
public enum Module {
  USER_MGR("userMgr", "人员管理"),
  COURSE_MGR("courseMgr", "课程管理"),
  ROOM_MGR("roomMgr","房间管理"),
  DEVICE_MGR("deviceMgr", "器材管理"),
  DATA_ALY("dataAly", "数据分析"),
  SYS_MGR("sysMgr", "系统管理");

  private String code;

  private String name;

  Module(String code, String name) {
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

  public static Module fromCode(String code) {
    for (Module module : Module.values()) {
      if (module.code.equals(code)) {
        return module;
      }
    }
    return null;
  }

  public static String codeFromValue(String value){
    for (Module module :Module.values()) {
      if(module.name.equals(value)){
        return module.code;
      }
    }
    return null;
  }
}