package cn.njit.info.sports.pojo;

import cn.njit.info.sports.pojo.enumeration.EntityProperties;

import java.util.Map;

/**
 * 资源对象
 * @author liuzw
 * @since 2019.3.20
 */
public class ResourceModel extends BaseEntity {
  /**
   * 对象编码
   */
  private String code;
  /**
   * 对象名字
   */
  private String name;

  public ResourceModel(){
    super();
  }

  public ResourceModel(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public ResourceModel(Integer id, Integer enterPriseId, String code, String name) {
    super(id, enterPriseId);
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   *  getObj
   * @param map result
   * @return Object
   */
  public static ResourceModel getObj(Map<String,Object> map){
    return new ResourceModel((Integer) map.get(EntityProperties.ResourceModel.ID),
        (Integer) map.get(EntityProperties.ResourceModel.ENTER_PRISE_ID),
        (String) map.get(EntityProperties.ResourceModel.CODE),
        (String) map.get(EntityProperties.ResourceModel.NAME));
  }

}