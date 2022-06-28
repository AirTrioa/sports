package cn.njit.info.sports.service;

import java.util.List;

/**
 * 基础服务层
 *
 * @author liuzw
 * @since 2019.3.27
 */
public interface AppBaseService<T> {
  /**
   * 创建对象
   *
   * @param params 参数
   * @return int
   */
  int create(String params);

  /**
   * 删除对象
   *
   * @param id 主键
   */
  void delete(Integer id);

  /**
   * 更新对象
   *
   * @param params 参数
   * @return int
   */
  int update(String params);

  /**
   * 查询
   *
   * @param id 。。
   * @return 。。
   */
  T details(Integer id);

  /**
   * 分页查询
   *
   * @param params 参数
   * @return T list
   */
  List<T> page(String params);

}
