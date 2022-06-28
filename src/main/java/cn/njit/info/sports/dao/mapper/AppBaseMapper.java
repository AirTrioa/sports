/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: AppBaseMapper
 * Author:   Administrator
 * Date:     2019/3/29 21:46
 */
package cn.njit.info.sports.dao.mapper;

import org.apache.ibatis.annotations.Select;

/**
 *
 * @author Liuzw
 * @since 2019/3/29
 */
public interface AppBaseMapper<T> {
  T findOne(Integer id);
  int save(T t);
  int update(T t);
  int delete(Integer id);
}