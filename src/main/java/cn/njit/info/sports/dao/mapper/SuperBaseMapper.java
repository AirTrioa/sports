package cn.njit.info.sports.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * MyBatis基本数据操作接口
 * (操蛋的mybatis)
 * @author liuzw
 * @since 2019.3.25
 */
@Mapper
@Repository
public interface SuperBaseMapper {
  /**
   * 执行自定义sql语句-超级查询
   * @param sql sql语句
   * @return 返回值
   */
  List<LinkedHashMap<String, Object>> superSelect(String sql);

}
