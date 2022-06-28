/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseCountDao
 * Author:   Administrator
 * Date:     2019/5/18 23:50
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.CourseCount;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 课程统计Dao
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@Mapper
@Repository
public interface CourseCountDao extends BaseMapper<CourseCount> {
  @Select(SqlProperties.MAX_ID + " COURSE_COUNT")
  Integer getMaxId();
}