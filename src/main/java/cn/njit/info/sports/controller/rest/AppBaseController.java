/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: AppBaseController
 * Author:   Administrator
 * Date:     2019/3/27 23:02
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.pojo.model.RestResult;

/**
 * 基类
 *
 * @author Liuzw
 * @since 2019/3/27
 */
public abstract class AppBaseController {

  public abstract RestResult create(String params);

  public abstract RestResult modify(String params);

  public abstract RestResult delete(Integer id);

  public abstract RestResult details(Integer id);
}