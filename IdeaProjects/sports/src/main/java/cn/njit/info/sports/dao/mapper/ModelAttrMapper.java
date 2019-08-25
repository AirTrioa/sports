/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ModelAttrMapper
 * Author:   Administrator
 * Date:     2019/3/29 20:56
 */
package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.ModelAttr;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 对象属性
 *
 * @author Liuzw
 * @since 2019/3/29
 */
@Mapper
@Repository
public interface ModelAttrMapper extends AppBaseMapper<ModelAttr> {
  String TABLE_NAME = "MODEL_ATTR ";

  String FIND_ONE = SqlProperties.SELECT + TABLE_NAME + SqlProperties.WHERE + SqlProperties.ID;

  String SAVE_ONE = SqlProperties.INSERT + TABLE_NAME + "(MODEL_ID,ATTR_TYPE,ATTR_CODE,ATTR_NAME,ENTER_PRISE_ID)"
      + SqlProperties.VALUES + "(#{modelId},#{attrType},#{attrCode},#{attrName},#{enterPriseId})";

  String UPDATE_ONE = SqlProperties.UPDATE + TABLE_NAME + SqlProperties.SET + "MODEL_ID=#{modelId},ATTR_TYPE=#{attrType},ATTR_CODE=#{attrCode}" +
      ",ATTR_NAME=#{attrName},ENTER_PRISE_ID=#{enterPriseId}" + SqlProperties.WHERE + SqlProperties.ID;

  String DELETE_ONE = SqlProperties.DELETE + TABLE_NAME + SqlProperties.WHERE + SqlProperties.ID;

  @Select(FIND_ONE)
  @Override
  ModelAttr findOne(Integer id);

  @Insert(SAVE_ONE)
  @Override
  int save(ModelAttr modelAttr);

  @Update(UPDATE_ONE)
  @Override
  int update(ModelAttr modelAttr);

  @Delete(DELETE_ONE)
  @Override
  int delete(Integer id);

  @Select("SELECT * FROM MODEL_ATTR where MODEL_ID= #{modelId} and ATTR_CODE = #{attrCode};")
  ModelAttr findOne2(Integer modelId,String attrCode);
}