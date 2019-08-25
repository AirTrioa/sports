/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RestTemplateUtils
 * Author:   Administrator
 * Date:     2019/5/12 22:14
 */
package cn.njit.info.sports.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * 模拟http请求工具类
 *
 * @author Liuzw
 * @since 2019/5/12
 */
public class RestTemplateUtils {
  private RestTemplateUtils() {
  }

  /**
   * 获取请求头--没有Token认证的请求头
   *
   * @return HttpEntity
   */
  public static HttpEntity getHttpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new HttpEntity<>(null, headers);
  }

  public static HttpEntity getHttpEntity(Object params) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new HttpEntity<>(params, headers);
  }
}