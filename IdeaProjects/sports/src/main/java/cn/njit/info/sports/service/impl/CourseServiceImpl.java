/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseServiceImpl
 * Author:   Administrator
 * Date:     2019/5/5 20:31
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.*;
import cn.njit.info.sports.dao.mapper.CourseGroupMapper;
import cn.njit.info.sports.dao.mapper.CourseGroupRelationsMapper;
import cn.njit.info.sports.dao.mapper.CourseMapper;
import cn.njit.info.sports.pojo.DTO.CourseDTO;
import cn.njit.info.sports.pojo.entity.*;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.CoachService;
import cn.njit.info.sports.service.CourseGroupService;
import cn.njit.info.sports.service.CourseService;
import cn.njit.info.sports.utils.*;
import cn.njit.info.sports.verify.VerifyCourse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 课程服务实现
 *
 * @author Liuzw
 * @since 2019/5/5
 */
@Service
public class CourseServiceImpl implements CourseService {
  private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
  private final CourseDao meDao;
  private final CourseMapper meMapper;
  private final VerifyCourse verifyCourse;
  private final CourseGroupRelationsMapper relationsMapper;
  private final CourseGroupService courseGroupService;
  private final CoachService coachService;
  private final CoachCourseRelationDao coachCourseRelationDao;
  private final PriCourseDao priCourseDao;


  @Autowired
  public CourseServiceImpl(CourseDao meDao,
                           CourseMapper meMapper,
                           VerifyCourse verifyCourse,
                           CourseGroupRelationsMapper relationsMapper,
                           CourseGroupService courseGroupService,
                           CoachService coachService,
                           CoachCourseRelationDao coachCourseRelationDao,
                           PriCourseDao priCourseDao) {
    this.meDao = meDao;
    this.meMapper = meMapper;
    this.verifyCourse = verifyCourse;
    this.relationsMapper = relationsMapper;
    this.courseGroupService = courseGroupService;
    this.coachService = coachService;
    this.coachCourseRelationDao = coachCourseRelationDao;
    this.priCourseDao = priCourseDao;
  }

  @Override
  public int create(String params) {
    Course course = Course.read(params);
    Integer maxId = meMapper.getMaxId();
    if (null == maxId) {
      maxId = 1;
    }
    course.setId(maxId + 1);
    boolean validName = verifyCourse.validName(course);
    logger.info("创建新的课程{}，Id为:{}", params, maxId + 1);
    return validName ? meDao.insert(course) : 0;
  }

  @Override
  public void delete(Integer id) {
    meDao.deleteById(id);
  }

  @Override
  public int update(String params) {
    Course course = Course.read(params);
    boolean validName = verifyCourse.validName(course);
    Course one = meDao.selectById(course.getId());
    if (null == one) {
      return 0;
    }
    one.setDate(null != course.getDate() ? course.getDate() : one.getDate());
    one.setName(null != course.getName() ? course.getName() : one.getName());
    one.setRoomName(StringUtils.isNotBlank(course.getRoomName()) ? course.getRoomName() : one.getRoomName());
    one.setTimeFrame(StringUtils.isNotBlank(course.getTimeFrame()) ? course.getTimeFrame() : one.getTimeFrame());
    logger.info("更新课程{}，Id为:{}", params, course.getId());
    return validName ? meDao.updateById(one) : 0;
  }

  @Override
  public Course details(Integer id) {
    return meDao.selectById(id);
  }

  @Override
  public List<Course> page(String params) {
    QueryWrapper<Course> qw = new QueryWrapper<>();
    if (null != params && params.length() > 0) {
      Course course = Course.read(params);
      if (null != course.getName())
        qw.like("NAME", course.getName());
      if (null != course.getLesson())
        qw.eq("LESSON", course.getLesson());
    }
    return meDao.selectList(qw);
  }

  @Override
  public void readExcel(MultipartFile file) {
    try {
      List<String[]> excelData = ExcelUtils.getExcelData(file);
      for (int i = 1; i < excelData.size(); i++) {
        String[] data = excelData.get(i);
        logger.info("Excel解析:第{}行,内容:{}", i, data);
        String name = data[0];
        Course one = meMapper.findOneByName(name);
        if (null == one) {
          one = new Course();
        }
        one.setName(name);
        one.setLesson(Integer.valueOf(data[1]));
        Date date = DateUtils.strToDate(data[2]);
        one.setDate(date);
        one.setDescription(data[3]);

        if (null != one.getId()) {
          meDao.updateById(one);
          return;
        }
        Integer maxId = meMapper.getMaxId();
        if (null == maxId) {
          maxId = 0;
        }
        one.setId(maxId + 1);
        meDao.insert(one);
      }
    } catch (Exception e) {
      logger.error("解析上传的Excel文件时发生异常", e);
    }
  }

  @Override
  public List<Course> getAllByGroupId(Integer groupId) {
    List<TreeGroup> childList = courseGroupService.getChildList(groupId.toString());
    List<Integer> groupIds = new ArrayList<>();
    if (CollectionUtils.isEmpty(childList)) {
      // 自己就是叶子节点
      groupIds.add(groupId);
    } else {
      for (TreeGroup tree : childList) {
        groupIds.add(tree.getId().intValue());
      }
    }
    List<Integer> courseIds = courseGroupService.getCourseIdsByGroupIds(groupIds);
    return getAllByIds(courseIds);
  }

