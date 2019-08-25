/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: PriCourseController
 * Author:   Administrator
 * Date:     2019/5/18 22:08
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.DTO.CourseDTO;
import cn.njit.info.sports.pojo.entity.PriCourse;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.PriCourseService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 私教课程控制层
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@RestController
@RequestMapping("/sports/pri_course")
public class PriCourseController extends AppBaseController {
  private final PriCourseService meService;

  @Autowired
  public PriCourseController(PriCourseService meService) {
    this.meService = meService;
  }

  @PostMapping("")
  public RestResult create(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    if (index == -1)
      return RestResultUtils.returnError("新增失败，时段和您的公共课有冲突");
    if (index == -2)
      return RestResultUtils.returnError("新增失败，时段和您已发布的私教课有冲突");
    return RestResultUtils.returnCreate(index);
  }

  @Override
  public RestResult modify(String params) {
    return null;
  }

  @Override
  public RestResult delete(Integer id) {
    return null;
  }

  @GetMapping("/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    PriCourse details = meService.details(id);
    return RestResultUtils.returnSuccess(details);
  }

  @GetMapping("/list/{id}")
  public RestResult detail(@PathVariable(WebConstant.ID) Integer id) {
    PriCourse details = meService.details(id);
    List<PriCourse> list = new ArrayList<>();
    list.add(details);
    return RestResultUtils.returnSuccess(list);
  }

  @GetMapping(name = "获取全部私教课程的树", value = "/tree")
  public RestResult getTree() {
    List<TreeGroup> treeData = meService.getTreeData();
    return RestResultUtils.returnSuccess(treeData);
  }

  @GetMapping(name = "获取私教课程的树", value = "/coach/tree")
  public RestResult getTree(HttpSession session) {
    Map<String, String> userMap = (Map<String, String>) session.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    List<TreeGroup> treeData = meService.getTreeData(Integer.valueOf(userId));
    return RestResultUtils.returnSuccess(treeData);
  }

  @GetMapping(name = "获取私教课程表", value = "/course_table")
  public RestResult getPriCourseTable(HttpSession session) {
    Map<String, String> userMap = (Map<String, String>) session.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    List<CourseDTO> courseTable = meService.getPriCourseTable(Integer.valueOf(userId));
    return RestResultUtils.returnSuccess(courseTable);
  }

  @DeleteMapping("/course_name/{courseName}")
  public RestResult deleteByName(@PathVariable("courseName") String courseName,
                                 HttpSession session) {
    Map<String, String> userMap = (Map<String, String>) session.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    meService.deleteByName(courseName, Integer.valueOf(userId));
    return RestResultUtils.returnSuccess();
  }

  @GetMapping("/page")
  public RestResult page(@RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<PriCourse> page = meService.page(params);
    return RestResultUtils.returnSuccess(page);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "选择私教课程")
  @PostMapping("/info")
  public RestResult info(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.choosePriCourse(params);
    if(index == -2)
      return RestResultUtils.returnError("选课失败，该门课已被其他用户选走");
    if (index == -1)
      return RestResultUtils.returnError("选课失败，您已经选择过这门课");
    return RestResultUtils.returnCreate(index);
  }
}