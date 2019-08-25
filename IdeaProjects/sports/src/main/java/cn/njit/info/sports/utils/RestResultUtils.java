/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RestResultUtils
 * Author:   Administrator
 * Date:     2019/3/20 11:04
 */
package cn.njit.info.sports.utils;

import cn.njit.info.sports.pojo.model.RestResult;
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.T;

/**
 * 返回前端渲染对象的工具类
 *
 * @author Liuzw
 * @since 2019/3/20
 */
public class RestResultUtils {
  /**
   * 私有构造器
   */
  private RestResultUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 返回正确
   *
   * @return restResult
   */
  public static RestResult returnSuccess() {
    return new RestResult(200, true, null);
  }

  /**
   * 返回错误提示
   *
   * @param errorMessage 错误提示
   * @return restResult
   */
  public static RestResult returnError(String errorMessage) {
    return new RestResult(500, false, errorMessage, null);
  }

  /**
   * @param data
   * @return
   */
  public static RestResult returnSuccess(Object data) {
    return new RestResult(200, true, data);
  }


  public static RestResult returnMessage(String message) {
    return new RestResult(200, true, message, null);
  }

  public static RestResult returnCreate(int index) {
    if (index <= 0) {
      return returnError("新建失败");
    }
    return returnMessage("新建成功");
  }

  public static RestResult returnUpdate(int index){
    if(index <= 0) {
      return returnError("更新失败");
    }
    return returnMessage("更新成功");
  }
}