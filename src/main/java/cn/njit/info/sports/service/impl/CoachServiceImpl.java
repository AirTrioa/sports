/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachServiceImpl
 * Author:   Administrator
 * Date:     2019/5/7 22:44
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.*;
import cn.njit.info.sports.dao.mapper.UserMapper;
import cn.njit.info.sports.pojo.DTO.CoachDTO;
import cn.njit.info.sports.pojo.entity.*;
import cn.njit.info.sports.service.CoachCourseRelationService;
import cn.njit.info.sports.service.CoachService;
import cn.njit.info.sports.utils.LogUtils;
import cn.njit.info.sports.utils.MD5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 教练服务 实现
 *
 * @author Liuzw
 * @since 2019/5/7
 */
@Service
public class CoachServiceImpl implements CoachService {
  private static final Logger logger = LoggerFactory.getLogger(CoachServiceImpl.class);
  private static final String ENTITY_NAME = "Coach";
  private final CoachDao meDao;
  private final CoachCourseRelationService relationService;
  private final CourseDao courseDao;
  private final UserDao userDao;
  private final UserMapper userMapper;
  private final RoomDao roomDao;
  private final PriCourseInfoDao priCourseInfoDao;
  private final HotNumDao hotNumDao;

  @Autowired
  public CoachServiceImpl(CoachDao meDao,
                          CoachCourseRelationService relationService,
                          CourseDao courseDao,
                          UserDao userDao,
                          UserMapper userMapper,
                          RoomDao roomDao,
                          PriCourseInfoDao priCourseInfoDao,
                          HotNumDao hotNumDao) {
    this.meDao = meDao;
    this.relationService = relationService;
    this.courseDao = courseDao;
    this.userDao = userDao;
    this.userMapper = userMapper;
    this.roomDao = roomDao;
    this.priCourseInfoDao = priCourseInfoDao;
    this.hotNumDao = hotNumDao;
  }


  @Override
  @Transactional
  public int create(String params) {
    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    CoachDTO dto = CoachDTO.read(params);
    //教练
    Coach coach = new Coach(dto);
    coach.setId(maxId + 1);
    //课程
    Course course = courseDao.findOneByName(dto.getCourseName());
    //教练-课程关系
    CoachCourseRelation relation = new CoachCourseRelation(dto);
    relation.setCourseId(course.getId());
    relation.setCoachId(maxId + 1);
    //保存记录
    int save = relationService.save(relation);
    if (save <= 0) {
      return 0;
    }
    //把教练信息同步到用户表
    User user = new User(coach);
    user.setUserId(coach.getId().toString());
    Integer maxId1 = userMapper.getMaxId();
    user.setId(maxId1 + 1);
    user.setPassword(MD5Utils.encrypt("njitCoach"));
    userDao.insert(user);
    //增加到热度表
    HotNum hotNum = new HotNum();
    hotNum.setId(coach.getId());
    hotNum.setCoachName(coach.getName());
    hotNum.setUserNum(0);
    hotNumDao.insert(hotNum);
    LogUtils.create(logger, ENTITY_NAME, params, maxId + 1);
    return meDao.insert(coach);
  }

  @Override
  public void delete(Integer id) {
    meDao.deleteById(id);
    //删除教练-课程记录
    relationService.deleteAllByCoachId(id);
  }

  @Transactional
  public int update(String params) {
    CoachDTO dto = CoachDTO.read(params);
    logger.info("更新一条教练数据:{}", params);
    Coach details = details(dto.getId());
    //教练
    Coach coach = new Coach(dto);
    //同步到user表
    User user = userDao.selectOne(new QueryWrapper<User>()
        .eq("USER_ID", coach.getId()));
    user.setUsername(coach.getName());
    user.setAge(coach.getAge());
    userDao.updateById(user);
    //课程
    Course course = courseDao.findOneByName(dto.getCourseName());
    //教练-课程关系
    CoachCourseRelation relation = relationService.findOneByCoachId(dto.getId());
    relation.setCourseId(course.getId());
    relation.setCourseName(course.getName());
    int index = (null != details) ? meDao.updateById(coach) : 0;
    if (index > 0)
      relationService.update(relation);
    return index;
  }

