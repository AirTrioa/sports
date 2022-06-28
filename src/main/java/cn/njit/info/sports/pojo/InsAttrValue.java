/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: InsAttrValue
 * Author:   Administrator
 * Date:     2019/3/20 10:46
 */
package cn.njit.info.sports.pojo;

/**
 * 实例属性值
 * @author Liuzw
 * @since 2019/3/20
 */
public class InsAttrValue extends BaseEntity{
  /**
   * 实例id
   */
  private Integer insId;
  /**
   * 对象id
   */
  private Integer modelId;
  /**
   * 属性id
   */
  private Integer attrId;
  /**
   * 属性名称
   */
  private String attrName;
  /**
   * 属性类型
   */
  private String attrType;
  /**
   * 实例属性值，JSON类型
   */
  private String value;

  public Integer getInsId() {
    return insId;
  }

  public void setInsId(Integer insId) {
    this.insId = insId;
  }

  public Integer getModelId() {
    return modelId;
  }

  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }

  public Integer getAttrId() {
    return attrId;
  }

  public void setAttrId(Integer attrId) {
    this.attrId = attrId;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public String getAttrType() {
    return attrType;
  }

  public void setAttrType(String attrType) {
    this.attrType = attrType;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}