/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: LogUtils
 * Author:   Administrator
 * Date:     2019/5/9 21:17
 */
package cn.njit.info.sports.utils;

import org.slf4j.Logger;

/**
 * 日志打印工具类
 *
 * @author Liuzw
 * @since 2019/5/9
 */
public class LogUtils {
  private LogUtils() {
  }

  public static void create(Logger logger, String entityName, String params, Integer id) {
    logger.info("新增一条{}:{},Id为{}", entityName, params, id);
  }

  public static void create(Logger logger, String params, Integer id) {
    logger.info("新增:{},Id为{}", params, id);
  }

  public static void delete(Logger logger, Integer id) {
    logger.info("删除一条记录,Id为{}", id);
  }

  public static void update(Logger logger, String entityName, String params) {
    logger.info("更新{}:{}", entityName, params);
  }

}