/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: UserControllerApp
 * Author:   Administrator
 * Date:     2019/4/2 21:09
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.entity.User;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.UserService;
import cn.njit.info.sports.utils.ExcelUtils;
import cn.njit.info.sports.utils.MathUtils;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user
 *
 * @author Liuzw
 * @since 2019/4/2
 */
@RestController
@RequestMapping("/sports/user")
public class UserController {
  private final UserService meService;

  @Autowired
  public UserController(UserService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.USER_MGR, opr = "角色注册")
  @PostMapping(name = "注册", value = "")
  public RestResult create(@RequestParam String params) {
    int index = meService.create(params);
    if (index < 1) {
      return RestResultUtils.returnError("该用户信息不可用");
    }
    return RestResultUtils.returnSuccess();
  }

  @UserLog(module = Module.USER_MGR, opr = "角色删除")
  @DeleteMapping(name = "删除用户", value = "/{id}")
  public RestResult delete(@PathVariable(WebConstant.ID) Integer id) {
    meService.delete(id);
    return RestResultUtils.returnSuccess("删除成功");
  }

  @UserLog(module = Module.USER_MGR, opr = "用户编辑")
  @PutMapping(name = "编辑", value = "/{id}")
  public RestResult modify(@PathVariable(WebConstant.ID) Integer id,
                           @RequestParam(WebConstant.PARAMS) String params) {
    boolean update = meService.update(id, params);
    if (!update) {
      return RestResultUtils.returnError("更新用户信息失败");
    }
    return RestResultUtils.returnSuccess("更新成功");
  }

  @UserLog(module = Module.USER_MGR, opr = "个人信息编辑")
  @PutMapping(name = "个人信息编辑", value = "/user_id_user/{userId}")
  public RestResult modify(@PathVariable(WebConstant.USER_ID) String userId,
                           @RequestParam(WebConstant.PARAMS) String params) {
    boolean update = meService.update(userId, params);
    if (!update) {
      return RestResultUtils.returnError("更新用户信息失败");
    }
    return RestResultUtils.returnSuccess("更新成功");
  }

  @UserLog(module = Module.USER_MGR, opr = "查询用户详情")
  @GetMapping(name = "获取信息", value = "/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    User user = meService.details(id);
    if (null == user) {
      return RestResultUtils.returnError("不存在这样的Id!");
    }
    return RestResultUtils.returnSuccess(user);
  }

  //@UserLog(module = Module.USER_MGR, opr = "角色登录验证")
  @GetMapping(name = "验证登录信息", value = "/{userId}/{password}")
  public RestResult details(@PathVariable Integer userId,
                            @PathVariable String password,
                            HttpServletRequest request) {
    RestResult details = meService.details(userId, password);
    User user = meService.details(userId);
    if (null == user) {
      return RestResultUtils.returnError("没有这个用户");
    }
    HttpSession session = request.getSession();
    Map<String, String> userMap = new HashMap<>();
    userMap.put(WebConstant.USER_ID, userId.toString());
    userMap.put(WebConstant.USERNAME, user.getUsername());
    userMap.put(WebConstant.USER_URL, user.getUrl());
    session.setMaxInactiveInterval(15 * 60);
    session.setAttribute(WebConstant.USER_MAP, userMap);
    return details;
  }

  //@UserLog(opr = "角色分页查询", module = Module.USER_MGR)
  @GetMapping(name = "查询所有,假分页", value = "/page")
  public RestResult page() {
    List<User> page = meService.page();
    return RestResultUtils.returnSuccess(page);
  }

  //@UserLog(module = Module.USER_MGR, opr = "读取Excel")
  @PostMapping(name = "导入Excel", value = "/excel")
  public RestResult importExcel(@RequestParam(WebConstant.FILE) MultipartFile file) {
    boolean checkFile = ExcelUtils.checkFile(file);
    if (!checkFile)
      return RestResultUtils.returnError("请上传符合条件的文件!");
    meService.readExcel(file);
    return RestResultUtils.returnSuccess("导入成功");
  }

  @UserLog(module = Module.USER_MGR, opr = "Email验证")
  @PostMapping(name = "验证Email", value = "/email")
  public RestResult validEmail(@RequestParam(WebConstant.OBJ) String obj) {
    boolean result = meService.validEmail(obj);
    if (!result)
      return RestResultUtils.returnError("该email尚未注册!");
    return RestResultUtils.returnMessage("密码已经重置");
  }

  @UserLog(module = Module.USER_MGR, opr = "短信找回密码,验证手机号码")
  @PostMapping(name = "短信找回密码,验证手机号码", value = "/message")
  public RestResult validPhone(@RequestParam(WebConstant.OBJ) String obj) {
    boolean validMessage = meService.validMessage(obj);
    if (!validMessage)
      return RestResultUtils.returnError("该手机号码尚未注册!");
    String authCode = MathUtils.createAuthCode();
    while (authCode.length() < 6) {
      authCode = MathUtils.createAuthCode();
    }
    return RestResultUtils.returnSuccess(authCode);
  }

  @UserLog(module = Module.USER_MGR, opr = "通过手机号码来重置密码")
  @PostMapping(name = "通过手机号码来重置密码", value = "/phone")
  public RestResult resetPwdByPhone(@RequestParam(WebConstant.OBJ) String obj) {
    boolean validMessage = meService.validMessage(obj);
    if (!validMessage)
      return RestResultUtils.returnError("该手机号码尚未注册!");
    meService.resetPwd(obj);
    return RestResultUtils.returnMessage("密码已经修改重置");
  }

  //@UserLog(module = Module.USER_MGR, opr = "获取角色Session")
  @GetMapping(name = "获取角色Session", value = "/session")
  public RestResult getUserSession(HttpSession httpSession) {
    Object userList = httpSession.getAttribute(WebConstant.USER_MAP);
    return RestResultUtils.returnSuccess(userList);
  }

  @UserLog(module = Module.USER_MGR, opr = "注销登录")
  @DeleteMapping(name = "销毁Session", value = "/session")
  public RestResult destroySession(HttpSession httpSession) {
    //销毁Session
    httpSession.invalidate();
    return RestResultUtils.returnSuccess("Ok");
  }

  @UserLog(module = Module.USER_MGR, opr = "更换头像")
  @PostMapping(name = "修改头像", value = "/avatar/{id}")
  public RestResult changeAvatar(@PathVariable(WebConstant.ID) String id,
                                 @RequestParam("urlParam") String urlParam,
                                 HttpSession httpSession) {
    boolean tip = meService.changePicUrl(id, urlParam);
    if (!tip)
      return RestResultUtils.returnError("更换失败");
    User user = meService.getUserByUserId(id);
    Map<String, String> userMap = (Map<String, String>) httpSession.getAttribute(WebConstant.USER_MAP);
    userMap.remove(WebConstant.USER_URL);
    userMap.put(WebConstant.USER_URL, user.getUrl());
    httpSession.setAttribute(WebConstant.USER_MAP, userMap);
    return RestResultUtils.returnMessage("更换成功");
  }

  @UserLog(module = Module.USER_MGR, opr = "获取个人信息")
  @GetMapping(name = "根据userId获取详情", value = "/userId")
  public RestResult getOneByUserId(@RequestParam(WebConstant.USER_ID) String userId) {
    User user = meService.getUserByUserId(userId);
    return RestResultUtils.returnSuccess(user);
  }

}