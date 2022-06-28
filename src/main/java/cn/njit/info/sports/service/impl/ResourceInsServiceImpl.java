/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ResourceInsServiceImplApp
 * Author:   Administrator
 * Date:     2019/3/30 13:43
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.mapper.ResourceInsMapper;
import cn.njit.info.sports.pojo.ResourceIns;
import cn.njit.info.sports.service.ResourceInsService;
import cn.njit.info.sports.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源实例
 * @author Liuzw
 * @since 2019/3/30
 */
@Service
public class ResourceInsServiceImpl implements ResourceInsService {
  private final ResourceInsMapper meDao;

  @Autowired
  public ResourceInsServiceImpl(ResourceInsMapper meDao) {
    this.meDao = meDao;
  }

  @Override
  public int create(String params) {
    ResourceIns resourceIns = JsonUtils.jsonToPojo(params, ResourceIns.class);
    return meDao.save(resourceIns);
  }

  @Override
  public void delete(Integer id) {
    meDao.delete(id);
  }

  @Override
  public int update(String params) {
    ResourceIns resourceIns = JsonUtils.jsonToPojo(params, ResourceIns.class);
    return meDao.update(resourceIns);
  }

  @Override
  public ResourceIns details(Integer id) {
    return meDao.findOne(id);
  }

  @Override
  public List<ResourceIns> page(String params) {
    return null;
  }
}