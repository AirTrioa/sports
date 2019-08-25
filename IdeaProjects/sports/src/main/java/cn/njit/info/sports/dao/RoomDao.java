/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: RoomDao
 * Author:   Administrator
 * Date:     2019/5/9 21:06
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.Room;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 房间Dao
 *
 * @author Liuzw
 * @since 2019/5/9
 */
@Mapper
@Repository
public interface RoomDao extends BaseMapper<Room> {
  @Select(SqlProperties.MAX_ID + EntityProperties.Room.TABLE_NAME)
  Integer getMaxId();

  @Select(SqlProperties.SELECT+EntityProperties.Room.TABLE_NAME+SqlProperties.WHERE+"NAME = #{name}")
  Room findOneByName(String name);
}