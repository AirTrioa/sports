/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseController
 * Author:   Administrator
 * Date:     2019/5/5 20:42
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.DTO.CourseDTO;
import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.CourseService;
import cn.njit.info.sports.utils.ExcelUtils;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 课程控制层
 *
 * @author Liuzw
 * @since 2019/5/5
 */
@RestController
@RequestMapping("/sports/course")
public class CourseController extends AppBaseController {
  private final CourseService meService;

  @Autowired
  public CourseController(CourseService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.COURSE_MGR, opr = "课程分页查询")
  @GetMapping(name = "分页查询", value = "/page")
  public RestResult page(@RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<Course> courses = meService.page(params);
    return RestResultUtils.returnSuccess(courses);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "新增课程")
  @PostMapping(name = "新增", value = "")
  public RestResult create(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    if (index < 0)
      return RestResultUtils.returnError("新增失败");
    return RestResultUtils.returnMessage("新增成功");
  }

  @UserLog(module = Module.COURSE_MGR, opr = "删除课程")
  @DeleteMapping(name = "删除", value = "/{id}")
  public RestResult delete(@PathVariable("id") Integer id) {
    meService.delete(id);
    return RestResultUtils.returnMessage("删除成功");
  }

  @UserLog(module = Module.COURSE_MGR, opr = "更新课程信息")
  @PutMapping(name = "更新", value = "")
  public RestResult modify(@RequestParam(WebConstant.PARAMS) String params) {
    int update = meService.update(params);
    if (update < 0)
      return RestResultUtils.returnError("更新失败");
    return RestResultUtils.returnMessage("更新成功");
  }

  @UserLog(module = Module.COURSE_MGR, opr = "查看课程详情")
  @GetMapping(name = "详情", value = "/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    Course details = meService.details(id);
    return RestResultUtils.returnSuccess(details);
  }

  //@UserLog(module = Module.COURSE_MGR, opr = "课程Excel导入")
  @PostMapping(name = "导入Excel", value = "/excel")
  public RestResult readExcel(@RequestParam(WebConstant.FILE) MultipartFile file) {
    boolean checkFile = ExcelUtils.checkFile(file);
    if (!checkFile)
      return RestResultUtils.returnError("请上传符合条件的文件!");
    meService.readExcel(file);
    return RestResultUtils.returnSuccess("导入成功");
  }

  @UserLog(module = Module.COURSE_MGR, opr = "根据分组Id获取全部的课程")
  @GetMapping(name = "根据分组Id获取全部的课程", value = "/group_id/{groupId}")
  public RestResult getCoursesByGroupId(@PathVariable("groupId") Integer groupId,
                                        @RequestParam(value = WebConstant.PARAMS, required = false) String params) {
    List<Course> all = meService.getAllByGroupId(groupId);
    return RestResultUtils.returnSuccess(all);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "获取所有未挂载关系的课程")
  @GetMapping(name = "获取所有未挂载关系的课程", value = "/all")
  public RestResult getCoursesWithoutRelations() {
    List<Course> courses = meService.getCoursesWithoutRelations();
    return RestResultUtils.returnSuccess(courses);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "根据教练名字查询课程表")
  @GetMapping(name = "根据教练名字查询课程表", value = "/coach_course/{coachName}")
  public RestResult getCoachCourseDataByCoachName(@PathVariable("coachName") String coachName) {
    List<CourseDTO> courseDTOS = meService.buildCoachCourseDataByCoachName(coachName);
    return RestResultUtils.returnSuccess(courseDTOS);
  }

  @UserLog(module = Module.COURSE_MGR,opr = "根据名字查询课程")
  @GetMapping("/course_name/{name}")
  public RestResult getCourseByName(@PathVariable("name") String name) {
    Course course = meService.getCourseByName(name);
    return RestResultUtils.returnSuccess(course);
  }

  @UserLog(module = Module.COURSE_MGR,opr = "根据教练id查询课程")
  @GetMapping("/coachId")
  public RestResult getCourseByCoachId(HttpSession httpSession) {
    Map<String, String> userMap = (Map<String, String>) httpSession.getAttribute(WebConstant.USER_MAP);
    //教练的userId就是自己的Id
    String userId = userMap.get(WebConstant.USER_ID);
    Course course = meService.getCourseByCoachId(Integer.valueOf(userId));
    return RestResultUtils.returnSuccess(course);
  }

}