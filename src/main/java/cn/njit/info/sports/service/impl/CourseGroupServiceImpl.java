/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupServiceImpl
 * Author:   Administrator
 * Date:     2019/5/6 19:23
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.CourseDao;
import cn.njit.info.sports.dao.CourseGroupDao;
import cn.njit.info.sports.dao.CourseGroupRelationsDao;
import cn.njit.info.sports.dao.mapper.CourseGroupMapper;
import cn.njit.info.sports.pojo.DTO.CourseRecDTO;
import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.entity.CourseGroup;
import cn.njit.info.sports.pojo.entity.CourseGroupRelations;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.CourseGroupService;
import cn.njit.info.sports.utils.JsonUtils;
import cn.njit.info.sports.utils.TreeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 课程分组服务实现
 *
 * @author Liuzw
 * @since 2019/5/6
 */
@Service
public class CourseGroupServiceImpl implements CourseGroupService {
  private static final Logger logger = LoggerFactory.getLogger(CourseGroupServiceImpl.class);
  private final CourseGroupDao meDao;
  private final CourseGroupMapper meMapper;
  private final CourseGroupRelationsDao relationsDao;
  private final CourseDao courseDao;

  @Autowired
  public CourseGroupServiceImpl(CourseGroupDao meDao,
                                CourseGroupMapper meMapper,
                                CourseGroupRelationsDao relationsDao,
                                CourseDao courseDao) {
    this.meDao = meDao;
    this.meMapper = meMapper;
    this.relationsDao = relationsDao;
    this.courseDao = courseDao;
  }

  @Override
  public int create(String params) {
    CourseGroup courseGroup = CourseGroup.read(params);
    Integer maxId = meMapper.getMaxId();
    if (null == maxId)
      maxId = 0;
    courseGroup.setId(maxId + 1);
    logger.info("新增的分组:{},ID为:{}", params, maxId + 1);
    return meDao.insert(courseGroup);
  }

  @Override
  public void delete(Integer id) {
    meDao.deleteById(id);
    //删除关系
    deleteRelationByGroupId(id);
  }

  @Override
  public int update(String params) {
    CourseGroup courseGroup = CourseGroup.read(params);
    CourseGroup details = details(courseGroup.getId());
    return null != details ? meDao.updateById(courseGroup) : 0;
  }

  @Override
  public CourseGroup details(Integer id) {
    return meMapper.findOne(id);
  }


  @Override
  public List<TreeGroup> getTreeData() {
    List<CourseGroup> groups = meDao.selectList(new QueryWrapper<>());
    List<TreeGroup> treeGroups = convertCourseGroup(groups);
    //根据根节点构建树
    TreeGroup treeGroup = TreeUtils.buildTree("ROOT", treeGroups);
    return treeGroup.getChildren();
  }

  public List<TreeGroup> getChildList(String key) {
    List<CourseGroup> groups = meDao.selectList(new QueryWrapper<>());
    List<TreeGroup> treeGroups = convertCourseGroup(groups);
    List<TreeGroup> childList = new ArrayList<>();
    TreeUtils.getChildList(treeGroups, key, childList);
    return childList;
  }

  private List<TreeGroup> convertCourseGroup(List<CourseGroup> groups) {
    List<TreeGroup> treeGroups = new ArrayList<>();
    for (CourseGroup group : groups) {
      TreeGroup treeGroup = convertCourseGroup(group);
      treeGroups.add(treeGroup);
    }
    return treeGroups;
  }

  /**
   * 把课程分组转换成前台需要的实体类
   *
   * @param group 分组
   * @return 树实体
   */
  private TreeGroup convertCourseGroup(CourseGroup group) {
    TreeGroup treeGroup = new TreeGroup();
    treeGroup.setId(Long.valueOf(group.getId()));
    treeGroup.setKey(group.getId().toString());
    treeGroup.setTitle(group.getGroupName());
    if (null == group.getParentId()) {
      treeGroup.setParentKey("ROOT");
    } else {
      treeGroup.setParentKey(group.getParentId().toString());
    }
    treeGroup.setIsLeaf(group.isLeaf());
    return treeGroup;
  }

