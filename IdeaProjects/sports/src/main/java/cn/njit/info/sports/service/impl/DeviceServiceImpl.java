/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: DeviceServiceImpl
 * Author:   Administrator
 * Date:     2019/5/10 13:15
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.DeviceDao;
import cn.njit.info.sports.dao.RoomDao;
import cn.njit.info.sports.pojo.entity.Device;
import cn.njit.info.sports.pojo.entity.Room;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.RoomType;
import cn.njit.info.sports.service.DeviceService;
import cn.njit.info.sports.utils.LogUtils;
import cn.njit.info.sports.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 设备器材服务实现
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@Service
public class DeviceServiceImpl implements DeviceService {
  private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
  private final DeviceDao meDao;
  private final RoomDao roomDao;

  @Autowired
  public DeviceServiceImpl(DeviceDao meDao, RoomDao roomDao) {
    this.meDao = meDao;
    this.roomDao = roomDao;
  }

  @Override
  public int create(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    String roomName = jsonObject.getString("roomName");
    Room room = roomDao.findOneByName(roomName);
    Device device = Device.read(params);
    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    device.setId(maxId + 1);
    device.setRoomId(room.getId());
    LogUtils.create(logger, EntityProperties.Device.TABLE_NAME, params, maxId + 1);
    return meDao.insert(device);
  }

  @Override
  public void delete(Integer id) {
    LogUtils.delete(logger, id);
    meDao.deleteById(id);
  }

  @Override
  public int update(String params) {
    Device device = Device.read(params);
    Device details = details(device.getId());
    LogUtils.update(logger, EntityProperties.Device.TABLE_NAME, params);
    return null != details ? meDao.updateById(device) : 0;
  }

  @Override
  public Device details(Integer id) {
    return meDao.selectById(id);
  }

  @Override
  public List<Device> page(String params) {
    logger.info("分页查询条件:{}", params);
    Device device = Device.read(params);
    QueryWrapper<Device> qw = new QueryWrapper<>();
    Integer roomId = device.getRoomId();
    String type = device.getType();
    if (null != roomId)
      qw.eq(EntityProperties.Device.TABLE_ROOM_ID, roomId);
    if (StringUtils.isNotBlank(type))
      qw.eq("TYPE", type);
    return meDao.selectList(qw);
  }

  @Override
  public List<Device> findAllByRoomId(Integer roomId, String params) {
    logger.info("分页查询条件:{}", params);
    Device device = Device.read(params);
    QueryWrapper<Device> qw = new QueryWrapper<>();
    String type = device.getType();
    if (null != roomId)
      qw.eq(EntityProperties.Device.TABLE_ROOM_ID, roomId);
    if (StringUtils.isNotBlank(type))
      qw.eq(EntityProperties.Device.TABLE_TYPE, type);
    return meDao.selectList(qw);
  }

  @Override
  public List<Device> findAllByTypeName(String typeCode) {
    RoomType type = RoomType.fromCode(typeCode);
    QueryWrapper<Device> qw = new QueryWrapper<>();
    if (null != type) {
      //训练室的Type
      String roomTypeName = type.getName();
      QueryWrapper<Room> qwRoom = new QueryWrapper<>();
      qwRoom.eq(EntityProperties.Device.TABLE_TYPE, roomTypeName);
      List<Room> rooms = roomDao.selectList(qwRoom);
      if (CollectionUtils.isEmpty(rooms)) {
        return Collections.emptyList();
      }
      List<Integer> roomIds = new ArrayList<>();
      for (Room room : rooms) {
        roomIds.add(room.getId());
      }
      //跳过空判断
      roomIds.add(Integer.MAX_VALUE);
      qw.in(EntityProperties.Device.TABLE_ROOM_ID, roomIds);
      return meDao.selectList(qw);
    }
    return Collections.emptyList();
  }
}