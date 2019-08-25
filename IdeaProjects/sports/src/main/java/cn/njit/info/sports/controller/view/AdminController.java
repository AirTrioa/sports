/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: LoginController
 * Author:   Administrator
 * Date:     2019/4/2 20:57
 */
package cn.njit.info.sports.controller.view;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.enumeration.Module;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端页面
 *
 * @author Liuzw
 * @since 2019/4/2
 */
@Controller
@RequestMapping("/")
public class AdminController {
  @GetMapping(name = "登录页面", value = "login")
  public String login() {
    return "redirect:/login-sign-up/index2.html";
  }

  @GetMapping(name = "宣传页面", value = "index")
  public String index() {
    return "redirect:/index.html";
  }

  @GetMapping(name = "测试页面", value = "test")
  public String test() {
    return "view/test";
  }

  @UserLog(module = Module.USER_MGR, opr = "进入会员管理页面")
  @GetMapping(name = "会员管理", value = "user")
  public String user() {
    return "view/user";
  }

  @UserLog(module = Module.COURSE_MGR, opr = "进入课程管理页面")
  @GetMapping(name = "课程管理", value = "course")
  public String course() {
    return "view/course";
  }

  @UserLog(module = Module.COURSE_MGR, opr = "进入课程分组页面")
  @GetMapping(name = "课程分组管理", value = "course_group")
  public String courseGroup() {
    return "view/course_group";
  }

  @UserLog(module = Module.USER_MGR, opr = "进入教练管理页面")
  @GetMapping(name = "教练管理", value = "coach")
  public String coach() {
    return "view/coach";
  }

  @UserLog(module = Module.ROOM_MGR, opr = "进入房间管理")
  @GetMapping(name = "房间管理", value = "room")
  public String room() {
    return "view/room";
  }

  @UserLog(module = Module.DEVICE_MGR, opr = "进入设备管理")
  @GetMapping(name = "设备管理", value = "device")
  public String device() {
    return "view/device";
  }

  @UserLog(module = Module.DATA_ALY, opr = "进入操作日志管理")
  @GetMapping(name = "操作日志管理", value = "opr_mgr")
  public String oprMgr() {
    return "view/opr_mgr";
  }

  @UserLog(module = Module.DATA_ALY, opr = "进入数据分析页面")
  @GetMapping(name = "数据分析", value = "data_sys")
  public String dataSys() {
    return "view/data_sys";
  }

  @UserLog(module = Module.USER_MGR, opr = "进入个人管理页面")
  @GetMapping(name = "个人设置", value = "user_setting")
  public String userSetting() {
    return "user/user_setting";
  }

  @UserLog(module = Module.COURSE_MGR, opr = "进入公共选课页面")
  @GetMapping(name = "选课页面", value = "course_info")
  public String chooseCourse() {
    return "user/course_info";
  }

  @UserLog(module = Module.COURSE_MGR, opr = "进入私教选课页面")
  @GetMapping(name = "私教课程页面", value = "pri_course")
  public String priCourse() {
    return "user/pri_course";
  }

  @UserLog(module = Module.DATA_ALY, opr = "进入评价页面")
  @GetMapping(name = "评价页面", value = "evaluate")
  public String evaluate() {
    return "user/evaluate";
  }

  @UserLog(module = Module.USER_MGR, opr = "进入教练课程设置")
  @GetMapping(name = "教练个人设置", value = "/coach_setting")
  public String coachSetting() {
    return "coach/coach_setting";
  }

  @UserLog(module = Module.COURSE_MGR, opr = "进入发布私教页面")
  @GetMapping(name = "发布私教页面", value = "/coach_pri_course")
  public String coachPriCourse() {
    return "coach/coach_pri_course";
  }
}