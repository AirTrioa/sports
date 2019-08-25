package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.ResourceIns;
import cn.njit.info.sports.pojo.enumeration.SqlProperties;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 资源实例
 *
 * @author Liuzw
 * @since 2019/3/29
 */
@Mapper
@Repository
public interface ResourceInsMapper extends AppBaseMapper<ResourceIns> {
  /**
   * 表名
   */
  String TABLE_NAME = "RESOURCE_INS";
  /**
   * findOneByCode
   */
  String FIND_ONE_SQL = SqlProperties.SELECT + TABLE_NAME + SqlProperties.WHERE + SqlProperties.ID;
  /**
   * saveOne
   */
  String SAVE_ONE = SqlProperties.INSERT + TABLE_NAME + "(MODEL_ID,ENTER_PRISE_ID)" + SqlProperties.VALUES + "(#{modelId},#{enterPriseId});";
  /**
   * UpdateOne
   */
  String UPDATE_ONE = SqlProperties.UPDATE + TABLE_NAME + SqlProperties.SET + "MODEL_ID=#{modelId},ENTER_PRISE_ID=#{enterPriseId} " + SqlProperties.WHERE + SqlProperties.ID;
  /**
   * DeleteOne
   */
  String DELETE_ONE = SqlProperties.DELETE + TABLE_NAME + SqlProperties.WHERE + SqlProperties.ID;

  @Select(FIND_ONE_SQL)
  @Override
  ResourceIns findOne(Integer id);

  @Insert(SAVE_ONE)
  @Override
  int save(ResourceIns resourceIns);

  @Update(UPDATE_ONE)
  @Override
  int update(ResourceIns resourceIns);

  @Delete(DELETE_ONE)
  @Override
  int delete(Integer id);
}
