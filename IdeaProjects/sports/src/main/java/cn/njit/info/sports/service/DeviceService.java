/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: DeviceService
 * Author:   Administrator
 * Date:     2019/5/10 13:14
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.entity.Device;

import java.util.List;

/**
 * 设备器材服务
 *
 * @author Liuzw
 * @since 2019/5/10
 */
public interface DeviceService extends AppBaseService<Device> {
  List<Device> findAllByRoomId(Integer roomId,String params);

  List<Device> findAllByTypeName(String typeName);
}