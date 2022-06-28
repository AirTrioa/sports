/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: JavaSmsUtils
 * Author:   Administrator
 * Date:     2019/3/30 14:10
 */
package cn.njit.info.sports.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送短信工具类
 *
 * @author Liuzw
 * @since 2019/3/30
 */
public class JavaSmsUtils {

  private JavaSmsUtils() {
  }

  //编码格式。发送编码格式统一用UTF-8
  private static final String ENCODING = "UTF-8";
  //模板发送接口的http地址
  private static final String URI_TPL_SEND_SMS =
      "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
  private static final Logger logger = LoggerFactory.getLogger(JavaSmsUtils.class);

  @Value("${message.apiKey}")
  private static String apiKey;
  @Value("${message.tpl_id}")
  private static long tplId;

  /**
   * 发送短信接口
   *
   * @param mobile 手机号码
   * @param name   名字
   * @param value  6位验证码
   * @return Json
   */
  public static String sendMessage(String mobile, String name, String value) {
    String tplSendSms = null;
    try {
      StringBuilder tplValue = new StringBuilder();
      tplValue.append(URLEncoder.encode("#name#", ENCODING));
      tplValue.append("=");
      tplValue.append(URLEncoder.encode(name, ENCODING));
      tplValue.append("&");
      tplValue.append(URLEncoder.encode("#code#", ENCODING));
      tplValue.append("=");
      tplValue.append(URLEncoder.encode(value, ENCODING));
      String tpl_value = tplValue.toString();
      tplSendSms = tplSendSms(apiKey, tplId, tpl_value, mobile);
      logger.info("tpl_value = {}", tpl_value);
      logger.info("tplSendSms = {}", tplSendSms);
      return tplSendSms;
    } catch (Exception e) {
      logger.error("调用短信接口发生错误!", e);
    }
    return tplSendSms;
  }

  /**
   * 通过模板发送短信(不推荐)
   *
   * @param apikey    apikey
   * @param tpl_id    　模板id
   * @param tpl_value 　模板变量值
   * @param mobile    　接受的手机号
   * @return json格式字符串
   * @throws IOException
   */
  private static String tplSendSms(String apikey, long tpl_id, String tpl_value,
                                   String mobile) throws IOException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("apikey", apikey);
    params.put("tpl_id", String.valueOf(tpl_id));
    params.put("tpl_value", tpl_value);
    params.put("mobile", mobile);
    return post(URI_TPL_SEND_SMS, params);
  }

  /**
   * 基于HttpClient 4.3的通用POST方法
   *
   * @param url       提交的URL
   * @param paramsMap 提交<参数，值>Map
   * @return 提交响应
   */
  private static String post(String url, Map<String, String> paramsMap) {
    CloseableHttpClient client = HttpClients.createDefault();
    String responseText = "";
    CloseableHttpResponse response = null;
    try {
      HttpPost method = new HttpPost(url);
      if (paramsMap != null) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : paramsMap.entrySet()) {
          NameValuePair pair = new BasicNameValuePair(param.getKey(),
              param.getValue());
          paramList.add(pair);
        }
        method.setEntity(new UrlEncodedFormEntity(paramList,
            ENCODING));
      }
      response = client.execute(method);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseText = EntityUtils.toString(entity, ENCODING);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        response.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return responseText;
  }

}