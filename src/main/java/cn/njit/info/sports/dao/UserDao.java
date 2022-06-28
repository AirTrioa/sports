/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserDao
 * Author:   Administrator
 * Date:     2019/4/27 22:05
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户Dao
 *
 * @author Liuzw
 * @since 2019/4/27
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {
}