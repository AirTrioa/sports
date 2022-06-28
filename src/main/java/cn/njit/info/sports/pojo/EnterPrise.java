/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: EnterPriseMapper
 * Author:   Administrator
 * Date:     2019/3/24 12:55
 */
package cn.njit.info.sports.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 企业实体类
 * @author Liuzw
 * @since 2019/3/24
 */
@TableName("ENTER_PRISE")
public class EnterPrise{
  /**
   * PK
   */
  private Integer id;
  /**
   * 企业编码
   */
  private String code;
  /**
   * 企业名字
   */
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
}