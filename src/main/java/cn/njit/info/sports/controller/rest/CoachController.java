/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachController
 * Author:   Administrator
 * Date:     2019/5/7 22:57
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.DTO.CoachDTO;
import cn.njit.info.sports.pojo.entity.Coach;
import cn.njit.info.sports.pojo.entity.HotNum;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.CoachService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 教练控制层
 *
 * @author Liuzw
 * @since 2019/5/7
 */
@RestController
@RequestMapping("/sports/coach")
public class CoachController extends AppBaseController {
  private final CoachService meService;

  @Autowired
  public CoachController(CoachService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.USER_MGR, opr = "创建教练信息")
  @PostMapping("")
  public RestResult create(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    return RestResultUtils.returnCreate(index);
  }

  @UserLog(module = Module.USER_MGR, opr = "修改教练信息")
  @PutMapping("")
  public RestResult modify(@RequestParam(WebConstant.PARAMS) String params) {
    int update = meService.update(params);
    return RestResultUtils.returnUpdate(update);
  }

  @UserLog(module = Module.USER_MGR, opr = "删除教练信息")
  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable(WebConstant.ID) Integer id) {
    meService.delete(id);
    return RestResultUtils.returnSuccess();
  }

  @UserLog(module = Module.USER_MGR, opr = "查看教练信息")
  @GetMapping("/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    Coach coach = meService.details(id);
    return RestResultUtils.returnSuccess(coach);
  }

  @UserLog(module = Module.USER_MGR, opr = "分页查询教练信息")
  @GetMapping("")
  public RestResult page(@RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<CoachDTO> page = meService.pageDto(params);
    return RestResultUtils.returnSuccess(page);
  }

  @UserLog(module = Module.DATA_ALY, opr = "获取教练男女的比例")
  @GetMapping(name = "获取教练男女比例", value = "/sex_num")
  public RestResult getSexNum() {
    List<Integer> sexNum = meService.getSexNum();
    return RestResultUtils.returnSuccess(sexNum);
  }

  @UserLog(module = Module.DATA_ALY, opr = "获取前五的热门教练")
  @GetMapping(name = "获取前五的热门教练", value = "/hot")
  public RestResult getHotCoach() {
    List<HotNum> fiveHot = meService.getFiveHot();
    return RestResultUtils.returnSuccess(fiveHot);
  }


}