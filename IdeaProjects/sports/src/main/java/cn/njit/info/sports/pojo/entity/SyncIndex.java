/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: SyncIndex
 * Author:   Administrator
 * Date:     2019/5/14 15:45
 */
package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 同步记录
 *
 * @author Liuzw
 * @since 2019/5/14
 */
@TableName("SYNC_INDEX")
public class SyncIndex {
  @TableId
  private Integer id;

  private String type;

  public SyncIndex() {
  }

  public SyncIndex(Integer id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}