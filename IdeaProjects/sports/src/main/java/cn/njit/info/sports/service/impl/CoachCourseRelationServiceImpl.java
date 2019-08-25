/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachCourseRelationServiceImpl
 * Author:   Administrator
 * Date:     2019/5/9 14:26
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.CoachCourseRelationDao;
import cn.njit.info.sports.pojo.entity.CoachCourseRelation;
import cn.njit.info.sports.service.CoachCourseRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教练课程服务实现
 *
 * @author Liuzw
 * @since 2019/5/9
 */
@Service
public class CoachCourseRelationServiceImpl implements CoachCourseRelationService {
  private static final Logger logger = LoggerFactory.getLogger(CoachCourseRelationServiceImpl.class);
  private final CoachCourseRelationDao meDao;

  @Autowired
  public CoachCourseRelationServiceImpl(CoachCourseRelationDao meDao) {
    this.meDao = meDao;
  }

  public int save(CoachCourseRelation relation) {
    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 0;
    relation.setId(maxId + 1);
    if (null == relation.getCoachId() || null == relation.getCourseId()) {
      return 0;
    }
    logger.info("新增一条教练_课程记录:{}", relation);
    return meDao.insert(relation);
  }

  @Override
  public void deleteAllByCoachId(Integer coachId) {
    List<Integer> idList = meDao.getIdsByCoachId(coachId);
    //跳过空验证
    idList.add(Integer.MAX_VALUE);
    meDao.deleteBatchIds(idList);
  }

  @Override
  public List<CoachCourseRelation> findAll() {
    return meDao.selectList(new QueryWrapper<>());
  }

  @Override
  public CoachCourseRelation findOneByCoachId(Integer coachId) {
    QueryWrapper<CoachCourseRelation> qw = new QueryWrapper<>();
    qw.eq("COACH_ID", coachId);
    List<CoachCourseRelation> list = meDao.selectList(qw);
    return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
  }

  @Override
  public void update(CoachCourseRelation relation) {
    meDao.updateById(relation);
  }
}