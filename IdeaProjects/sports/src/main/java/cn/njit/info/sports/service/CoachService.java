/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CoachService
 * Author:   Administrator
 * Date:     2019/5/7 22:42
 */
package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.DTO.CoachDTO;
import cn.njit.info.sports.pojo.entity.Coach;
import cn.njit.info.sports.pojo.entity.HotNum;

import java.util.List;
import java.util.Map;

/**
 * 教练服务
 *
 * @author Liuzw
 * @since 2019/5/7
 */
public interface CoachService extends AppBaseService<Coach> {

  /**
   * 分页查询
   *
   * @param params 参数
   * @return list
   */
  List<CoachDTO> pageDto(String params);

  Coach findOneByName(String name);

  Integer getCourseIdById(Integer id);

  /**
   * 获取教练比例
   *
   * @return list
   */
  List<Integer> getSexNum();

  /**
   * 获取前五热度的教练
   *
   * @return hot
   */
  List<HotNum> getFiveHot();
}