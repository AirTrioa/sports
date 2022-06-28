/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogServiceImpl
 * Author:   Administrator
 * Date:     2019/5/10 20:59
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.dao.SyncIndexDao;
import cn.njit.info.sports.dao.UserDao;
import cn.njit.info.sports.dao.UserLogDao;
import cn.njit.info.sports.pojo.DTO.UserLogDTO;
import cn.njit.info.sports.pojo.entity.SyncIndex;
import cn.njit.info.sports.pojo.entity.User;
import cn.njit.info.sports.pojo.entity.UserLogEntity;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.OprLogEntity;
import cn.njit.info.sports.pojo.enumeration.UserType;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.UserLogService;
import cn.njit.info.sports.utils.DateUtils;
import cn.njit.info.sports.utils.RestTemplateUtils;
import cn.njit.info.sports.utils.StringUtils;
import cn.njit.info.sports.utils.TreeUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.beans.Transient;
import java.util.*;

/**
 * UserLogServiceImpl
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@Service
public class UserLogServiceImpl implements UserLogService {
  private static final Logger logger = LoggerFactory.getLogger(UserLogServiceImpl.class);
  private final UserLogDao meDao;
  private final UserDao userDao;
  private final RestTemplate restTemplate;
  private final SyncIndexDao syncIndexDao;

  @Value("${restTemplate.url}")
  private String searchUrl;

  @Autowired
  public UserLogServiceImpl(UserLogDao meDao, UserDao userDao, RestTemplate restTemplate, SyncIndexDao syncIndexDao) {
    this.meDao = meDao;
    this.userDao = userDao;
    this.restTemplate = restTemplate;
    this.syncIndexDao = syncIndexDao;
  }

  @Override
  public int create(String params) {
    return 0;
  }

  @Override
  public void delete(Integer id) {
  }

  @Override
  public int update(String params) {
    return 0;
  }

  @Override
  public UserLogEntity details(Integer id) {
    String url = searchUrl + "/" + id;
    HttpEntity httpEntity = RestTemplateUtils.getHttpEntity();
    ResponseEntity<RestResult> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, RestResult.class);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      RestResult body = responseEntity.getBody();
      if (null == body)
        return null;
      String params = JSONObject.toJSONString(body.getData());
      logger.info("restTemplate读取到的参数:{}", params);
      return UserLogEntity.read(params);
    }
    return null;
  }

  @Override
  public List<UserLogEntity> page(String params) {
    UserLogEntity entity = UserLogEntity.read(params);
    QueryWrapper<UserLogEntity> qw = new QueryWrapper<>();
    logger.info("UserLog分页查询的条件:{}", params);
    String username = entity.getUsername();
    if (null != username) {
      qw.like(OprLogEntity.USER_NAME.getCode(), username);
    }
    Integer userId = entity.getUserId();
    if (null != userId) {
      qw.eq(OprLogEntity.USER_ID.getCode(), userId);
    }
    String moduleName = entity.getOprModule();
    if (null != moduleName) {
      qw.eq(OprLogEntity.OPR_MODULE.getCode(), Module.codeFromValue(moduleName));
    }
    Date createDate = entity.getCreateDate();
    if (null != createDate) {
      qw.eq(OprLogEntity.CREATE_DATE.getCode(), createDate);
    }
    //按时间排序
    qw.orderByAsc(OprLogEntity.CREATE_DATE.getCode());
    List<UserLogEntity> entities = meDao.selectList(qw);
    for (UserLogEntity logEntity : entities) {
      String moduleCode = logEntity.getOprModule();
      if (null != moduleCode) {
        Module module = Module.fromCode(moduleCode);
        if (StringUtils.isNotBlank(module.getName()))
          logEntity.setOprModule(module.getName());
      }
    }
    return entities;
  }

  public List<UserLogEntity> pageEs(String params) {
    Map<String, Object> uriVariables = new HashMap<>();
    uriVariables.put("param", params);
    String url = searchUrl + "/page" + "?params={param}";
    logger.info("URL：{}", url);
    HttpEntity httpEntity = RestTemplateUtils.getHttpEntity();
    ResponseEntity<RestResult> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, RestResult.class, uriVariables);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      RestResult body = responseEntity.getBody();
      if (null == body)
        return Collections.emptyList();
      String jsonStr = JSONObject.toJSONString(body.getData());
      logger.info("请求的参数:{}", params);
      List<UserLogEntity> entities = JSONObject.parseArray(jsonStr, UserLogEntity.class);
      for (UserLogEntity entity : entities) {
        String moduleCode = entity.getOprModule();
        if (null != Module.fromCode(moduleCode))
          entity.setOprModule(Module.fromCode(moduleCode).getName());
      }
      return entities;
    }
    return Collections.emptyList();
  }


  public synchronized int create(UserLogEntity userLogEntity) {
    Integer maxId = meDao.getMaxId();
    if (null == maxId)
      maxId = 1000;
    userLogEntity.setId(maxId + 1);
    return meDao.insert(userLogEntity);
  }

  @Override
  public Integer getMaxId() {
    return meDao.getMaxId();
  }

  @Override
  public List<TreeGroup> getTree() {
    List<TreeGroup> groupList = new ArrayList<>();
    List<User> users = userDao.selectList(new QueryWrapper<>());
    for (User user : users) {
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setId(Long.valueOf(user.getUserId()));
      treeGroup.setTitle(user.getUsername());
      treeGroup.setKey(user.getUserId());
      treeGroup.setParentKey(user.getType());
      treeGroup.setIsLeaf(true);
      groupList.add(treeGroup);
    }
    groupList.addAll(addParent());

    TreeGroup root = TreeUtils.buildTree("ROOT", groupList);
    return root.getChildren();
  }

  /**
   * 构造角色的上层节点
   *
   * @return 树列表
   */
  private List<TreeGroup> addParent() {
    List<TreeGroup> list = new ArrayList<>();
    for (UserType type : UserType.values()) {
      TreeGroup treeGroup = new TreeGroup();
      treeGroup.setKey(type.getCode());
      treeGroup.setParentKey("ROOT");
      treeGroup.setIsLeaf(false);
      treeGroup.setTitle(type.getName());
      list.add(treeGroup);
    }
    return list;
  }

  @Override
  public List<UserLogEntity> getLogsByUserId(String userId) {
    QueryWrapper<UserLogEntity> qw = new QueryWrapper<>();
    qw.eq("USER_ID", userId);
    qw.orderByAsc("CREATE_DATE");
    return meDao.selectList(qw);
  }

  @Override
  public List<UserLogEntity> getLogsByUserIdEs(String id) {
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.eq("USER_ID", id);
    User user = userDao.selectOne(qw);
    UserLogEntity userLogEntity = new UserLogEntity();
    userLogEntity.setUsername(user.getUsername());
    String params = JSONObject.toJSONString(userLogEntity);
    logger.info("构造而成的参数:{}", params);
    List<UserLogEntity> entities = pageEs(params);
    List<UserLogEntity> resultList = new ArrayList<>();
    for (UserLogEntity log : entities) {
      if (log.getUsername().equals(user.getUsername())) {
        resultList.add(log);
      }
    }
    return resultList;
  }

  @Override
  public synchronized int createEs(UserLogEntity userLogEntity) {
    QueryWrapper<SyncIndex> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("TYPE", "LOGGER");
    SyncIndex syncIndex = syncIndexDao.selectOne(queryWrapper);
    if (null == syncIndex) {
      syncIndex = new SyncIndex(3000);
      syncIndex.setType("LOGGER");
      syncIndexDao.insert(syncIndex);
    }
    Integer syncMaxId = syncIndex.getId();
    userLogEntity.setId(syncMaxId + 1);
    syncIndex.setId(syncMaxId + 1);
    syncIndexDao.update(syncIndex);


    UserLogDTO userLogDTO = new UserLogDTO(userLogEntity);
    Map<String, Object> uriVariables = new HashMap<>();
    String param = JSONObject.toJSONString(userLogDTO);
    uriVariables.put("param", param);
    logger.info("param:{}", param);
    String url = searchUrl + "" + "?params={param}";
    logger.info("URL：{}", url);
    HttpEntity httpEntity = RestTemplateUtils.getHttpEntity();
    ResponseEntity<RestResult> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, RestResult.class, uriVariables);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      RestResult body = responseEntity.getBody();
      if (null != body && body.getStatus() == 200) {
        return 1;
      }
    }
    return 0;
  }

  @Override
  public List<UserLogEntity> search(String params) {
    Map<String, Object> uriVariables = new HashMap<>();
    uriVariables.put("param", params);
    String url = searchUrl + "/search" + "?str={param}";
    logger.info("URL：{}", url);
    HttpEntity httpEntity = RestTemplateUtils.getHttpEntity();
    ResponseEntity<RestResult> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, RestResult.class, uriVariables);
    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      RestResult body = responseEntity.getBody();
      if (null == body)
        return Collections.emptyList();
      Object data = body.getData();
      String jsonStr = JSONObject.toJSONString(data);
      List<UserLogEntity> entities = JSONObject.parseArray(jsonStr, UserLogEntity.class);
      for (UserLogEntity entity : entities) {
        String moduleCode = entity.getOprModule();
        if (null != Module.fromCode(moduleCode))
          entity.setOprModule(Module.fromCode(moduleCode).getName());
      }
      return entities;
    }
    return Collections.emptyList();
  }

  @Override
  public List<Integer> getPageViewNum() {
    //管理员登录跳转页面
    String method = "cn.njit.info.sports.controller.view.AdminController.dataSys";
    List<UserLogEntity> logEntities = getAllByMethod(method);
    //会员登录管理页面
    String userSetting = "cn.njit.info.sports.controller.view.AdminController.userSetting";
    List<UserLogEntity> allByMethod = getAllByMethod(userSetting);
    //教练登录
    String coachSetting = "cn.njit.info.sports.controller.view.AdminController.coachSetting";
    List<UserLogEntity> coachMethod = getAllByMethod(coachSetting);

    logEntities.addAll(allByMethod);
    logEntities.addAll(coachMethod);
    int first = 0;
    int second = 0;
    int third = 0;
    int fourth = 0;
    int firth = 0;
    int sixth = 0;
    int seventh = 0;
    for (UserLogEntity log : logEntities) {
      Date createDate = log.getCreateDate();
      int diffNow = (int) DateUtils.getDiffNow(createDate);
      switch (diffNow) {
        case 0:
          first++;
          break;
        case 1:
          second++;
          break;
        case 2:
          third++;
          break;
        case 3:
          fourth++;
          break;
        case 4:
          firth++;
          break;
        case 5:
          sixth++;
          break;
        case 6:
          seventh++;
          break;
        default:
          break;
      }
    }
    List<Integer> resultList = new ArrayList<>();
    resultList.add(seventh);
    resultList.add(sixth);
    resultList.add(firth);
    resultList.add(fourth);
    resultList.add(third);
    resultList.add(second);
    resultList.add(first);
    return resultList;
  }

  public List<UserLogEntity> getAllByMethod(String method) {
    UserLogEntity logEntity = new UserLogEntity();
    logEntity.setMethod(method);
    logEntity.setOprModule(Module.USER_MGR.getCode());
    String params = JSONObject.toJSONString(logEntity);
    List<UserLogEntity> logEntities = pageEs(params);
    if (CollectionUtils.isEmpty(logEntities)) {
      return Collections.emptyList();
    }
    return logEntities;
  }
}
