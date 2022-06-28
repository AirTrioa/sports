/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseMapper
 * Author:   Administrator
 * Date:     2019/5/5 20:29
 */
package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程数据Mapper
 *
 * @author Liuzw
 * @since 2019/5/5
 */
@Repository
public interface CourseMapper {
  @Select("SELECT MAX(ID) FROM" + EntityProperties.Course.TABLE_NAME)
  public Integer getMaxId();

  @Select(SqlProperties.SELECT + EntityProperties.Course.TABLE_NAME + SqlProperties.WHERE + " NAME = #{name}")
  public Course findOneByName(String name);


}