/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachDao
 * Author:   Administrator
 * Date:     2019/5/7 22:40
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.Coach;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教练Dao
 *
 * @author Liuzw
 * @since 2019/5/7
 */
@Mapper
@Repository
public interface CoachDao extends BaseMapper<Coach> {

  @Select(SqlProperties.MAX_ID + EntityProperties.Coach.TABLE_NAME)
  Integer getMaxId();

  @Select(SqlProperties.SELECT + EntityProperties.Coach.TABLE_NAME + SqlProperties.WHERE + " NAME = #{name}")
  Coach findOneByName(String name);

}