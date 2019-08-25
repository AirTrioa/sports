/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ModelAttr
 * Author:   Administrator
 * Date:     2019/3/20 10:43
 */
package cn.njit.info.sports.pojo;

import java.util.Map;

/**
 * 对象属性
 * @author Liuzw
 * @since 2019/3/20
 */
public class ModelAttr extends BaseEntity{
  /**
   * 对象id
   */
  private Integer modelId;
  /**
   * 属性类型,枚举类
   */
  private String attrType;
  /**
   * 属性编码
   */
  private String attrCode;
  /**
   * 属性名字
   */
  private String attrName;

  public Integer getModelId() {
    return modelId;
  }

  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }

  public String getAttrType() {
    return attrType;
  }

  public void setAttrType(String attrType) {
    this.attrType = attrType;
  }

  public String getAttrCode() {
    return attrCode;
  }

  public void setAttrCode(String attrCode) {
    this.attrCode = attrCode;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public static ModelAttr getObj(Map map){
    ModelAttr modelAttr = new ModelAttr();
    modelAttr.setId((Integer) map.get("ID"));
    modelAttr.setAttrCode(map.get("ATTR_CODE").toString());
    modelAttr.setAttrName(map.get("ATTR_NAME").toString());
    modelAttr.setAttrType(map.get("ATTR_TYPE").toString());
    modelAttr.setModelId((Integer)map.get("MODEL_ID"));
    modelAttr.setEnterPriseId((Integer)map.get("ENTER_PRISE_ID"));
    return modelAttr;
  }
}