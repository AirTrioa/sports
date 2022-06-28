/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ModelAttrServiceImplApp
 * Author:   Administrator
 * Date:     2019/3/29 22:29
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.mapper.ModelAttrMapper;
import cn.njit.info.sports.pojo.ModelAttr;
import cn.njit.info.sports.service.ModelAttrService;
import cn.njit.info.sports.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ModelAttrService
 * @author Liuzw
 * @since 2019/3/29
 */
@Service
public class ModelAttrServiceImpl implements ModelAttrService {
  private final ModelAttrMapper meDao;

  @Autowired
  public ModelAttrServiceImpl(ModelAttrMapper meDao) {
    this.meDao = meDao;
  }

  @Override
  public int create(String params) {
    ModelAttr modelAttr = JsonUtils.jsonToPojo(params, ModelAttr.class);
    return meDao.save(modelAttr);
  }

  @Override
  public void delete(Integer id) {
    meDao.delete(id);
  }

  @Override
  public int update(String params) {
    ModelAttr modelAttr = JsonUtils.jsonToPojo(params, ModelAttr.class);
    return meDao.update(modelAttr);
  }

  @Override
  public ModelAttr details(Integer id) {
    return meDao.findOne(id);
  }

  @Override
  public List<ModelAttr> page(String params) {
    return null;
  }
}