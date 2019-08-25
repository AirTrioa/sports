/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: EnterPriseMapper
 * Author:   Administrator
 * Date:     2019/3/27 22:52
 */
package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.EnterPrise;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 企业表
 * @author Liuzw
 * @since 2019/3/27
 */
@Mapper
@Repository
public interface EnterPriseMapper {

  @Select("SELECT * FROM ENTER_PRISE WHERE id = #{id}")
  EnterPrise findOne(Integer id);

  @Insert("INSERT INTO ENTER_PRISE(CODE,NAME) values(#{code},#{name})")
  int save(EnterPrise enterPrise);

  @Update("UPDATE ENTER_PRISE SET CODE = #{code},NAME=#{name} where ID = #{id}")
  int update(EnterPrise enterPrise);

  @Delete("DELETE FROM ENTER_PRISE where ID = #{id}")
  int delete(Integer id);

}