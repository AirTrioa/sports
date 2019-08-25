/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseDao
 * Author:   Administrator
 * Date:     2019/5/5 20:19
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 课程数据访问层
 * @author Liuzw
 * @since 2019/5/5
 */
@Mapper
@Repository
public interface CourseDao extends BaseMapper<Course> {
  @Select(SqlProperties.SELECT+EntityProperties.Course.TABLE_NAME+SqlProperties.WHERE+" NAME = #{name}")
  Course findOneByName(String name);
}