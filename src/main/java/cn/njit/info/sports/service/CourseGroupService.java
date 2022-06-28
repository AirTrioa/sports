package cn.njit.info.sports.service;

import cn.njit.info.sports.pojo.DTO.CourseRecDTO;
import cn.njit.info.sports.pojo.entity.Course;
import cn.njit.info.sports.pojo.entity.CourseGroup;
import cn.njit.info.sports.pojo.model.TreeGroup;

import java.util.List;

/**
 * 课程分组服务
 *
 * @author Liuzw
 * @since 2019/5/6
 */
public interface CourseGroupService extends AppBaseService<CourseGroup> {
  /**
   * 获取树结构的值
   *
   * @return List
   */
  List<TreeGroup> getTreeData();

  /**
   * 根据Key获取该Key下所有的叶子节点
   *
   * @param key 父关键字
   * @return treeGroups
   */
  List<TreeGroup> getChildList(String key);

  /**
   * 根据分组ids来获取课程Ids
   *
   * @param groupIds 分组ids
   * @return 课程Ids
   */
  List<Integer> getCourseIdsByGroupIds(List<Integer> groupIds);

  /**
   * 从分组中移除掉课程
   *
   * @param courseId 课程Id
   */
  void removeCourse(Integer courseId);

  /**
   * 创建分组和课程之间的关系
   *
   * @param groupId          分组Id
   * @param courseIdsJsonStr 课程列表Json
   * @return 创建条数
   */
  int createRelations(Integer groupId, String courseIdsJsonStr);

  /**
   * 根据课程Ids来获取同类型的课程
   *
   * @param courseIds 课程Ids
   * @return 课程list
   */
  List<CourseRecDTO> getCourseRecDTOByCourseIds(List<Integer> courseIds);
}
