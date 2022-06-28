/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogController
 * Author:   Administrator
 * Date:     2019/5/11 15:00
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.entity.UserLogEntity;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.UserLogService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志管理
 *
 * @author Liuzw
 * @since 2019/5/11
 */
@RestController
@RequestMapping("/sports/user_log")
public class UserLogController {
  private final UserLogService meService;

  @Autowired
  public UserLogController(UserLogService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.DATA_ALY, opr = "分页查询日志")
  @GetMapping("/page")
  public RestResult page(@RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<UserLogEntity> page = meService.pageEs(params);
    return RestResultUtils.returnSuccess(page);
  }

  //  @UserLog(module = Module.DATA_ALY, opr = "获取操作日志的树")
  @GetMapping("/tree")
  public RestResult tree() {
    List<TreeGroup> tree = meService.getTree();
    return RestResultUtils.returnSuccess(tree);
  }

  @UserLog(module = Module.DATA_ALY, opr = "根据用户Id查询日志")
  @GetMapping("/user_id/{userId}")
  public RestResult getLogsByUserId(@PathVariable("userId") String userId) {
    List<UserLogEntity> logs = meService.getLogsByUserIdEs(userId);
    return RestResultUtils.returnSuccess(logs);
  }

  @UserLog(module = Module.DATA_ALY, opr = "详情")
  @GetMapping("/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    UserLogEntity details = meService.details(id);
    return RestResultUtils.returnSuccess(details);
  }

  @GetMapping("/search")
  public RestResult search(@RequestParam(WebConstant.PARAMS) String params) {
    List<UserLogEntity> search = meService.search(params);
    return RestResultUtils.returnSuccess(search);
  }

  @GetMapping(name = "获取近7天访问量", value = "/page_view_num")
  public RestResult getPageViewNum() {
    List<Integer> pageViewNum = meService.getPageViewNum();
    return RestResultUtils.returnSuccess(pageViewNum);
  }
}