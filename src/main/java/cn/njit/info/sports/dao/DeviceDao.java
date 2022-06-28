/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: DeviceDao
 * Author:   Administrator
 * Date:     2019/5/10 13:10
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.Device;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 设备Dao
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@Mapper
@Repository
public interface DeviceDao extends BaseMapper<Device> {
  @Select(SqlProperties.MAX_ID + EntityProperties.Device.TABLE_NAME)
  Integer getMaxId();
}