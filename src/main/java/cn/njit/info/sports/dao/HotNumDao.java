/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: HotNumDao
 * Author:   Administrator
 * Date:     2019/5/20 22:18
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.HotNum;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 热度Dao
 * @author Liuzw
 * @since 2019/5/20
 */
@Mapper
@Repository
public interface HotNumDao extends BaseMapper<HotNum> {

}