/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourseDao
 * Author:   Administrator
 * Date:     2019/5/18 21:55
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.PriCourse;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 私教课程dao
 * @author Liuzw
 * @since 2019/5/18
 */
@Mapper
@Repository
public interface PriCourseDao extends BaseMapper<PriCourse> {
  @Select(SqlProperties.MAX_ID+" PRI_COURSE")
  Integer getMaxId();

  @Select("SELECT distinct COACH_ID FROM PRI_COURSE")
  List<Integer> getCoachIds();
}