package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.InsAttrValue;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * InsAttrValue
 *
 * @author Liuzw
 * @since 2019/3/30
 */
@Mapper
@Repository
public interface InsAttrValueMapper extends AppBaseMapper<InsAttrValue> {
  String TABLE_NAME = "INS_ATTR_VALUE ";
  String FIND_ONE = SqlProperties.SELECT + TABLE_NAME + SqlProperties.WHERE + SqlProperties.ID;

  String SAVE_ONE = SqlProperties.INSERT + TABLE_NAME + "(INS_ID,MODEL_ID,ATTR_ID,ATTR_NAME,ATTR_TYPE,VALUE,ENTER_PRISE_ID)"
      + SqlProperties.VALUES + "(#{insId},#{modelId},#{attrId},#{attrName},#{attrType},#{value},#{enterPriseId})";

  String UPDATE_ONE = SqlProperties.UPDATE + TABLE_NAME + SqlProperties.SET +
      "INS_ID=#{insId}," +
      "MODEL_ID=#{modelId}," +
      "ATTR_ID=#{attrId}," +
      "ATTR_NAME=#{attrName}," +
      "ATTR_TYPE=#{attrType}," +
      "VALUE=#{value}," +
      "ENTER_PRISE_ID=#{enterPriseId}"
      + SqlProperties.WHERE + SqlProperties.ID;

  String DELETE_ONE = SqlProperties.DELETE + TABLE_NAME + SqlProperties.WHERE + SqlProperties.ID;

  @Select(FIND_ONE)
  @Override
  InsAttrValue findOne(Integer id);

  @Insert(SAVE_ONE)
  @Override
  int save(InsAttrValue insAttrValue);

  @Update(UPDATE_ONE)
  @Override
  int update(InsAttrValue insAttrValue);

  @Delete(DELETE_ONE)
  @Override
  int delete(Integer id);

  @Select(SqlProperties.SELECT+TABLE_NAME+SqlProperties.WHERE+"MODEL_ID=#{param1} and ATTR_ID=#{param2} and VALUE=#{param3};")
  @Results({
      @Result(property = "id",column = "ID"),
      @Result(property = "insId",column = "INS_ID"),
      @Result(property = "modelId",column = "MODEL_ID"),
      @Result(property = "attrId",column = "ATTR_ID"),
      @Result(property = "attrName",column = "ATTR_NAME"),
      @Result(property = "attrType",column = "ATTR_TYPE"),
      @Result(property = "value",column = "VALUE"),
      @Result(property = EntityProperties.ResourceModel.ENTER_PRISE_ID,column = "ENTER_PRISE_ID")
  })
  List<InsAttrValue> findAll(Integer modelId,Integer attrId,String value);

  @Select(SqlProperties.SELECT+TABLE_NAME+SqlProperties.WHERE+"INS_ID=#{param1} and ATTR_ID=#{param2};")
  @Results({
      @Result(property = "id",column = "ID"),
      @Result(property = "insId",column = "INS_ID"),
      @Result(property = "modelId",column = "MODEL_ID"),
      @Result(property = "attrId",column = "ATTR_ID"),
      @Result(property = "attrName",column = "ATTR_NAME"),
      @Result(property = "attrType",column = "ATTR_TYPE"),
      @Result(property = "value",column = "VALUE"),
      @Result(property = EntityProperties.ResourceModel.ENTER_PRISE_ID,column = "ENTER_PRISE_ID")
  })
  InsAttrValue findOne2(Integer insId,Integer attrId);
}
