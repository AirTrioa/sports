/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ResourceModelVerify
 * Author:   Administrator
 * Date:     2019/3/26 22:54
 */
package cn.njit.info.sports.verify;

import cn.njit.info.sports.dao.mapper.ResourceModelMapper;
import cn.njit.info.sports.pojo.ResourceModel;
import cn.njit.info.sports.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


/**
 * 资源对象验证类
 *
 * @author Liuzw
 * @since 2019/3/26
 */
@Component
public class ResourceModelVerify {
  private static final Logger logger = LoggerFactory.getLogger(ResourceModelVerify.class);

  private final ResourceModelMapper meDao;

  @Autowired
  public ResourceModelVerify(ResourceModelMapper meDao) {
    this.meDao = meDao;
  }

  /**
   * 判空
   *
   * @param resourceModel 资源对象
   * @return true/false
   */
  private boolean isNotNull(ResourceModel resourceModel) {
    return null != meDao.findOne(resourceModel.getId());
  }

  /**
   * 校验是否可用
   *
   * @param param 前端传递的参数param
   * @return true/false
   */
  public static Boolean isUsed(String param) {
    ResourceModel resourceModel = JsonUtils.jsonToPojo(param, ResourceModel.class);
    if (ObjectUtils.isEmpty(resourceModel)) {
      logger.error("转换时发生异常:{}", param);
      return false;
    }
    //暂时先校验这么多，等下再校验其他
    return true;
  }
}