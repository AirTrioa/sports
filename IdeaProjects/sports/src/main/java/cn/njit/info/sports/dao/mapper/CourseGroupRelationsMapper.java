/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupRelationsMapper
 * Author:   Administrator
 * Date:     2019/5/7 13:16
 */
package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.entity.CourseGroupRelations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CourseGroupRelationsMapper
 *
 * @author Liuzw
 * @since 2019/5/7
 */
@Mapper
@Repository
public interface CourseGroupRelationsMapper extends AppBaseMapper<CourseGroupRelations> {

  @Select("SELECT COURSE_ID FROM COURSE_GROUP_RELATIONS WHERE GROUP_ID = #{groupId}")
  List<Integer> getCourseIdsByGroupId(Integer groupId);

  @Select("SELECT COURSE_ID FROM COURSE_GROUP_RELATIONS")
  List<Integer> getCourseIdsWithRelations();

}