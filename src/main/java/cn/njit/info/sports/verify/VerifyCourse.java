/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: VerifyCourse
 * Author:   Administrator
 * Date:     2019/5/6 13:00
 */
package cn.njit.info.sports.verify;

import cn.njit.info.sports.dao.CourseDao;
import cn.njit.info.sports.pojo.entity.Course;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程验证
 *
 * @author Liuzw
 * @since 2019/5/6
 */
@Service
public class VerifyCourse {
  private final CourseDao meDao;
  @Autowired
  public VerifyCourse(CourseDao meDao) {
    this.meDao = meDao;
  }

  /**
   * 验证名字是否重复
   *
   * @param course 课程
   * @return true/false
   */
  public boolean validName(Course course) {
    QueryWrapper<Course> qw = new QueryWrapper<>();
    qw.eq("NAME", course.getName());
    List<Course> courses = meDao.selectList(qw);
    if (CollectionUtils.isNotEmpty(courses) && null != course.getId()) {
      return courses.size() <= 1;
    }
    return CollectionUtils.isEmpty(courses);
  }
}