package cn.njit.info.sports.dao.mapper;

import cn.njit.info.sports.pojo.ResourceModel;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 资源数据访问层
 * @author Liuzw
 * @since 2019.3.20
 */
@Mapper
@Repository
public interface ResourceModelMapper {
  /**
   * 查询详细
   * @param id 主键
   * @return ResourceModel
   */
  @Select("SELECT * FROM RESOURCE_MODEL WHERE id = #{id}")
  @Results({
      @Result(id = true,property = EntityProperties.ResourceModel.ID,column = "ID"),
      @Result(property = EntityProperties.ResourceModel.CODE,column = "CODE"),
      @Result(property = EntityProperties.ResourceModel.NAME,column = "NAME"),
      @Result(property = EntityProperties.ResourceModel.ENTER_PRISE_ID,column = "ENTER_PRISE_ID")
  })
  ResourceModel findOne(Integer id);

  /**
   * 保存对象(create)
   * @param resourceModel 对象
   * @return 对象
   */
  @Insert("INSERT INTO RESOURCE_MODEL(CODE,NAME,ENTER_PRISE_ID) values(#{code},#{name},#{enterPriseId})")
  @Results({
      @Result(property = EntityProperties.ResourceModel.CODE,column = "CODE"),
      @Result(property = EntityProperties.ResourceModel.NAME,column = "NAME"),
      @Result(property = EntityProperties.ResourceModel.ENTER_PRISE_ID,column = "ENTER_PRISE_ID")
  })
  int save(ResourceModel resourceModel);

  @Update("UPDATE RESOURCE_MODEL SET CODE = #{code},NAME=#{name} where ID = #{id}")
  int update(ResourceModel resourceModel);

  @Delete("DELETE FROM RESOURCE_MODEL where ID = #{id}")
  int delete(Integer id);


  @Select("SELECT * FROM RESOURCE_MODEL WHERE CODE = #{code}")
  ResourceModel findOneByCode(String code);

}
