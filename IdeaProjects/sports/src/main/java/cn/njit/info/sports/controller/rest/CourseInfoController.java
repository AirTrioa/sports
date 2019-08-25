/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: ChooseCourseController
 * Author:   Administrator
 * Date:     2019/5/18 13:21
 */
package cn.njit.info.sports.controller.rest;

import cn.njit.info.sports.aspect.UserLog;
import cn.njit.info.sports.pojo.entity.CourseInfo;
import cn.njit.info.sports.pojo.model.RestResult;
import cn.njit.info.sports.service.CourseInfoService;
import cn.njit.info.sports.utils.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 选课控制器
 *
 * @author Liuzw
 * @since 2019/5/18
 */
@RestController
@RequestMapping("/sports/course_info")
public class CourseInfoController extends AppBaseController {
  private final CourseInfoService meService;

  @Autowired
  public CourseInfoController(CourseInfoService meService) {
    this.meService = meService;
  }

  @PostMapping(name = "创建选课", value = "")
  public RestResult create(String params) {
    int index = meService.create(params);
    if (index == 0)
      return RestResultUtils.returnError("您已经选择过这门课程");
    if (index == -1)
      return RestResultUtils.returnError("该门课程尚未有教练执教，已通知管理员处理");
    return RestResultUtils.returnMessage("选课成功");
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


  @PostMapping(name = "多选课程", value = "/userId/{userId}")
  public RestResult createLists(@PathVariable("userId") String userId,
                                @RequestParam("courseIds") String courseIds) {
    int index = meService.createLists(userId, courseIds);
    return RestResultUtils.returnCreate(index);
  }
}