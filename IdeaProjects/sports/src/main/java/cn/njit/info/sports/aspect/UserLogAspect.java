/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserLogApsect
 * Author:   Administrator
 * Date:     2019/5/10 23:37
 */
package cn.njit.info.sports.aspect;

import cn.njit.info.sports.pojo.entity.UserLogEntity;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.UserLogService;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 切面
 *
 * @author Liuzw
 * @since 2019/5/10
 */
@Aspect
@Component
public class UserLogAspect {
  private static final Logger logger = LoggerFactory.getLogger(UserLogAspect.class);
  private final UserLogService meService;

  @Autowired
  public UserLogAspect(UserLogService meService) {
    this.meService = meService;
  }

  @Pointcut("@annotation(cn.njit.info.sports.aspect.UserLog)")//自定义接口实现
  public void LogPointCut() {
  }

  @Before("LogPointCut()")
  public void doBefore(JoinPoint joinPoint) {
    // 接收到请求，记录请求内容
    logger.info("UserLogAspect.doBefore()");
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (null == attributes) {
      logger.error("获取到的是空");
      return;
    }
    //获取注解中的值
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();
    UserLog userLog = method.getAnnotation(UserLog.class);
    //模块
    Module module = userLog.module();
    //操作
    String opr = userLog.opr();

    logger.info("操作模块:{}", module.getName());
    logger.info("操作详情:{}", opr);
    Integer userId = null;
    String username = null;
    //获取请求的值
    HttpServletRequest request = attributes.getRequest();
    Object userMap = request.getSession().getAttribute("userMap");
    if (null != userMap) {
      String userMapJsonStr = JSONObject.toJSONString(userMap);
      JSONObject jsonObject = JSONObject.parseObject(userMapJsonStr);
      userId = jsonObject.getInteger("userId");
      username = jsonObject.getString("username");
      logger.info("操作人的Id:{}", userId);
      logger.info("操作人:{}", username);
    }

    StringBuffer requestURL = request.getRequestURL();
    String requestMethod = request.getMethod();
    String remoteAddr = request.getRemoteAddr();
    String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    Date time = new Date();
    // 记录下请求内容
    logger.info("URL : {}", requestURL);
    logger.info("HTTP_METHOD : {}", requestMethod);
    logger.info("IP : {}", remoteAddr);
    logger.info("CLASS_METHOD :{}", methodName);
    logger.info("ARGS :{} ", args);
    logger.info("Time:{}", time);

    UserLogEntity logEntity = new UserLogEntity();
    logEntity.setCreateDate(time);
    logEntity.setMethod(methodName);
    logEntity.setOperation(opr);
    logEntity.setOprModule(module.getCode());
    logEntity.setParams(JSONObject.toJSONString(args));
    logEntity.setUserId(userId);
    logEntity.setUsername(username);
    meService.createEs(logEntity);
  }

  @AfterReturning(returning = "ret", pointcut = "LogPointCut()")
  public void doAfterReturning(RestResult ret) {
    // 处理完请求，返回内容
    logger.info("RESPONSE : {}", JSONObject.toJSON(ret.getData()));
  }
}