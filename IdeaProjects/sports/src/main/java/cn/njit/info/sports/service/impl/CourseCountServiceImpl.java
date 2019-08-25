/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseCountServiceImpl
 * Author:   Administrator
 * Date:     2019/5/19 0:28
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.*;
import cn.njit.info.sports.pojo.DTO.CourseEvaDTO;
import cn.njit.info.sports.pojo.DTO.CourseRecDTO;
import cn.njit.info.sports.pojo.entity.*;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.CourseCountService;
import cn.njit.info.sports.service.CourseGroupService;
import cn.njit.info.sports.utils.LogUtils;
import cn.njit.info.sports.utils.TreeUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程统计服务实现
 *
 * @author Liuzw
 * @since 2019/5/19
 */
@Service
public class CourseCountServiceImpl implements CourseCountService {
  private static final String USER_ID = "USER_ID";
  private static final String COACH_ID = "COACH_ID";
  private static final String COURSE_ID = "COURSE_ID";
  private static final Logger logger = LoggerFactory.getLogger(CourseCountServiceImpl.class);
  private final CourseCountDao meDao;
  private final CourseDao courseDao;
  private final EvaluateDao evaluateDao;
  private final CoachDao coachDao;
  private final CoachCourseRelationDao coachCourseRelationDao;
  private final CourseGroupService courseGroupService;
  private final PriCourseInfoDao priCourseInfoDao;
  private final PriCourseDao priCourseDao;

  @Autowired
  public CourseCountServiceImpl(CourseCountDao meDao,
                                CourseDao courseDao,
                                EvaluateDao evaluateDao,
                                CoachDao coachDao,
                                CoachCourseRelationDao coachCourseRelationDao,
                                CourseGroupService courseGroupService,
                                PriCourseInfoDao priCourseInfoDao,
                                PriCourseDao priCourseDao) {
    this.meDao = meDao;
    this.courseDao = courseDao;
    this.evaluateDao = evaluateDao;
    this.coachDao = coachDao;
    this.coachCourseRelationDao = coachCourseRelationDao;
    this.courseGroupService = courseGroupService;
    this.priCourseInfoDao = priCourseInfoDao;
    this.priCourseDao = priCourseDao;
  }