  @Override
  public Coach details(Integer id) {
    return meDao.selectById(id);
  }

  @Transactional
  public List<CoachDTO> pageDto(String params) {
    CoachDTO dto = CoachDTO.read(params);
    Coach coach = new Coach(dto);
    logger.info("分页的查询条件:{}", params);
    QueryWrapper<Coach> qw = new QueryWrapper<>();
    if (null != coach.getName())
      qw.like("NAME", coach.getName());
    if (null != coach.getAge())
      qw.eq("AGE", coach.getAge());
    if (null != coach.getSex())
      qw.eq("SEX", coach.getSex());

    List<Coach> coachList = meDao.selectList(qw);
    List<CoachCourseRelation> relationList = relationService.findAll();
    List<CoachDTO> dtoList = new ArrayList<>();
    for (Coach one : coachList) {
      CoachDTO coachDTO = new CoachDTO(one);
      for (CoachCourseRelation relation : relationList) {
        if (relation.getCoachId().equals(one.getId())) {
          coachDTO.setCourseName(relation.getCourseName());
          coachDTO.setCourseId(relation.getCourseId());
          break;
        }
      }

      Course course = courseDao.selectOne(new QueryWrapper<Course>()
          .eq("ID", coachDTO.getCourseId()));
      Room room = roomDao.selectOne(new QueryWrapper<Room>()
          .eq("NAME", course.getRoomName()));
      if (null != room) {
        Integer userNum = room.getUserNum();
        List<PriCourseInfo> infos = priCourseInfoDao.selectList(new QueryWrapper<PriCourseInfo>()
            .eq("COACH_ID", coach.getId()));
        if (CollectionUtils.isEmpty(infos)) {
          coachDTO.setUserNum(userNum);
        }
        coachDTO.setUserNum(userNum + infos.size());
        HotNum hotNum = new HotNum();
        hotNum.setId(coachDTO.getId());
        hotNum.setUserNum(userNum + infos.size());
        hotNum.setCoachName(coachDTO.getName());
        hotNumDao.updateById(hotNum);
      } else {
        coachDTO.setUserNum(0);
      }
      dtoList.add(coachDTO);
    }
    return dtoList;
  }

  @Override
  public Coach findOneByName(String name) {
    QueryWrapper<Coach> qw = new QueryWrapper<>();
    qw.eq("NAME", name);
    return meDao.selectOne(qw);
  }

  @Override
  public List<Coach> page(String params) {
    Coach coach = Coach.read(params);
    logger.info("分页的查询条件:{}", params);
    QueryWrapper<Coach> qw = new QueryWrapper<>();
    if (null != coach.getName())
      qw.like("NAME", coach.getName());
    if (null != coach.getAge())
      qw.eq("AGE", coach.getAge());
    if (null != coach.getSex())
      qw.eq("SEX", coach.getSex());
    return meDao.selectList(qw);
  }

  @Override
  public Integer getCourseIdById(Integer id) {
    CoachCourseRelation relation = relationService.findOneByCoachId(id);
    return relation.getCourseId();
  }

  @Override
  public List<Integer> getSexNum() {
    List<Coach> coaches = meDao.selectList(new QueryWrapper<>());
    int man = 0;
    int woman = 0;
    for (Coach coach : coaches) {
      String sex = coach.getSex();
      if (null != sex && sex.equals("男")) {
        man++;
      } else {
        woman++;
      }
    }
    List<Integer> resultList = new ArrayList<>();
    resultList.add(man);
    resultList.add(woman);
    return resultList;
  }

  @Override
  public List<HotNum> getFiveHot() {
    QueryWrapper<HotNum> qw = new QueryWrapper<>();
    qw.orderByDesc("USER_NUM");
    List<HotNum> hotNums = hotNumDao.selectList(qw);
    List<HotNum> xyz = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      xyz.add(hotNums.get(i));
    }
    return xyz;
  }
}