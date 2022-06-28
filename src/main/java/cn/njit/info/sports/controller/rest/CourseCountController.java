/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseCountController
 * Author:   Administrator
 * Date:     2019/5/19 9:09
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.DTO.CourseEvaDTO;
import cn.njit.info.sports.pojo.DTO.CourseRecDTO;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.CourseCountService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 课程统计控制层
 *
 * @author Liuzw
 * @since 2019/5/19
 */
@RestController
@RequestMapping("/sports/course_count")
public class CourseCountController extends AppBaseController {
  private final CourseCountService meService;

  @Autowired
  public CourseCountController(CourseCountService meService) {
    this.meService = meService;
  }

  @Override
  public RestResult create(String params) {
    return null;
  }

  @Override
  public RestResult modify(String params) {
    return null;
  }

  @Override
  public RestResult delete(Integer id) {
    return null;
  }

  @Override
  public RestResult details(Integer id) {
    return null;
  }

  @GetMapping(name = "获取用户-课程的树", value = "/tree")
  public RestResult getTreeData(HttpSession httpSession) {
    Map<String, String> userMap = (Map<String, String>) httpSession.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    List<TreeGroup> treeData = meService.getTreeData(userId);
    return RestResultUtils.returnSuccess(treeData);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "获取课程详情")
  @GetMapping(name = "获取课程详情", value = "/course_id/{courseId}")
  public RestResult getCourseByCourseId(@PathVariable("courseId") Integer courseId,
                                        HttpSession httpSession) {
    Map<String, String> userMap = (Map<String, String>) httpSession.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    CourseEvaDTO dto = meService.getByCourseId(courseId, userId);
    return RestResultUtils.returnSuccess(dto);
  }

  @UserLog(module = Module.USER_MGR, opr = "新增评价")
  @PostMapping(name = "新增评价", value = "/evaluate")
  public RestResult createEvaluate(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    return RestResultUtils.returnCreate(index);
  }

  @UserLog(module = Module.USER_MGR, opr = "更新评价")
  @PutMapping(name = "更新评价", value = "/evaluate")
  public RestResult modifyEvaluate(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.update(params);
    return RestResultUtils.returnUpdate(index);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "获取推荐的课程")
  @GetMapping(name = "获取推荐的课程", value = "/recommend")
  public RestResult getRecommendCourse(HttpSession httpSession) {
    Map<String, String> userMap = (Map<String, String>) httpSession.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    List<CourseRecDTO> recommendCourse = meService.getRecommendCourse(userId);
    return RestResultUtils.returnSuccess(recommendCourse);
  }

  @UserLog(module = Module.USER_MGR, opr = "获取教练的等级")
  @GetMapping(name = "获取教练的等级", value = "/grade")
  public RestResult getGradeByCoachId(HttpSession httpSession) {
    Map<String, String> userMap = (Map<String, String>) httpSession.getAttribute(WebConstant.USER_MAP);
    String userId = userMap.get(WebConstant.USER_ID);
    List<Integer> grades = meService.getGradeByCoachId(Integer.valueOf(userId));
    return RestResultUtils.returnSuccess(grades);
  }

  @UserLog(module = Module.DATA_ALY, opr = "获取等级比例人数")
  @GetMapping(name = "获取等级比例人数", value = "/avg_grade")
  public RestResult getAvgGrade() {
    List<Integer> avgGrade = meService.getAvgGrade();
    return RestResultUtils.returnSuccess(avgGrade);
  }
}