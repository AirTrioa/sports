/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroup
 * Author:   Administrator
 * Date:     2019/5/6 19:07
 */
package cn.njit.info.sports.pojo.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 课程分组
 *
 * @author Liuzw
 * @since 2019/5/6
 */
@TableName("COURSE_GROUP")
public class CourseGroup extends AppBaseEntity {
  /**
   * 分组名字
   */
  private String groupName;
  /**
   * 父分组id
   */
  private Integer parentId;
  /**
   * 是否是叶子节点
   */
  private boolean isLeaf;

  public CourseGroup() {
  }

  public CourseGroup(String groupName, Integer parentId, boolean isLeaf) {
    this.groupName = groupName;
    this.parentId = parentId;
    this.isLeaf = isLeaf;
  }

  public CourseGroup(Integer id, String groupName, Integer parentId, boolean isLeaf) {
    this.setId(id);
    this.groupName = groupName;
    this.parentId = parentId;
    this.isLeaf = isLeaf;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public boolean isLeaf() {
    return isLeaf;
  }

  public void setLeaf(boolean leaf) {
    isLeaf = leaf;
  }

  public static CourseGroup read(String params) {
    JSONObject jsonObject = JSONObject.parseObject(params);
    Integer id = jsonObject.getInteger("id");
    String groupName = jsonObject.getString("groupName");
    Integer parentId = jsonObject.getInteger("parentId");
    boolean leaf = jsonObject.getBooleanValue("isLeaf");
    return new CourseGroup(id, groupName, parentId, leaf);
  }
}