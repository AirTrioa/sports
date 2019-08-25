/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: CourseGroupDao
 * Author:   Administrator
 * Date:     2019/5/6 19:21
 */
package cn.njit.info.sports.dao;

import cn.njit.info.sports.pojo.entity.CourseGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * CourseGroup数据访问层
 *
 * @author Liuzw
 * @since 2019/5/6
 */
@Mapper
@Repository
public interface CourseGroupDao extends BaseMapper<CourseGroup> {
}