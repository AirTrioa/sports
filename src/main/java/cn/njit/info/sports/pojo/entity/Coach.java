/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: Coach
 * Author:   Administrator
 * Date:     2019/5/5 19:07
 */
package cn.njit.info.sports.pojo.entity;

import cn.njit.info.sports.pojo.DTO.CoachDTO;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.utils.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 教练对象
 *
 * @author Liuzw
 * @since 2019/5/5
 */
@TableName("COACH")
public class Coach extends AppBaseEntity {
  /**
   * 名字
   */
  private String name;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 性别
   */
  private String sex;


  public Coach() {
  }

  public Coach(String name, Integer age, String sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
  }

  public Coach(Integer id, String name, Integer age, String sex) {
    this.setId(id);
    this.name = name;
    this.age = age;
    this.sex = sex;
  }

  public Coach(CoachDTO dto) {
    this.setId(dto.getId());
    this.name = dto.getName();
    this.age = dto.getAge();
    this.sex = dto.getSex();
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public static Coach read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("id");
    String name = jsonObject.getString("name");
    Integer age = jsonObject.getInteger("age");
    String sex = jsonObject.getString("sex");
    if (EntityProperties.Coach.MAN.equals(sex))
      sex = "男";
    if (EntityProperties.Coach.WOMAN.equals(sex))
      sex = "女";
    return new Coach(id, name, age, sex);
  }
}