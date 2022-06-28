package cn.njit.info.sports.pojo.model;

import java.util.List;

/**
 * 辅助生成树实体类
 *
 * @author liuzw
 * @since 2019/4/16
 */
public class TreeGroup {
  /**
   * Id
   */
  private Long id;
  /**
   * Key关键字,不可重复
   */
  private String key;
  /**
   * 显示标题
   */
  private String title;
  /**
   * 父关键字
   */
  private String parentKey;
  /**
   * 是否是叶子节点
   */
  private boolean isIsLeaf;
  /**
   * 子列表
   */
  private List<TreeGroup> children;

  public TreeGroup() {
  }

  public TreeGroup(Long id, String key, String title, String parentKey, boolean isIsLeaf) {
    this.id = id;
    this.key = key;
    this.title = title;
    this.parentKey = parentKey;
    this.isIsLeaf = isIsLeaf;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getParentKey() {
    return parentKey;
  }

  public void setParentKey(String parentKey) {
    this.parentKey = parentKey;
  }

  public boolean isIsLeaf() {
    return isIsLeaf;
  }

  public void setIsLeaf(boolean isIsLeaf) {
    this.isIsLeaf = isIsLeaf;
  }

  public List<TreeGroup> getChildren() {
    return children;
  }

  public void setChildren(List<TreeGroup> children) {
    this.children = children;
  }
}
