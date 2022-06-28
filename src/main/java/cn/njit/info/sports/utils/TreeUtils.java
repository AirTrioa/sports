package cn.njit.info.sports.utils;

import cn.njit.info.sports.pojo.model.TreeGroup;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具类
 *
 * @author liuzw
 * @since 2019/4/17
 */
public class TreeUtils {
  private TreeUtils() {
  }

  /**
   * 创建一个根节点
   *
   * @return ROOT节点
   */
  private static TreeGroup createRoot() {
    TreeGroup root = new TreeGroup();
    root.setId(Long.MAX_VALUE);
    root.setTitle("根节点");
    root.setKey("ROOT");
    root.setParentKey("根节点");
    root.setIsLeaf(false);
    return root;
  }

  /**
   * 以当前Key的TreeGroup为根节点构建树
   *
   * @param rootKey 根关键字
   * @param list    源列表
   * @return Root节点, 子列表就是树
   */
  public static TreeGroup buildTree(String rootKey, List<TreeGroup> list) {
    TreeGroup rootNode = createRoot();
    list.add(rootNode);
    TreeGroup root = getTreeGroupByKey(rootKey, list);
    if (null != root) {
      List<TreeGroup> children = buildChildren(root.getKey(), list);
      root.setChildren(children);
    }
    return root;
  }

  /**
   * 递归构建子列表
   *
   * @param parentKey 父关键字
   * @param list      源列表
   * @return TreeGroup列表
   */
  private static List<TreeGroup> buildChildren(String parentKey, List<TreeGroup> list) {
    List<TreeGroup> children = new ArrayList<>();
    for (TreeGroup treeGroup : list) {
      //防止父节点空导致空指针
      if (null != treeGroup.getParentKey() && treeGroup.getParentKey().equals(parentKey)) {
        children.add(treeGroup);
      }
    }
    //若子列表不是空
    if (CollectionUtils.isNotEmpty(children)) {
      for (TreeGroup tg : children) {
        List<TreeGroup> childList = new ArrayList<>();
        if (!tg.isIsLeaf()) {
          //若不是叶子节点
          childList = buildChildren(tg.getKey(), list);
        }
        tg.setChildren(childList);
      }
    }
    return children;
  }

  /**
   * 通过Key获取TreeGroup
   *
   * @param key  key
   * @param list 源列表
   * @return TreeGroup
   */
  private static TreeGroup getTreeGroupByKey(String key, List<TreeGroup> list) {
    for (TreeGroup treeGroup : list) {
      if (treeGroup.getKey().equals(key)) {
        return treeGroup;
      }
    }
    return null;
  }

  /**
   * 递归获取某个“parentKey”的所有子节点列表
   *
   * @param list      源列表
   * @param parentKey 父关键字
   * @param childList 子列表
   */
  public static void getChildList(List<TreeGroup> list, String parentKey, List<TreeGroup> childList) {
    for (TreeGroup treeGroup : list) {
      if (parentKey.equals(treeGroup.getParentKey())) {
        getChildList(list, treeGroup.getKey(), childList);
        childList.add(treeGroup);
      }
    }
  }

  /**
   * 递归获取某个“parentKey”的所有叶子节点列表
   *
   * @param list      源列表
   * @param parentKey 父关键字
   * @param childList 子列表
   */
  public static void getLeafChildList(List<TreeGroup> list, String parentKey, List<TreeGroup> childList) {
    for (TreeGroup treeGroup : list) {
      if (parentKey.equals(treeGroup.getParentKey())) {
        getChildList(list, treeGroup.getKey(), childList);
        if (treeGroup.isIsLeaf()) {
          childList.add(treeGroup);
        }
      }
    }
  }
}
