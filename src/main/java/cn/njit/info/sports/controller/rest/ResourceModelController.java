/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ResourceModelControllerApp
 * Author:   Administrator
 * Date:     2019/3/20 11:17
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.pojo.ResourceModel;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.ResourceModelService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 资源对象控制层
 *
 * @author Liuzw
 * @since 2019/3/20
 */
@RestController
@RequestMapping("sports/resource_model")
public class ResourceModelController extends AppBaseController {
  private final ResourceModelService resourceModelService;

  @Autowired
  public ResourceModelController(ResourceModelService resourceModelService) {
    this.resourceModelService = resourceModelService;
  }

  @GetMapping("/{id}")
  public RestResult details(@PathVariable Integer id) {
    ResourceModel details = resourceModelService.details(id);
    return RestResultUtils.returnSuccess(details);
  }

  @PostMapping("")
  public RestResult create(@RequestParam String params) {
    int create = resourceModelService.create(params);
    if (create < 0) {
      return RestResultUtils.returnError("create ");
    }
    return RestResultUtils.returnSuccess();
  }

  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable Integer id) {
    resourceModelService.delete(id);
    return RestResultUtils.returnSuccess();
  }

  @PutMapping("")
  public RestResult modify(String params) {
    int update = resourceModelService.update(params);
    if (update < 0) {
      return RestResultUtils.returnError("查询失败!");
    }
    return RestResultUtils.returnSuccess();
  }
}