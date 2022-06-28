/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseInfoDao
 * Author:   Administrator
 * Date:     2019/5/18 10:42
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.CourseInfo;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 选课信息Dao
 * @author Liuzw
 * @since 2019/5/18
 */
@Mapper
@Repository
public interface CourseInfoDao extends BaseMapper<CourseInfo> {
  @Select(SqlProperties.MAX_ID+EntityProperties.CourseInfo.TABLE_TYPE)
  Integer getMaxId();
}