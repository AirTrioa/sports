/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: BaseEntity
 * Author:   Administrator
 * Date:     2019/3/20 10:40
 */
package cn.njit.info.sports.pojo;

import java.util.Map;

/**
 * 基本实体
 * @author Liuzw
 * @since 2019/3/20
 */
public class BaseEntity {
  /**
   * PK
   */
  private Integer id;

  /**
   * 企业id
   */
  private Integer enterPriseId;

  public BaseEntity() {
  }

  public BaseEntity(Integer id, Integer enterPriseId) {
    this.id = id;
    this.enterPriseId = enterPriseId;
  }

  public Integer getEnterPriseId() {
    return enterPriseId;
  }

  public void setEnterPriseId(Integer enterPriseId) {
    this.enterPriseId = enterPriseId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}