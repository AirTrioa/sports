/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: DeviceController
 * Author:   Administrator
 * Date:     2019/5/10 13:31
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.entity.Device;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.DeviceService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备控制层
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@RestController
@RequestMapping("/sports/device")
public class DeviceController extends AppBaseController {
  private final DeviceService meService;

  @Autowired
  public DeviceController(DeviceService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "新建设备器材")
  @PostMapping("")
  public RestResult create(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    return RestResultUtils.returnCreate(index);
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "修改设备器材")
  @PutMapping("")
  public RestResult modify(@RequestParam(WebConstant.PARAMS) String params) {
    int update = meService.update(params);
    return RestResultUtils.returnUpdate(update);
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "删除设备器材")
  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable(WebConstant.ID) Integer id) {
    meService.delete(id);
    return RestResultUtils.returnSuccess();
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "查询设备详情")
  @GetMapping("/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    Device details = meService.details(id);
    return RestResultUtils.returnSuccess(details);
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "分页查询设备数据")
  @GetMapping("/page")
  public RestResult page(@RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<Device> page = meService.page(params);
    return RestResultUtils.returnSuccess(page);
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "根据房间Id来查询器材")
  @GetMapping("/room_id/{roomId}")
  public RestResult findAllByRoomId(@PathVariable("roomId") Integer roomId,
                                    @RequestParam(WebConstant.PARAMS) String params) {
    List<Device> devices = meService.findAllByRoomId(roomId, params);
    return RestResultUtils.returnSuccess(devices);
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "根据器材类型来查询所有器材")
  @GetMapping("/type/{type}")
  public RestResult findAllByType(@PathVariable("type") String typeCode) {
    List<Device> page = meService.findAllByTypeName(typeCode);
    return RestResultUtils.returnSuccess(page);
  }
}