/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: AppBaseEntity
 * Author:   Administrator
 * Date:     2019/5/5 19:09
 */
package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 应用基础对象
 * @author Liuzw
 * @since 2019/5/5
 */
public class AppBaseEntity {
  @TableId("ID")
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}