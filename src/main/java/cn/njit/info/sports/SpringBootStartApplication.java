/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: SpringBootStartApplication
 * Author:   Administrator
 * Date:     2019/3/30 17:56
 */
package cn.njit.info.sports;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * war包启动类
 *
 * @author Liuzw
 * @since 2019/3/30
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(SportsApplication.class);
  }
}