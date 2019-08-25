/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: SyncIndexDao
 * Author:   Administrator
 * Date:     2019/5/14 15:47
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.SyncIndex;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 操作记录
 *
 * @author Liuzw
 * @since 2019/5/14
 */
@Mapper
@Repository
public interface SyncIndexDao extends BaseMapper<SyncIndex> {

  @Update(SqlProperties.UPDATE+" SYNC_INDEX SET ID = #{id} WHERE TYPE = 'LOGGER'")
  int update(SyncIndex syncIndex);
}