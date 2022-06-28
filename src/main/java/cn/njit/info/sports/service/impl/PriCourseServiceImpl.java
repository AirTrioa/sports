/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourseServiceImpl
 * Author:   Administrator
 * Date:     2019/5/18 21:57
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.*;
import cn.njit.info.sports.pojo.DTO.CourseDTO;
import cn.njit.info.sports.pojo.entity.*;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.CourseService;
import cn.njit.info.sports.service.MailService;
import cn.njit.info.sports.service.PriCourseService;
import cn.njit.info.sports.utils.LogUtils;
import cn.njit.info.sports.utils.TreeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 私教课程服务实现
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@Service
public class PriCourseServiceImpl implements PriCourseService {
  private static final Logger logger = LoggerFactory.getLogger(PriCourseServiceImpl.class);
  //私教Dao
  private final PriCourseDao meDao;
  //教练Dao
  private final CoachDao coachDao;
  //课程Dao
  private final CourseService courseService;
  private final PriCourseInfoDao priCourseInfoDao;
  private final UserDao userDao;
  private final MailService mailService;

  @Autowired
  public PriCourseServiceImpl(PriCourseDao meDao,
                              CoachDao coachDao,
                              CourseService courseService,
                              PriCourseInfoDao priCourseInfoDao,
                              UserDao userDao,
                              MailService mailService) {
    this.meDao = meDao;
    this.coachDao = coachDao;
    this.courseService = courseService;
    this.priCourseInfoDao = priCourseInfoDao;
    this.userDao = userDao;
    this.mailService = mailService;
  }

  @Override
  public int create(String params) {
    PriCourse priCourse = PriCourse.read(params);
    Integer coachId = priCourse.getCoachId();
    Course course = courseService.getCourseByCoachId(coachId);
    String timeFrame = course.getTimeFrame();
    if (priCourse.getTimeFrame().equals(timeFrame)) {
      return -1;
    }
    List<PriCourse> courseList = meDao.selectList(new QueryWrapper<PriCourse>()
        .eq("COACH_ID", coachId));
    for (PriCourse pri : courseList) {
      if (pri.getTimeFrame().equals(priCourse.getTimeFrame())
          && pri.getWeek().equals(priCourse.getWeek())) {
        return -2;
      }
    }

    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    priCourse.setDate(new Date());
    priCourse.setId(maxId + 1);


    LogUtils.create(logger, params, maxId + 1);
    return meDao.insert(priCourse);
  }

  @Override
  public void delete(Integer id) {
    meDao.deleteById(id);
  }

  @Override
  public int update(String params) {
    return 0;
  }

  @Override
  public PriCourse details(Integer id) {
    return meDao.selectById(id);
  }

  @Override
  public List<PriCourse> page(String params) {
    return meDao.selectList(new QueryWrapper<PriCourse>());
  }

  @Override
  public List<TreeGroup> getTreeData() {
    List<Integer> coachIds = meDao.getCoachIds();
    List<TreeGroup> resultList = new ArrayList<>();
    for (Integer coachId : coachIds) {
      Coach coach = coachDao.selectById(coachId);
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setIsLeaf(false);
      treeGroup.setParentKey("ROOT");
      treeGroup.setId(coachId.longValue());
      treeGroup.setTitle(coach.getName());
      treeGroup.setKey(coachId.toString());
      resultList.add(treeGroup);
    }

    List<PriCourse> priCourses = meDao.selectList(new QueryWrapper<>());
    for (PriCourse priCourse : priCourses) {
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setId(priCourse.getId().longValue());
      treeGroup.setParentKey(priCourse.getCoachId().toString());
      treeGroup.setKey(priCourse.getId().toString());
      treeGroup.setTitle(priCourse.getName());
      treeGroup.setIsLeaf(true);
      resultList.add(treeGroup);
    }
    TreeGroup root = TreeUtils.buildTree("ROOT", resultList);
    return root.getChildren();
  }

  @Override
  public List<TreeGroup> getTreeData(Integer coachId) {
    List<PriCourse> priCourses = meDao.selectList(
        new QueryWrapper<PriCourse>()
            .eq("COACH_ID", coachId));
    List<TreeGroup> courseList = new ArrayList<>();
    for (PriCourse course : priCourses) {
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setId(course.getId().longValue());
      treeGroup.setParentKey("ROOT");
      treeGroup.setKey(course.getId().toString());
      treeGroup.setTitle(course.getName());
      treeGroup.setIsLeaf(true);
      courseList.add(treeGroup);
    }
    TreeGroup root = TreeUtils.buildTree("ROOT", courseList);
    return root.getChildren();
  }

  @Override
  public List<CourseDTO> getPriCourseTable(Integer coachId) {
    return courseService.getPrivateCourseTable(coachId);
  }

  @Transactional
  public void deleteByName(String courseName, Integer coachId) {
    QueryWrapper<PriCourse> qw = new QueryWrapper<>();
    qw.eq("COACH_ID", coachId);
    qw.eq("NAME", courseName);
    List<PriCourse> courseList = meDao.selectList(qw);
    Integer id = courseList.get(0).getId();
    meDao.deleteById(id);
    Coach coach = coachDao.selectById(coachId);
    //删除选课信息
    QueryWrapper<PriCourseInfo> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("COACH_ID", coachId);
    queryWrapper.eq("PRI_COURSE_NAME", courseName);
    List<PriCourseInfo> priCourseInfos = priCourseInfoDao.selectList(queryWrapper);
    if (CollectionUtils.isEmpty(priCourseInfos))
      return;
    for (PriCourseInfo info : priCourseInfos) {
      User user = userDao.selectOne(new QueryWrapper<User>()
          .eq("USER_ID", info.getUserId()));
      String to = user.getEmail();
      String subject = "致歉";
      String contents = "您的私教课程:[" + courseName + "]已被私教教练+[" + coach.getName() + "]于" +
          new Date() + "删除,请联系教练关于剩余课程的补偿措施";
      mailService.sendSimpleMail(to, subject, contents);
      priCourseInfoDao.deleteById(info.getId());
    }
  }

  @Override
  public int choosePriCourse(String params) {
    PriCourseInfo info = PriCourseInfo.read(params);
    List<PriCourseInfo> priCourseInfos = priCourseInfoDao.selectList(new QueryWrapper<PriCourseInfo>()
        .eq("COACH_ID", info.getCoachId())
        .eq("USER_ID", info.getUserId())
        .eq("PRI_COURSE_ID", info.getPriCourseId()));
    if (CollectionUtils.isNotEmpty(priCourseInfos)) {
      return -1;
    }
    //查看选课表中的信息，是否被其他人选走
    PriCourseInfo one = priCourseInfoDao.selectOne(new QueryWrapper<PriCourseInfo>()
        .eq("COACH_ID", info.getCoachId())
        .eq("PRI_COURSE_ID", info.getPriCourseId()));
    if (null != one) {
      return -2;
    }
    Integer maxId = priCourseInfoDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    info.setId(maxId + 1);
    LogUtils.create(logger, params, maxId + 1);
    return priCourseInfoDao.insert(info);
  }
}