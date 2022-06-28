/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserServiceImplApp
 * Author:   Administrator
 * Date:     2019/4/2 21:18
 */
package cn.njit.info.sports.service.impl;

import cn.njit.info.sports.dao.*;
import cn.njit.info.sports.dao.mapper.*;
import cn.njit.info.sports.pojo.InsAttrValue;
import cn.njit.info.sports.pojo.ModelAttr;
import cn.njit.info.sports.pojo.ResourceModel;
import cn.njit.info.sports.pojo.entity.Coach;
import cn.njit.info.sports.pojo.entity.User;
import cn.njit.info.sports.pojo.enumeration.EntityProperties;
import cn.njit.info.sports.pojo.enumeration.UserType;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.MailService;
import cn.njit.info.sports.service.UserService;
import cn.njit.info.sports.utils.*;
import cn.njit.info.sports.verify.VerifyUser;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author Liuzw
 * @since 2019/4/2
 */
@Service
public class UserServiceImpl implements UserService {

  private final ResourceModelMapper resourceModelDao;
  private final InsAttrValueMapper insAttrValueDao;
  private final UserMapper meMapper;
  private final SuperBaseMapper baseMapper;
  private final UserDao meDao;
  private final VerifyUser verifyUser;
  private final MailService mailService;
  private final CoachDao coachDao;
  //日志
  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  public UserServiceImpl(ResourceModelMapper resourceModelDao,
                         InsAttrValueMapper insAttrValueDao,
                         UserMapper meMapper,
                         SuperBaseMapper baseMapper,
                         UserDao meDao,
                         VerifyUser verifyUser,
                         MailService mailService,
                         CoachDao coachDao) {
    this.resourceModelDao = resourceModelDao;
    this.insAttrValueDao = insAttrValueDao;
    this.meMapper = meMapper;
    this.baseMapper = baseMapper;
    this.meDao = meDao;
    this.verifyUser = verifyUser;
    this.mailService = mailService;
    this.coachDao = coachDao;
  }

  @Override
  public int create(String params) {
    JSONObject jsonObject = parseObject(params);
    String username = jsonObject.getString(EntityProperties.User.USERNAME);
    String email = jsonObject.getString(EntityProperties.User.EMAIL);
    Integer age = jsonObject.getInteger("age");
    String password = jsonObject.getString(EntityProperties.Common.CREDENTIAL);
    String phone = jsonObject.getString(EntityProperties.User.PHONE);
    //查询条件
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("PHONE", phone);
    User user1 = meDao.selectOne(queryWrapper);
    if (null != user1) {
      return 0;
    }
    Integer maxId = meMapper.getMaxId();
    User user = new User();
    String userId = MathUtils.createUserId();
    user.setUserId(userId);
    user.setPhone(phone);
    user.setAge(age);

    while (!verifyUser.validUserId(user)) {
      userId = MathUtils.createUserId();
      user.setUserId(userId);
    }

    if (!verifyUser.validPhone(user)) {
      return 0;
    }

    if (!verifyUser.validEmail(user)) {
      return 0;
    }
    user.setId(maxId + 1);
    user.setUsername(username);
    user.setEmail(email);

    //passwordMD5加密
    String pwd = MD5Utils.encrypt(password);
    user.setPassword(pwd);

    //默认会员
    user.setType("normal");
    return meDao.insert(user);
  }

  @Override
  public void delete(Integer id) {
    meDao.deleteById(id);
  }


  @Override
  public int update(String params) {
    return 0;
  }

  @Override
  public User details(Integer id) {
    return meMapper.findOne(id);
  }

  @Override
  public boolean isUser(Integer userId, String password) {
    //User的code为user
    ResourceModel user = resourceModelDao.findOneByCode("user");
    Integer modelId = user.getId();
    //获取userId
    String sql = "SELECT * FROM MODEL_ATTR WHERE MODEL_ID = " + modelId + " and ATTR_CODE = 'userId';";
    List<LinkedHashMap<String, Object>> mapList = baseMapper.superSelect(sql);
    ModelAttr modelAttrOnUserId = ModelAttr.getObj(mapList.get(0));
    Integer attrId = modelAttrOnUserId.getId();
    //获取password
    String sql2 = "SELECT * FROM MODEL_ATTR WHERE MODEL_ID = " + modelId + " and ATTR_CODE = 'password';";
    List<LinkedHashMap<String, Object>> mapList2 = baseMapper.superSelect(sql2);
    ModelAttr modelAttrOnPassword = ModelAttr.getObj(mapList2.get(0));
    Integer attrIdPwd = modelAttrOnPassword.getId();
    List<InsAttrValue> userIds = insAttrValueDao.findAll(modelId, attrId, userId.toString());
    if (CollectionUtils.isEmpty(userIds)) {
      //不存在这样的id
      return false;
    }
    Integer insId = userIds.get(0).getInsId();
    //通过InsId和attrId查询密码结果
    InsAttrValue one = insAttrValueDao.findOne2(insId, attrIdPwd);
    String passwordValue = one.getValue();
    //获取加密后的密码
    String encrypt = MD5Utils.encrypt(password);
    return passwordValue.equals(encrypt);
  }

