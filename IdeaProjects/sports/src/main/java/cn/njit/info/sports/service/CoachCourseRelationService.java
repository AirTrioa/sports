/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachCourseRelationService
 * Author:   Administrator
 * Date:     2019/5/9 14:25
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.entity.CoachCourseRelation;

import java.util.List;

/**
 * 教练课程服务
 *
 * @author Liuzw
 * @since 2019/5/9
 */
public interface CoachCourseRelationService {

  int save(CoachCourseRelation relation);

  void deleteAllByCoachId(Integer coachId);

  List<CoachCourseRelation> findAll();

  CoachCourseRelation findOneByCoachId(Integer coachId);

  void update(CoachCourseRelation relation);
}