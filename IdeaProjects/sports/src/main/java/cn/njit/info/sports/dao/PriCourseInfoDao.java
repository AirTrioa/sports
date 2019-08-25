/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourseInfoDao
 * Author:   Administrator
 * Date:     2019/5/20 15:31
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.PriCourseInfo;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 私教选课信息Dao
 *
 * @author Liuzw
 * @since 2019/5/20
 */
@Mapper
@Repository
public interface PriCourseInfoDao extends BaseMapper<PriCourseInfo> {
  @Select(SqlProperties.MAX_ID + " PRI_COURSE_INFO")
  Integer getMaxId();
}