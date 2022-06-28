/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupMapper
 * Author:   Administrator
 * Date:     2019/5/6 19:22
 */
package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.entity.CourseGroup;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * CourseGroup自定义SQL
 *
 * @author Liuzw
 * @since 2019/5/6
 */
@Mapper
@Repository
public interface CourseGroupMapper extends AppBaseMapper<CourseGroup> {
  @Select(SqlProperties.MAX_ID+EntityProperties.CourseGroup.TABLE_NAME)
  Integer getMaxId();

  @Select(SqlProperties.SELECT+EntityProperties.CourseGroup.TABLE_NAME+SqlProperties.WHERE+SqlProperties.ID)
  CourseGroup findOne(Integer id);
}