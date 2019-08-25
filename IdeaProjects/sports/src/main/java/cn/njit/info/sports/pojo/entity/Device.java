/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: Device
 * Author:   Administrator
 * Date:     2019/5/10 13:00
 */
package cn.njit.info.sports.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 设备器材类
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@TableName("DEVICE")
public class Device extends AppBaseEntity {
  /**
   * 设备类型
   */
  private String type;
  /**
   * 设备数量
   */
  private Integer num;
  /**
   * 房间Id
   */
  private Integer roomId;

  public Device() {
  }

  public Device(Integer id, String type, Integer num, Integer roomId) {
    this.setId(id);
    this.type = type;
    this.num = num;
    this.roomId = roomId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Integer getRoomId() {
    return roomId;
  }

  public void setRoomId(Integer roomId) {
    this.roomId = roomId;
  }

  public static Device read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("id");
    String type = jsonObject.getString("type");
    Integer num = jsonObject.getInteger("num");
    Integer roomId = jsonObject.getInteger("roomId");
    return new Device(id, type, num, roomId);
  }
}