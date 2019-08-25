/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseInfoServiceImpl
 * Author:   Administrator
 * Date:     2019/5/18 10:46
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.*;
import cn.njit.info.sports.pojo.entity.*;
import cn.njit.info.sports.service.CourseInfoService;
import cn.njit.info.sports.service.MailService;
import cn.njit.info.sports.utils.JsonUtils;
import cn.njit.info.sports.utils.LogUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 选课信息服务实现
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@Service
public class CourseInfoServiceImpl implements CourseInfoService {
  private static final Logger logger = LoggerFactory.getLogger(CourseInfoServiceImpl.class);
  private final CourseInfoDao meDao;
  private final CourseCountDao countDao;
  @Autowired
  private RoomDao roomDao;
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private CoachCourseRelationDao relationDao;
  @Autowired
  private MailService mailService;

  @Autowired
  public CourseInfoServiceImpl(CourseInfoDao meDao, CourseCountDao countDao) {
    this.meDao = meDao;
    this.countDao = countDao;
  }

  @Transactional
  public int create(String params) {
    CourseInfo one = CourseInfo.read(params);
    QueryWrapper<CourseInfo> qw = new QueryWrapper<>();
    qw.eq("USER_ID", one.getUserId());
    qw.eq("COURSE_ID", one.getCourseId());
    List<CourseInfo> infos = meDao.selectList(qw);
    //判断是否存在已经选择过的课程
    if (CollectionUtils.isNotEmpty(infos)) {
      return 0;
    }
    //判断该公共课有没有教练在执教
    QueryWrapper<CoachCourseRelation> relationQueryWrapper
        = new QueryWrapper<>();
    relationQueryWrapper.eq("COURSE_ID", one.getCourseId());
    List<CoachCourseRelation> courseRelations = relationDao.selectList(relationQueryWrapper);
    if (CollectionUtils.isEmpty(courseRelations)) {
      //若该门公共课没有教练，向管理员/企业用户发送邮件来进行通知
      String to = "airtrioa@foxmail.com";
      String subject = "紧急通知";
      String content = "公共课程Id:[" + one.getCourseId() + "]目前还未有教练执教，请及时添加";
      mailService.sendSimpleMail(to, subject, content);
      return -1;
    }
    //根据courseId查询课程
    Course course = courseDao.selectById(one.getCourseId());

    //构建课程统计实体
    CourseCount count = new CourseCount();
    Integer countDaoMaxId = countDao.getMaxId();
    if (null == countDaoMaxId)
      countDaoMaxId = 0;
    count.setId(countDaoMaxId + 1);
    count.setUserId(one.getUserId());
    count.setCoachId(courseRelations.get(0).getCoachId());
    count.setCourseId(one.getCourseId());
    Room room = roomDao.findOneByName(course.getRoomName());
    count.setMaxUserNum(room.getUserNum());
    count.setRoomId(room.getId());
    //新增课程统计实体
    countDao.insert(count);

    //courseInfo的新增
    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    one.setId(maxId + 1);
    LogUtils.create(logger, params, maxId + 1);
    return meDao.insert(one);
  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public int update(String params) {
    return 0;
  }

  @Override
  public CourseInfo details(Integer id) {
    return null;
  }

  @Override
  public List<CourseInfo> page(String params) {
    return null;
  }

  @Override
  public int createLists(String userId, String courseIdsJsonStr) {
    List<Integer> courseIds = JsonUtils.jsonToList(courseIdsJsonStr, Integer.class);
    //userId从session中取的，不可能不存在，所以不做验证
    int count = 0;
    for (Integer courseId : courseIds) {
      CourseInfo one = new CourseInfo();
      one.setUserId(userId);
      one.setCourseId(courseId);
      int insert = create(JSONObject.toJSONString(one));
      count = count + insert;
    }
    return count;
  }
}