  @Override
  public int create(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer rate = jsonObject.getInteger("rate");
    String userId = jsonObject.getString("userId");
    String courseName = jsonObject.getString("courseName");
    String contents = jsonObject.getString("contents");
    Evaluate evaluate = new Evaluate();
    CoachCourseRelation courseRelation = coachCourseRelationDao.findOneByCourseName(courseName);
    if (null == courseRelation) {
      PriCourseInfo info = priCourseInfoDao.selectOne(new QueryWrapper<PriCourseInfo>()
          .eq(USER_ID, userId)
          .eq("PRI_COURSE_NAME", courseName));
      if (null == info)
        return 0;
      evaluate.setPri(true);
      evaluate.setCoachId(info.getCoachId());
      evaluate.setCourseId(info.getPriCourseId());
    } else {
      evaluate.setCoachId(courseRelation.getCoachId());
      evaluate.setCourseId(courseRelation.getCourseId());
    }

    evaluate.setUserId(userId);
    evaluate.setContents(contents);
    evaluate.setRank(Evaluate.buildRank(rate));
    Integer maxId = evaluateDao.getMaxId();
    if (null == maxId) {
      maxId = 0;
    }
    evaluate.setId(maxId + 1);
    LogUtils.create(logger, params, maxId + 1);
    return evaluateDao.insert(evaluate);
  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public int update(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer rate = jsonObject.getInteger("rate");
    String userId = jsonObject.getString("userId");
    String courseName = jsonObject.getString("courseName");
    String contents = jsonObject.getString("contents");
    QueryWrapper<Evaluate> qw = new QueryWrapper<>();
    CoachCourseRelation courseRelation = coachCourseRelationDao.findOneByCourseName(courseName);
    if (null == courseRelation) {
      PriCourseInfo info = priCourseInfoDao.selectOne(new QueryWrapper<PriCourseInfo>()
          .eq(USER_ID, userId)
          .eq("PRI_COURSE_NAME", courseName));
      if (null == info)
        return 0;
      qw.eq(COACH_ID, info.getCoachId());
      qw.eq(COURSE_ID, info.getPriCourseId());
    } else {
      qw.eq(COACH_ID, courseRelation.getCoachId());
      qw.eq(COURSE_ID, courseRelation.getCourseId());
    }
    qw.eq(USER_ID, userId);
    Evaluate evaluate = evaluateDao.selectOne(qw);
    evaluate.setContents(contents);
    evaluate.setRank(Evaluate.buildRank(rate));
    return evaluateDao.updateById(evaluate);
  }

  @Override
  public CourseCount details(Integer id) {
    return null;
  }

  @Override
  public List<CourseCount> page(String params) {
    return null;
  }

  public List<TreeGroup> getTreeData(String userId) {
    QueryWrapper<CourseCount> qw = new QueryWrapper<>();
    qw.eq(USER_ID, userId);
    List<CourseCount> counts = meDao.selectList(qw);
    List<TreeGroup> treeList = new ArrayList<>();

    TreeGroup publicTree = new TreeGroup();
    publicTree.setId(0L);
    publicTree.setKey("PUBLIC");
    publicTree.setParentKey("ROOT");
    publicTree.setTitle("公选课");
    publicTree.setIsLeaf(false);
    treeList.add(publicTree);

    TreeGroup privateTree = new TreeGroup();
    privateTree.setId(-1L);
    privateTree.setKey("PRIVATE");
    privateTree.setParentKey("ROOT");
    privateTree.setTitle("私教课程");
    privateTree.setIsLeaf(false);
    treeList.add(privateTree);

    for (CourseCount count : counts) {
      Integer courseId = count.getCourseId();
      Course course = courseDao.selectById(courseId);
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setIsLeaf(true);
      treeGroup.setTitle(course.getName());
      treeGroup.setParentKey("PUBLIC");
      treeGroup.setKey(courseId.toString());
      treeGroup.setId(count.getId().longValue());
      treeList.add(treeGroup);
    }

    List<PriCourseInfo> priCourseInfos = priCourseInfoDao.selectList(new QueryWrapper<PriCourseInfo>()
        .eq(USER_ID, userId));
    for (PriCourseInfo info : priCourseInfos) {
      Integer priCourseId = info.getPriCourseId();
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setId(priCourseId.longValue());
      treeGroup.setKey(priCourseId.toString());
      treeGroup.setParentKey("PRIVATE");
      treeGroup.setTitle(info.getPriCourseName());
      treeGroup.setIsLeaf(true);
      treeList.add(treeGroup);
    }

    TreeGroup root = TreeUtils.buildTree("ROOT", treeList);
    return root.getChildren();
  }

  @Override
  public CourseEvaDTO getByCourseId(Integer courseId, String userId) {
    Course course = courseDao.selectById(courseId);
    Integer courseIdInEva;
    if (null == course) {
      PriCourse priCourse = priCourseDao.selectById(courseId);
      courseIdInEva = priCourse.getId();
      QueryWrapper<Evaluate> qw = new QueryWrapper<>();
      qw.eq(COURSE_ID, courseIdInEva);
      qw.eq(USER_ID, userId);
      qw.eq("IS_PRI", true);
      Evaluate evaluate = evaluateDao.selectOne(qw);
      Coach coach = coachDao.selectById(priCourse.getCoachId());
      if (null != evaluate) {
        return new CourseEvaDTO(priCourse, coach.getName(), evaluate.getRank(), evaluate.getContents());
      }
      return new CourseEvaDTO(priCourse, coach.getName(), null, null);
    } else {
      courseIdInEva = course.getId();
      QueryWrapper<Evaluate> qw = new QueryWrapper<>();
      qw.eq(COURSE_ID, courseIdInEva);
      qw.eq(USER_ID, userId);
      qw.eq("IS_PRI", false);
      Evaluate evaluate = evaluateDao.selectOne(qw);
      CoachCourseRelation courseRelation = coachCourseRelationDao.findOneByCourseId(courseId);
      Integer coachId = courseRelation.getCoachId();
      Coach coach = coachDao.selectById(coachId);
      if (null != evaluate) {
        return new CourseEvaDTO(course, coach.getName(), evaluate.getRank(), evaluate.getContents());
      }
      return new CourseEvaDTO(course, coach.getName(), null, null);
    }
  }

  @Override
  public List<CourseRecDTO> getRecommendCourse(String userId) {
    QueryWrapper<CourseCount> qw = new QueryWrapper<>();
    qw.eq(USER_ID, userId);
    List<CourseCount> counts = meDao.selectList(qw);
    List<Integer> courseIds = new ArrayList<>();
    for (CourseCount count : counts) {
      Integer courseId = count.getCourseId();
      courseIds.add(courseId);
    }
    return courseGroupService.getCourseRecDTOByCourseIds(courseIds);
  }

  @Override
  public List<Integer> getGradeByCoachId(Integer coachId) {
    QueryWrapper<Evaluate> qw = new QueryWrapper<>();
    if (null != coachId)
      qw.eq(COACH_ID, coachId);
    List<Evaluate> evaluates = evaluateDao.selectList(qw);
    int s = 0;
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;
    for (Evaluate evaluate : evaluates) {
      String rank = evaluate.getRank();
      switch (rank) {
        case "S":
          s++;
          break;
        case "A":
          a++;
          break;
        case "B":
          b++;
          break;
        case "C":
          c++;
          break;
        case "D":
          d++;
          break;
        default:
          break;
      }
    }
    List<Integer> resultList = new ArrayList<>();
    resultList.add(s);
    resultList.add(a);
    resultList.add(b);
    resultList.add(c);
    resultList.add(d);
    return resultList;
  }

  @Override
  public String getAvgRank(Integer coachId) {
    QueryWrapper<Evaluate> qw = new QueryWrapper<>();
    qw.eq(COACH_ID, coachId);
    List<Evaluate> evaluates = evaluateDao.selectList(qw);
    Integer avgRate = Evaluate.getAvgRank(evaluates);
    return Evaluate.buildRank(avgRate);
  }

  @Override
  public List<Integer> getAvgGrade() {
    return getGradeByCoachId(null);
  }
}