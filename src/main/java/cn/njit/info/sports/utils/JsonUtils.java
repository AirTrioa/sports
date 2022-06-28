/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: JsonUtils
 * Author:   Administrator
 * Date:     2019/3/26 20:24
 */
package cn.njit.info.sports.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Json转换工具类
 *
 * @author Liuzw
 * @since 2019/3/26
 */
public class JsonUtils {
  //定义jackson对象
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * 将对象转化成json字符串
   *
   * @param data
   * @return
   */
  public static String objectToJson(Object data) {
    try {
      String string = MAPPER.writeValueAsString(data);
      return string;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将json结果集转化为对象
   *
   * @param jsonData json数据
   * @param beanType 对象中的object类型
   * @return
   */
  public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
    try {
      T t = MAPPER.readValue(jsonData, beanType);
      return t;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将json数据转成pojo对象list
   *
   * @param jsonData
   * @param beanType
   * @return
   */
  public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
    JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      List<T> list = MAPPER.readValue(jsonData, javaType);
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}