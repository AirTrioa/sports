/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogEntity
 * Author:   Administrator
 * Date:     2019/5/10 23:35
 */
package cn.njit.info.sports.aspect;

import cn.njit.info.sports.pojo.enumeration.Module;

import java.lang.annotation.*;

/**
 * 用户日志
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLog {

  /**
   * 操作
   *
   * @return 操作
   */
  String opr() default "";

  /**
   * 模块
   *
   * @return 模块
   */
  Module module();
}