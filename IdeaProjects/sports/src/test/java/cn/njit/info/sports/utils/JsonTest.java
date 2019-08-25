/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: JsonTest
 * Author:   Administrator
 * Date:     2019/3/26 20:58
 */
package cn.njit.info.sports.utils;

import cn.njit.info.sports.pojo.ResourceModel;
import org.junit.Test;

/**
 * JsonTest
 * @author Liuzw
 * @since 2019/3/26
 */
public class JsonTest {
  @Test
  public void testJson(){
    String str="{ \"enterPriseId\": 1,\n" +
        "        \"code\": \"test\",\n" +
        "        \"name\": \"test\"}";
    ResourceModel resourceModel = JsonUtils.jsonToPojo(str, ResourceModel.class);
    System.out.println(resourceModel.getCode());
  }
}