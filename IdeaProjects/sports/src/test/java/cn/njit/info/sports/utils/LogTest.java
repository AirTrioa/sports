/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: LogTest
 * Author:   Administrator
 * Date:     2019/3/25 23:50
 */
package cn.njit.info.sports.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * x
 * @author Liuzw
 * @since 2019/3/25
 */
public class LogTest {
  public static final Logger log=LoggerFactory.getLogger(LogTest.class);

  public static void main(String[] args) {
    log.trace("trace");
    log.debug("debug");
    log.warn("warn");
    log.info("info");
    log.error("error");
  }
}