  @Override
  public RestResult details(Integer id, String password) {
    User one = meMapper.findOne(id);
    if (null == one) {
      return RestResultUtils.returnError("不存在这个Id");
    }
    String encryptPwd = MD5Utils.encrypt(password);
    //比较加密后的密码和数据库密码是否一致
    if (StringUtils.isBlank(encryptPwd) || !encryptPwd.equals(one.getPassword())) {
      return RestResultUtils.returnError("密码不正确");
    }
    return RestResultUtils.returnSuccess(one);
  }

  @Override
  public List<User> page() {
    QueryWrapper<User> qw = new QueryWrapper<>();
    qw.eq("TYPE", "normal");
    return meDao.selectList(qw);
  }

  @Override
  public int create(User user) {
    return meDao.insert(user);
  }

  @Transactional
  public boolean update(Integer id, String params) {
    User user = meDao.selectById(id);
    JSONObject jsonObject = parseObject(params);
    String username = jsonObject.getString(EntityProperties.User.USERNAME);
    String email = jsonObject.getString(EntityProperties.User.EMAIL);
    String password = jsonObject.getString(EntityProperties.Common.CREDENTIAL);
    String phone = jsonObject.getString(EntityProperties.User.PHONE);
    Integer age = jsonObject.getInteger(EntityProperties.User.AGE);
    //密码的二次验证
    if (!password.equals("******")) {
      String encrypt = MD5Utils.encrypt(password);
      user.setPassword(encrypt);
    }
    user.setUsername(username);
    user.setEmail(email);
    user.setPhone(phone);
    user.setAge(age);
    //教练更新信息
    if (user.getType().equals(UserType.COACH.getCode())) {
      Coach coach = coachDao.selectById(user.getUserId());
      coach.setName(username);
      coach.setAge(age);
      coachDao.updateById(coach);
    }
    return meDao.updateById(user) > 0;
  }

  @Override
  public boolean update(String userId, String params) {
    User one = meMapper.findOne(Integer.valueOf(userId));
    return update(one.getId(), params);
  }

  @Override
  public void readExcel(MultipartFile file) {
    try {
      List<String[]> excelData = ExcelUtils.getExcelData(file);
      for (int i = 1; i < excelData.size(); i++) {
        String[] data = excelData.get(i);
        logger.info("Excel解析:第{}行,内容:{}", i, data);
        String phone = data[2];
        User one = meMapper.findOneByPhone(phone);
        if (null == one) {
          //若用手机号码查不出数据，说明是新增
          one = new User();
        }
        //若不为空，则是更新
        one.setUsername(data[0]);
        one.setAge(Integer.valueOf(data[1]));
        one.setPhone(phone);
        one.setEmail(data[3]);
        one.setPassword(MD5Utils.encrypt(data[4]));
        if (!verifyUser.validEmail(one)) {
          logger.error("邮箱不正确");
          return;
        }
        if (null != one.getId()) {
          meDao.updateById(one);
          return;
        }
        one.setType("normal");
        one.setUserId(MathUtils.createUserId());
        while (!verifyUser.validUserId(one)) {
          String userId = MathUtils.createUserId();
          one.setUserId(userId);
        }
        one.setId(meMapper.getMaxId() + 1);
        meDao.insert(one);
      }
    } catch (Exception e) {
      logger.error("解析上传的Excel文件时发生异常", e);
    }
  }


  @Override
  public boolean validEmail(String obj) {
    JSONObject jsonObject = parseObject(obj);
    String email = jsonObject.getString("email");
    List<User> ones = meMapper.findAllByEmail(email);
    if (!CollectionUtils.isEmpty(ones)) {
      resetPwd(email, ones.get(0));
    }
    return !CollectionUtils.isEmpty(ones);
  }

  @Override
  public void resetPwd(String email, User user) {
    String newPassword = MathUtils.createUserId();
    String message = "尊敬的" + user.getUsername() + ",您的NJIT健身云密码已经重置,新密码是" + newPassword + ",请登陆后进行修改";
    user.setPassword(MD5Utils.encrypt(newPassword));
    meDao.updateById(user);
    mailService.sendSimpleMail(email, "找回密码", message);
  }

  @Override
  public boolean validMessage(String obj) {
    JSONObject jsonObject = parseObject(obj);
    String phone = jsonObject.getString("phone");
    User one = meMapper.findOneByPhone(phone);
    return null != one;
  }

  @Override
  public void resetPwd(String obj) {
    JSONObject jsonObject = parseObject(obj);
    String phone = jsonObject.getString("phone");
    String password = jsonObject.getString("password");
    User one = meMapper.findOneByPhone(phone);
    one.setPassword(MD5Utils.encrypt(password));
    meDao.updateById(one);
  }

  @Override
  public List<User> page(String params) {
    return null;
  }


  @Override
  public boolean changePicUrl(String userId, String urlParam) {
    User user = meMapper.findOne(Integer.valueOf(userId));
    if (null == user) {
      return false;
    }
    String str = "http://localhost:8081/img/";
    int length = str.length();
    String jpg = urlParam.substring(length);
    logger.info("用户:{}的新的头像:{}", user.getUsername(), jpg);
    if (jpg.length() <= 6) {
      jpg = "/img/" + jpg;
    }
    user.setUrl(jpg);
    meDao.updateById(user);
    return true;
  }

  @Override
  public User getUserByUserId(String userId) {
    return meMapper.findOne(Integer.valueOf(userId));
  }

}