  @Override
  public List<Course> getAllByIds(List<Integer> idList) {
    //在此重构的原因是:直接使用这个idList会默认的返回Arrays工具类中的内部静态类“ArrayList”,
    // 从而直接调用AbstractList的add()方法,抛出UnsupportedOperationException异常
    // 发生的场景:点击一个没有子节点的父分组节点报此异常
    ArrayList<Integer> ids = new ArrayList<>(idList);
    //跳过空验证
    ids.add(Integer.MAX_VALUE);
    QueryWrapper<Course> qw = new QueryWrapper<>();
    qw.in("ID", ids);
    List<Course> courses = meDao.selectList(qw);
    if (CollectionUtils.isEmpty(courses)) {
      return Collections.emptyList();
    }
    return courses;
  }

  @Override
  public List<Course> getCoursesWithoutRelations() {
    List<Integer> courseIds = relationsMapper.getCourseIdsWithRelations();
    //跳过空验证
    courseIds.add(Integer.MAX_VALUE);
    QueryWrapper<Course> qw = new QueryWrapper<>();
    qw.notIn("ID", courseIds);
    return meDao.selectList(qw);
  }

  @Override
  public List<Course> getAllByCoachName(String coachName) {
    Coach coach = coachService.findOneByName(coachName);
    //获取CourseId
    Integer courseId = coachService.getCourseIdById(coach.getId());
    Course course = meDao.selectById(courseId);
    List<Course> courses = new ArrayList<>();
    courses.add(course);
    return courses;
  }

  @Override
  public List<CourseDTO> buildCoachCourseDataByCoachName(String coachName) {
    List<Course> courses = getAllByCoachName(coachName);
    //目前来说--一个教练对应一个课程
    Course course = courses.get(0);
    String timeFrame = course.getTimeFrame();
    Coach coach = coachService.findOneByName(coachName);
    List<CourseDTO> dtoList = getPrivateCourseTable(coach.getId());
    for (CourseDTO dto : dtoList) {
      dto.setCoachName(coachName);
      if (dto.getTimeFrame().equals(timeFrame)) {
        dto.setMonday(course.getName() + ";" + course.getRoomName());
        dto.setTuesday(course.getName() + ";" + course.getRoomName());
        dto.setWednesday(course.getName() + ";" + course.getRoomName());
        dto.setThursday(course.getName() + ";" + course.getRoomName());
        dto.setFriday(course.getName() + ";" + course.getRoomName());
        dto.setSaturday(course.getName() + ";" + course.getRoomName());
      }
    }
    return dtoList;
  }

  @Override
  public Course getCourseByName(String name) {
    QueryWrapper<Course> qw = new QueryWrapper<>();
    qw.eq("NAME", name);
    return meDao.selectOne(qw);
  }

  @Override
  public Course getCourseByCoachId(Integer coachId) {
    QueryWrapper<CoachCourseRelation> qw = new QueryWrapper<>();
    qw.eq("COACH_ID", coachId);
    List<CoachCourseRelation> relations = coachCourseRelationDao.selectList(qw);
    return meDao.selectById(relations.get(0).getCourseId());
  }

  public List<CourseDTO> getPrivateCourseTable(Integer coachId) {
    List<PriCourse> courseList = priCourseDao.selectList(new QueryWrapper<PriCourse>()
        .eq("COACH_ID", coachId));
    List<CourseDTO> resultList = new ArrayList<>();
    for (PriCourse priCourse : courseList) {
      CourseDTO courseDTO = new CourseDTO();
      courseDTO.setCoachId(coachId);
      courseDTO.setTimeFrame(priCourse.getTimeFrame());
      switch (priCourse.getWeek()) {
        case 1:
          courseDTO.setMonday(priCourse.getName() + ";" + priCourse.getRoomName());
          break;
        case 2:
          courseDTO.setTuesday(priCourse.getName() + ";" + priCourse.getRoomName());
          break;
        case 3:
          courseDTO.setWednesday(priCourse.getName() + ";" + priCourse.getRoomName());
          break;
        case 4:
          courseDTO.setTuesday(priCourse.getName() + ";" + priCourse.getRoomName());
          break;
        case 5:
          courseDTO.setMonday(priCourse.getName() + ";" + priCourse.getRoomName());
          break;
        case 6:
          courseDTO.setSaturday(priCourse.getName() + ";" + priCourse.getRoomName());
          break;
        default:
          break;
      }
      resultList.add(courseDTO);
    }
    List<CourseDTO> dtoList = new ArrayList<>();
    CourseDTO am = CourseDTO.buildDTO("上午", resultList);
    am.setCoachId(coachId);
    dtoList.add(am);

    CourseDTO pm = CourseDTO.buildDTO("下午", resultList);
    pm.setCoachId(coachId);
    dtoList.add(pm);

    CourseDTO night = CourseDTO.buildDTO("晚上", resultList);
    night.setCoachId(coachId);
    dtoList.add(night);
    return dtoList;
  }
}