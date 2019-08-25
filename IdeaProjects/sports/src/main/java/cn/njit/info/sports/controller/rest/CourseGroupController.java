/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupController
 * Author:   Administrator
 * Date:     2019/5/6 19:24
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.controller.rest.AppBaseController;
import cn.njit.info.sports.pojo.entity.CourseGroup;
import cn.njit.info.sports.pojo.enumeration.Module;
import cn.njit.info.sports.pojo.enumeration.WebConstant;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.pojo.model.TreeGroup;
import cn.njit.info.sports.service.CourseGroupService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CourseGroup控制层
 *
 * @author Liuzw
 * @since 2019/5/6
 */
@RestController
@RequestMapping("/sports/course_group")
public class CourseGroupController extends AppBaseController {
  private final CourseGroupService meService;

  @Autowired
  public CourseGroupController(CourseGroupService meService) {
    this.meService = meService;
  }

  @UserLog(module = Module.COURSE_MGR, opr = "创建课程分组")
  @PostMapping("")
  public RestResult create(@RequestParam(WebConstant.PARAMS) String params) {
    int index = meService.create(params);
    return RestResultUtils.returnCreate(index);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "删除课程分组")
  @DeleteMapping("/{id}")
  public RestResult delete(@PathVariable(WebConstant.ID) Integer id) {
    meService.delete(id);
    return RestResultUtils.returnMessage("删除");
  }

  @UserLog(module = Module.COURSE_MGR, opr = "查询课程分组")
  @GetMapping("/{id}")
  public RestResult details(@PathVariable(WebConstant.ID) Integer id) {
    CourseGroup details = meService.details(id);
    return RestResultUtils.returnSuccess(details);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "修改课程分组")
  @PutMapping("")
  public RestResult modify(@RequestParam(WebConstant.PARAMS) String params) {
    int update = meService.update(params);
    return RestResultUtils.returnUpdate(update);
  }

  @UserLog(module = Module.COURSE_MGR, opr = "获取课程分组树的值")
  @GetMapping(name = "获取树的值", value = "/page")
  public RestResult getTreeData() {
    List<TreeGroup> treeData = meService.getTreeData();
    return RestResultUtils.returnSuccess(treeData);
  }

  @UserLog(module = Module.COURSE_MGR,opr = "从分组删除掉课程")
  @DeleteMapping(name = "从分组删除掉课程", value = "/course_id/{courseId}")
  public RestResult removeCourse(@PathVariable("courseId") Integer courseId) {
    meService.removeCourse(courseId);
    return RestResultUtils.returnMessage("移除成功");
  }

  @UserLog(module = Module.COURSE_MGR,opr = "创建课程分组关系")
  @PostMapping(name = "创建分组关系", value = "/relations/{groupId}")
  public RestResult createRelations(@PathVariable("groupId") Integer groupId,
                                    @RequestParam("courseIds") String courseIds) {
    int index = meService.createRelations(groupId, courseIds);
    return RestResultUtils.returnCreate(index);
  }

  
}