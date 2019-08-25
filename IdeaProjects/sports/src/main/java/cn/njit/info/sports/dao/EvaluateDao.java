/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: EvaluateDao
 * Author:   Administrator
 * Date:     2019/5/19 0:10
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.Evaluate;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 评价Dao
 * @author Liuzw
 * @since 2019/5/19
 */
@Mapper
@Repository
public interface EvaluateDao extends BaseMapper<Evaluate> {
  @Select(SqlProperties.MAX_ID + " EVALUATE")
  Integer getMaxId();
}