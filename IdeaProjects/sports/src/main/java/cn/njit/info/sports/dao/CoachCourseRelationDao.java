/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachCourseRelationsDao
 * Author:   Administrator
 * Date:     2019/5/9 12:14
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.CoachCourseRelation;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教练课程关系Dao
 *
 * @author Liuzw
 * @since 2019/5/9
 */
@Mapper
@Repository
public interface CoachCourseRelationDao extends BaseMapper<CoachCourseRelation> {
  @Select(SqlProperties.MAX_ID + EntityProperties.CoachCourseRelation.TABLE_NAME)
  Integer getMaxId();

  @Select("SELECT ID FROM " + EntityProperties.CoachCourseRelation.TABLE_NAME + SqlProperties.WHERE + " COACH_ID = #{coachId}")
  List<Integer> getIdsByCoachId(Integer coachId);

  @Select(SqlProperties.SELECT + EntityProperties.CoachCourseRelation.TABLE_NAME + SqlProperties.WHERE + " COURSE_ID = #{courseId}")
  CoachCourseRelation findOneByCourseId(Integer courseId);

  @Select(SqlProperties.SELECT + EntityProperties.CoachCourseRelation.TABLE_NAME + SqlProperties.WHERE +" COURSE_NAME = #{courseName}")
  CoachCourseRelation findOneByCourseName(String courseName);
}