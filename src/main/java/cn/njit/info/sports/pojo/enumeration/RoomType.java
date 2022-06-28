/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RoomType
 * Author:   Administrator
 * Date:     2019/5/10 11:16
 */
package cn.njit.info.sports.pojo.enumeration;

/**
 * 房间类型
 *
 * @author Liuzw
 * @since 2019/5/10
 */
public enum RoomType {
  POWER("power", "力量训练"),
  PLIABLE("pliable", "柔韧训练"),
  DANCE("dance", "舞蹈教室"),
  LOUNGE("lounge", "休息室"),
  STAFF_ONLY("staffOnly", "员工专用"),
  PC_ROOM("pcRoom", "电脑教室"),
  CLINIC("clinic", "医务室"),
  SHOWERS("showers", "淋浴室"),
  LOCKER_ROOM("lockerRoom", "更衣室");


  private String code;
  private String name;

  RoomType(String code, String name) {
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

  public static RoomType fromCode(String code) {
    for (RoomType room : RoomType.values()) {
      if (room.code.equals(code)) {
        return room;
      }
    }
    return null;
  }

  public static String fromName(String name) {
    for (RoomType type : RoomType.values()) {
      if (type.name.equals(name)) {
        return type.code;
      }
    }
    return null;
  }
}