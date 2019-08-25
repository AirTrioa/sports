/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogDTO
 * Author:   Administrator
 * Date:     2019/5/14 15:30
 */
package cn.njit.info.sports.pojo.DTO;

import cn.njit.info.sports.pojo.entity.UserLogEntity;
import cn.njit.info.sports.utils.DateUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 操作日志存进ES
 *
 * @author Liuzw
 * @since 2019/5/14
 */
public class UserLogDTO {
  @TableId("ID")
  private Integer id;
  /**
   * 操作Id
   */
  private Integer userId;
  /**
   * 操作人
   */
  private String username;
  /**
   * 操作
   */
  private String operation;
  /**
   * 方法名
   */
  private String method;
  /**
   * 参数
   */
  private String params;
  /**
   * 操作时间
   */
  private String createDate;
  /**
   * 操作模块
   */
  private String oprModule;

  public UserLogDTO() {
  }


  public UserLogDTO(UserLogEntity userLogEntity) {
    this.id = userLogEntity.getId();
    this.userId = userLogEntity.getUserId();
    this.username = userLogEntity.getUsername();
    this.operation = userLogEntity.getOperation();
    this.method = userLogEntity.getMethod();
    this.params = userLogEntity.getParams();
    Date date = userLogEntity.getCreateDate();
    this.createDate = DateUtils.dateToPreStr(date);
    this.oprModule = userLogEntity.getOprModule();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getOprModule() {
    return oprModule;
  }

  public void setOprModule(String oprModule) {
    this.oprModule = oprModule;
  }
}