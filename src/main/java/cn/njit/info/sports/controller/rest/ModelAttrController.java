/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ModelAttrControllerApp
 * Author:   Administrator
 * Date:     2019/3/29 22:34
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.pojo.ModelAttr;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.ModelAttrService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * ModelAttr
 *
 * @author Liuzw
 * @since 2019/3/29
 */
@RestController
@RequestMapping("sports/model_attr")
public class ModelAttrController extends AppBaseController {
  private final ModelAttrService modelAttrService;

  @Autowired
  public ModelAttrController(ModelAttrService modelAttrService) {
    this.modelAttrService = modelAttrService;
  }

  @PostMapping("")
  public RestResult create(@RequestParam String params) {
    int create = modelAttrService.create(params);
    if (create < 0) {
      return RestResultUtils.returnError("创建失败！");
    }
    return RestResultUtils.returnSuccess();
  }

  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable Integer id) {
    modelAttrService.delete(id);
    return RestResultUtils.returnSuccess();
  }

  @PutMapping("")
  public RestResult modify(@RequestParam String params) {
    int update = modelAttrService.update(params);
    if (update < 0) {
      return RestResultUtils.returnError("更新失败!");
    }
    return RestResultUtils.returnSuccess();
  }

  @GetMapping("/{id}")
  public RestResult details(@PathVariable Integer id) {
    ModelAttr modelAttr = modelAttrService.details(id);
    if(ObjectUtils.isEmpty(modelAttr)){
      return RestResultUtils.returnError("查询失败!");
    }
    return RestResultUtils.returnSuccess(modelAttr);
  }
}