  public List<Integer> getCourseIdsByGroupIds(List<Integer> groupIds) {
    QueryWrapper<CourseGroupRelations> qw = new QueryWrapper<>();
    qw.in("GROUP_ID", groupIds);
    List<CourseGroupRelations> relations = relationsDao.selectList(qw);
    List<Integer> courseIds = new ArrayList<>();
    if (CollectionUtils.isEmpty(relations)) {
      logger.error("根据{}查询不到对应的关系", groupIds);
      return Collections.emptyList();
    }
    for (CourseGroupRelations relation : relations) {
      courseIds.add(relation.getCourseId());
    }
    logger.info("根据{}查询到对应的{}条关系", groupIds, courseIds.size());
    return courseIds;
  }

  @Override
  public void removeCourse(Integer courseId) {
    relationsDao.deleteByCourseId(courseId);
  }

  private void deleteRelationByGroupId(Integer groupId) {
    List<TreeGroup> childList = getChildList(groupId.toString());
    if (CollectionUtils.isEmpty(childList)) {
      //子节点为空，说明自己就是子节点
      relationsDao.deleteByGroupId(groupId);
    } else {
      List<Integer> groupIds = new ArrayList<>();
      for (TreeGroup treeGroup : childList) {
        groupIds.add(treeGroup.getId().intValue());
      }
      groupIds.add(Integer.MAX_VALUE);
      QueryWrapper<CourseGroupRelations> qw = new QueryWrapper<>();
      qw.in("GROUP_ID", groupIds);
      logger.info("需要删除关系的groupIds:{}", groupIds.toArray());
      relationsDao.delete(qw);
    }
  }

  @Override
  public int createRelations(Integer groupId, String courseIdsJsonStr) {
    List<Integer> courseIds = JsonUtils.jsonToList(courseIdsJsonStr, Integer.class);
    CourseGroup group = details(groupId);
    if (null == group || CollectionUtils.isEmpty(courseIds)) {
      logger.error("不存在{}分组", groupId);
      return 0;
    }
    Integer maxId = relationsDao.getMaxId();
    if (null == maxId) {
      maxId = 0;
    }
    int count = 0;
    for (Integer courseId : courseIds) {
      CourseGroupRelations one = new CourseGroupRelations();
      maxId = maxId + 1;
      one.setId(maxId);
      one.setCourseId(courseId);
      one.setGroupId(groupId);
      int index = relationsDao.insert(one);
      count = count + index;
    }
    return count;
  }

  @Override
  public List<CourseGroup> page(String params) {
    return null;
  }


  @Override
  public List<CourseRecDTO> getCourseRecDTOByCourseIds(List<Integer> courseIds) {
    QueryWrapper<CourseGroupRelations> qw = new QueryWrapper<>();
    courseIds.add(Integer.MAX_VALUE);
    qw.in("COURSE_ID", courseIds);
    List<CourseGroupRelations> relations = relationsDao.selectList(qw);
    Set<Integer> groupIdSet = new HashSet<>();
    for (CourseGroupRelations relation : relations) {
      groupIdSet.add(relation.getGroupId());
    }
    Set<CourseRecDTO> dtoSet = new HashSet<>();
    for (Integer groupId : groupIdSet) {
      CourseRecDTO courseRecDTO = new CourseRecDTO();
      CourseGroup group = meDao.selectById(groupId);
      courseRecDTO.setGroupName(group.getGroupName());
      QueryWrapper<CourseGroupRelations> queryWrapper =
          new QueryWrapper<>();
      queryWrapper.eq("GROUP_ID", groupId);
      List<CourseGroupRelations> groupRelations = relationsDao.selectList(queryWrapper);
      Integer courseId = groupRelations.get(0).getCourseId();
      Course course = courseDao.selectById(courseId);
      courseRecDTO.setCourseName(course.getName());
      courseRecDTO.setId(courseId);
      dtoSet.add(courseRecDTO);
    }

    return new ArrayList<>(dtoSet);
  }
}