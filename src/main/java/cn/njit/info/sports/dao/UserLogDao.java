/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UseLogDao
 * Author:   Administrator
 * Date:     2019/5/10 20:58
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.UserLogEntity;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 使用日志
 * @author Liuzw
 * @since 2019/5/10
 */
@Mapper
@Repository
public interface UserLogDao extends BaseMapper<UserLogEntity> {
  @Select(SqlProperties.MAX_ID+" USER_LOG")
  Integer getMaxId();

  @Select(SqlProperties.MAX_ID+" SYNC_INDEX")
  Integer getSyncMaxId();

}