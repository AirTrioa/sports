/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ResourceInsControllerApp
 * Author:   Administrator
 * Date:     2019/3/30 13:50
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.pojo.ResourceIns;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.ResourceInsService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ResourceIns
 * @author Liuzw
 * @since 2019/3/30
 */
@RequestMapping("/sports/resource_ins")
@RestController
public class ResourceInsController extends AppBaseController {
  private final ResourceInsService resourceInsService;

  @Autowired
  public ResourceInsController(ResourceInsService resourceInsService) {
    this.resourceInsService = resourceInsService;
  }
  @PostMapping("")
  @Override
  public RestResult create(@RequestParam String params) {
    resourceInsService.create(params);
    return RestResultUtils.returnSuccess();
  }

  @DeleteMapping("/{id}")
  @Override
  public RestResult delete(@PathVariable Integer id) {
    resourceInsService.delete(id);
    return RestResultUtils.returnSuccess();
  }

  @PutMapping("")
  public RestResult modify(@RequestParam String params) {
    resourceInsService.update(params);
    return RestResultUtils.returnSuccess();
  }

  @GetMapping("/{id}")
  @Override
  public RestResult details(@PathVariable Integer id) {
    ResourceIns details = resourceInsService.details(id);
    return RestResultUtils.returnSuccess(details);
  }
}