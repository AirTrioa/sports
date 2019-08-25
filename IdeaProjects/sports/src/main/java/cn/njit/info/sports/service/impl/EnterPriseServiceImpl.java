/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: EnterPriseServiceImpl
 * Author:   Administrator
 * Date:     2019/3/27 23:18
 */

package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.mapper.EnterPriseMapper;
import cn.njit.info.sports.pojo.EnterPrise;
import cn.njit.info.sports.service.EnterPriseService;
import cn.njit.info.sports.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业服务层实现类
 * @author Liuzw
 * @since 2019/3/27
 */

@Service
public class EnterPriseServiceImpl  implements EnterPriseService {
  private final EnterPriseMapper meDao;

  @Autowired
  public EnterPriseServiceImpl(EnterPriseMapper meDao) {
    this.meDao = meDao;
  }

  @Override
  public EnterPrise details(Integer id) {
    return meDao.findOne(id);
  }

  @Override
  public int create(String params) {
    EnterPrise enterPrise = JsonUtils.jsonToPojo(params, EnterPrise.class);
    return meDao.save(enterPrise);
  }

  @Override
  public void delete(Integer id) {
    meDao.delete(id);
  }

  @Override
  public int update(String params) {
    EnterPrise enterPrise = JsonUtils.jsonToPojo(params, EnterPrise.class);
    return meDao.update(enterPrise);
  }

  @Override
  public List<EnterPrise> page(String params) {
    return null;
  }
}
