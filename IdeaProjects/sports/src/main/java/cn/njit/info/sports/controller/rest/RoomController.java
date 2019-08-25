/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RoomController
 * Author:   Administrator
 * Date:     2019/5/9 21:28
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.entity.Room;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.RoomService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间管理
 *
 * @author Liuzw
 * @since 2019/5/9
 */
@RestController
@RequestMapping("/sports/room")
public class RoomController extends AppBaseController {
  private final RoomService meService;

  @Autowired
  public RoomController(RoomService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.ROOM_MGR, opr = "新建房间")
  @PostMapping("")
  public RestResult create(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    return RestResultUtils.returnCreate(index);
  }

  @UserLog(module = Module.ROOM_MGR, opr = "修改房间数据")
  @PutMapping("")
  public RestResult modify(@RequestParam(WebConstant.PARAMS) String params) {
    int update = meService.update(params);
    return RestResultUtils.returnUpdate(update);
  }

  @UserLog(module = Module.ROOM_MGR, opr = "删除房间数据")
  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable(WebConstant.ID) Integer id) {
    meService.delete(id);
    return RestResultUtils.returnSuccess();
  }

  @UserLog(module = Module.ROOM_MGR, opr = "查看房间详情")
  @GetMapping("/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    Room room = meService.details(id);
    return RestResultUtils.returnSuccess(room);
  }

  @UserLog(module = Module.ROOM_MGR, opr = "分页查询详情")
  @GetMapping("/page")
  public RestResult page(@RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<Room> page = meService.page(params);
    return RestResultUtils.returnSuccess(page);
  }

  @UserLog(module = Module.ROOM_MGR, opr = "房间树查询")
  @GetMapping("/tree")
  public RestResult tree() {
    List<TreeGroup> tree = meService.tree();
    return RestResultUtils.returnSuccess(tree);
  }

  @GetMapping("/public")
  public RestResult findPubicRooms() {
    List<Room> all = meService.findAll(false);
    return RestResultUtils.returnSuccess(all);
  }

  @GetMapping("/private")
  public RestResult findPrivateRooms() {
    List<Room> all = meService.findAll(true);
    return RestResultUtils.returnSuccess(all);
  }

}