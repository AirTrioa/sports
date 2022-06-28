/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupRelationsDao
 * Author:   Administrator
 * Date:     2019/5/7 11:33
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.CourseGroupRelations;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 分组关系Dao
 *
 * @author Liuzw
 * @since 2019/5/7
 */
@Mapper
@Repository
public interface CourseGroupRelationsDao extends BaseMapper<CourseGroupRelations> {

  @Delete("DELETE FROM COURSE_GROUP_RELATIONS WHERE COURSE_ID = #{courseId} ")
  void deleteByCourseId(Integer courseId);

  @Delete("DELETE FROM COURSE_GROUP_RELATIONS WHERE GROUP_ID = #{groupId}")
  void deleteByGroupId(Integer groupId);

  @Select(SqlProperties.MAX_ID+" COURSE_GROUP_RELATIONS")
  Integer getMaxId();

}