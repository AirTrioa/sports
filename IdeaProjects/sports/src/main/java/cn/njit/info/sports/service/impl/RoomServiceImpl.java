/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RoomServiceImpl
 * Author:   Administrator
 * Date:     2019/5/9 21:08
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.RoomDao;
import cn.njit.info.sports.pojo.entity.Room;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.RoomType;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.RoomService;
import cn.njit.info.sports.utils.LogUtils;
import cn.njit.info.sports.utils.StringUtils;
import cn.njit.info.sports.utils.TreeUtils;
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
 * 房间服务实现
 *
 * @author Liuzw
 * @since 2019/5/9
 */
@Service
public class RoomServiceImpl implements RoomService {
  private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);
  private static final String ENTITY_NAME = "Room";
  private final RoomDao meDao;

  @Autowired
  public RoomServiceImpl(RoomDao meDao) {
    this.meDao = meDao;
  }

  @Override
  public int create(String params) {
    Room room = Room.read(params);
    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    room.setId(maxId + 1);
    LogUtils.create(logger, ENTITY_NAME, params, maxId + 1);
    return meDao.insert(room);
  }

  @Override
  public void delete(Integer id) {
    LogUtils.delete(logger, id);
    meDao.deleteById(id);
  }

  @Override
  public int update(String params) {
    Room room = Room.read(params);
    Room details = details(room.getId());
    LogUtils.update(logger, ENTITY_NAME, params);
    return null != details ? meDao.updateById(room) : 0;
  }

  @Override
  public Room details(Integer id) {
    return meDao.selectById(id);
  }

  @Override
  public List<Room> page(String params) {
    Room room = Room.read(params);
    logger.info("{}分页的查询条件:{}", EntityProperties.Room.TABLE_NAME, params);
    QueryWrapper<Room> qw = new QueryWrapper<>();
    if (StringUtils.isNotBlank(room.getName()))
      qw.eq("NAME", room.getName());
    if (StringUtils.isNotBlank(room.getType()))
      qw.eq("TYPE", room.getType());
    if (null != room.isPri()) {
      int index = 0;
      if (room.isPri()) {
        index = 1;
      }
      qw.eq("IS_PRI", index);
    }
    return meDao.selectList(qw);
  }

  @Override
  public List<TreeGroup> tree() {
    List<Room> rooms = meDao.selectList(new QueryWrapper<>());
    List<TreeGroup> list = new ArrayList<>();
    for (Room room : rooms) {
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setIsLeaf(true);
      treeGroup.setTitle(room.getName());
      treeGroup.setKey(room.getId().toString());
      treeGroup.setParentKey(RoomType.fromName(room.getType()));
      list.add(treeGroup);
    }
    List<TreeGroup> types = addGroup();
    list.addAll(types);
    TreeGroup root = TreeUtils.buildTree("ROOT", list);
    return root.getChildren();
  }

  /**
   * 为树的列表增加组别
   *
   * @return 组List
   */
  private List<TreeGroup> addGroup() {
    List<TreeGroup> list = new ArrayList<>();
    for (RoomType type : RoomType.values()) {
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setTitle(type.getName());
      treeGroup.setKey(type.getCode());
      treeGroup.setParentKey("ROOT");
      treeGroup.setIsLeaf(false);
      list.add(treeGroup);
    }
    return list;
  }

  @Override
  public List<Room> findAll(Boolean isPri) {
    QueryWrapper<Room> qw = new QueryWrapper<>();
    qw.eq("IS_PRI", isPri);
    List<Room> rooms = meDao.selectList(qw);
    if (CollectionUtils.isEmpty(rooms)) {
      return Collections.emptyList();
    }
    return rooms;
  }
}