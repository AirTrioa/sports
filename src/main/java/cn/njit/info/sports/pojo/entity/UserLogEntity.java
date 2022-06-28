/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogEntity
 * Author:   Administrator
 * Date:     2019/5/10 20:25
 */
package cn.njit.info.sports.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 使用日志
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@TableName("USER_LOG")
public class UserLogEntity {
  @TableId("ID")
  private Integer id;
  /**
   * 操作Id
   */
  @TableField("USER_ID")
  private Integer userId;
  /**
   * 操作人
   */
  @TableField("USER_NAME")
  private String username;
  /**
   * 操作
   */
  @TableField("OPERATION")
  private String operation;
  /**
   * 方法名
   */
  @TableField("METHOD")
  private String method;
  /**
   * 参数
   */
  @TableField("PARAMS")
  private String params;
  /**
   * 操作时间
   */
  @TableField("CREATE_DATE")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createDate;
  /**
   * 操作模块
   */
  @TableField("OPR_MODULE")
  private String oprModule;

  public UserLogEntity() {
  }

  public UserLogEntity(Integer id, Integer userId, String username, String operation, String method, String params, Date createDate, String oprModule) {
    this.id = id;
    this.userId = userId;
    this.username = username;
    this.operation = operation;
    this.method = method;
    this.params = params;
    this.createDate = createDate;
    this.oprModule = oprModule;
  }

  public String getOprModule() {
    return oprModule;
  }

  public void setOprModule(String oprModule) {
    this.oprModule = oprModule;
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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public static UserLogEntity read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("id");
    Integer userId = jsonObject.getInteger("userId");
    String username = jsonObject.getString("username");
    String operation = jsonObject.getString("operation");
    String method = jsonObject.getString("method");
    String paramsTemp = jsonObject.getString("params");
    Date createDate = jsonObject.getDate("createDate");
    String oprModule = jsonObject.getString("oprModule");
    return new UserLogEntity(id, userId, username, operation, method, paramsTemp, createDate, oprModule);
  }
}