/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: Room
 * Author:   Administrator
 * Date:     2019/5/9 20:36
 */
package cn.njit.info.sports.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 房间
 *
 * @author Liuzw
 * @since 2019/5/9
 */
@TableName("ROOM")
public class Room extends AppBaseEntity {
  /**
   * 房间名称
   */
  private String name;
  /**
   * 是否是私教房间
   */
  private Boolean isPri;
  /**
   * 摆放器材的类型-- 即房间类型
   */
  private String type;
  /**
   * 器材数量
   */
  private Integer equipmentNum;
  /**
   * 容纳人数
   */
  private Integer userNum;

  public Room() {
  }

  public Room(String name, Boolean isPri, String type, Integer equipmentNum, Integer userNum) {
    this.name = name;
    this.isPri = isPri;
    this.type = type;
    this.equipmentNum = equipmentNum;
    this.userNum = userNum;
  }
  public Room(Integer id,String name, Boolean isPri, String type, Integer equipmentNum, Integer userNum) {
    this.setId(id);
    this.name = name;
    this.isPri = isPri;
    this.type = type;
    this.equipmentNum = equipmentNum;
    this.userNum = userNum;
  }


  public Boolean isPri() {
    return isPri;
  }

  public void setPri(Boolean pri) {
    isPri = pri;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getEquipmentNum() {
    return equipmentNum;
  }

  public void setEquipmentNum(Integer equipmentNum) {
    this.equipmentNum = equipmentNum;
  }

  public Integer getUserNum() {
    return userNum;
  }

  public void setUserNum(Integer userNum) {
    this.userNum = userNum;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static Room read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    String name = jsonObject.getString("name");
    Integer id = jsonObject.getInteger("id");
    String type = jsonObject.getString("type");
    Boolean isPri = jsonObject.getBoolean("isPri");
    Integer equipmentNum = jsonObject.getInteger("equipmentNum");
    Integer userNum = jsonObject.getInteger("userNum");

    return new Room(id, name,isPri, type, equipmentNum, userNum);
  }
}