/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: EnterPriseControllerApp
 * Author:   Administrator
 * Date:     2019/3/27 23:23
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.pojo.EnterPrise;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.EnterPriseService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 企业控制层
 *
 * @author Liuzw
 * @since 2019/3/27
 */
@RestController
@RequestMapping("sports/enter_prise")
public class EnterPriseController extends AppBaseController {
  private final EnterPriseService enterPriseService;

  @Autowired
  public EnterPriseController(EnterPriseService enterPriseService) {
    this.enterPriseService = enterPriseService;
  }

  @PostMapping("")
  @Override
  public RestResult create(@RequestParam String params) {
    int create = enterPriseService.create(params);
    if (create <= 0) {
      return RestResultUtils.returnError("创建失败");
    }
    return RestResultUtils.returnSuccess("创建成功!");
  }

  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable Integer id) {
    enterPriseService.delete(id);
    return RestResultUtils.returnSuccess("更新成功!");
  }

  @PutMapping("")
  public RestResult modify(@RequestParam String params) {
    int update = enterPriseService.update(params);
    if (update <= 0) {
      return RestResultUtils.returnError("更新失败");
    }
    return RestResultUtils.returnSuccess("更新成功!");
  }

  @GetMapping("/{id}")
  public RestResult details(@PathVariable Integer id) {
    EnterPrise details = enterPriseService.details(id);
    if (ObjectUtils.isEmpty(details)) {
      return RestResultUtils.returnError("查询失败！");
    }
    return RestResultUtils.returnSuccess(details);
  }
}