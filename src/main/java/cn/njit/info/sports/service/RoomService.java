/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RoomService
 * Author:   Administrator
 * Date:     2019/5/9 21:07
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.entity.Room;
import cn.njit.info.sports.pojo.model.TreeGroup;

import java.util.List;

/**
 * 房间服务
 *
 * @author Liuzw
 * @since 2019/5/9
 */
public interface RoomService extends AppBaseService<Room> {

  /**
   * 获取房间树
   *
   * @return 树
   */
  List<TreeGroup> tree();

  /**
   * 获取房间列表
   *
   * @param isPri 是否是私教课程
   * @return 房间列表
   */
  List<Room> findAll(Boolean isPri);
}