/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RestResult
 * Author:   Administrator
 * Date:     2019/3/20 11:01
 */
package cn.njit.info.sports.pojo.model;

/**
 * 返回前端渲染对象
 *
 * @author Liuzw
 * @since 2019/3/20
 */
public class RestResult<T> {
  /**
   * 请求返回编码（200/500/404之类的）
   */
  private int status;
  /**
   * 是否成功
   */
  private boolean success;
  /**
   * message
   */
  private String message;

  /**
   * 返回前端的值
   */
  private T data;

  public RestResult() {
  }

  public RestResult(int status, boolean success, T data) {
    this.status = status;
    this.success = success;
    this.data = data;
  }

  public RestResult(int status, boolean success, String message, T data) {
    this.status = status;
    this.success = success;
    this.message = message;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}