/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserMapper
 * Author:   Administrator
 * Date:     2019/4/2 21:20
 */
package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.entity.User;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liuzw
 * @since 2019/4/2
 */
@Mapper
@Repository
public interface UserMapper extends AppBaseMapper<User> {

  @Override
  @Select(SqlProperties.SELECT + EntityProperties.User.TABLE_NAME + SqlProperties.WHERE + SqlProperties.USER_ID)
  User findOne(Integer userId);

  @Select("SELECT MAX(ID) FROM" + EntityProperties.User.TABLE_NAME)
  Integer getMaxId();

  @Select(SqlProperties.SELECT + EntityProperties.User.TABLE_NAME + SqlProperties.WHERE + " PHONE = #{phone}")
  User findOneByPhone(String phone);

  @Select(SqlProperties.SELECT + EntityProperties.User.TABLE_NAME + SqlProperties.WHERE + " EMAIL = #{email}")
  List<User> findAllByEmail(String email);
}