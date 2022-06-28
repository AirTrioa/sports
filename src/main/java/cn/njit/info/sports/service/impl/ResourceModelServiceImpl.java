/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ResourceModelServiceImplApp
 * Author:   Administrator
 * Date:     2019/3/20 11:19
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.mapper.ResourceModelMapper;
import cn.njit.info.sports.pojo.ResourceModel;
import cn.njit.info.sports.service.ResourceModelService;
import cn.njit.info.sports.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 资源服务实现类
 * @author Liuzw
 * @since 2019/3/20
 */
@Service
public class ResourceModelServiceImpl implements ResourceModelService {
  private final ResourceModelMapper meDao;

  @Autowired
  public ResourceModelServiceImpl(ResourceModelMapper meDao) {
    this.meDao = meDao;
  }

  @Override
  public int create(String param) {
    ResourceModel resourceModel = JsonUtils.jsonToPojo(param, ResourceModel.class);
    //暂时不做验证
    return meDao.save(resourceModel);
  }

  @Override
  public ResourceModel details(Integer id) {
    return meDao.findOne(id);
  }



  @Override
  public void delete(Integer id) {
    meDao.delete(id);
  }

  @Override
  public int update(String params) {
    ResourceModel resourceModel = JsonUtils.jsonToPojo(params, ResourceModel.class);
    return meDao.update(resourceModel);
  }

  @Override
  public List<ResourceModel> page(String params) {
    return null;
  }
}