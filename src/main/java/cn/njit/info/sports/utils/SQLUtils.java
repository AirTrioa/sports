/**
 * Copyright (C), 2015-2019, 南京工程学院
 * FileName: SQLUtils
 * Author:   Administrator
 * Date:     2019/3/25 23:11
 */
package cn.njit.info.sports.utils;

/**
 * 简单SQL语句拼接的工具类
 * 类似于jdbcTemplate的作用
 *
 * @author Liuzw
 * @since 2019/3/25
 */
public class SQLUtils {
  private SQLUtils() {
  }

  /**
   * 拼接findOne的代码
   *
   * @param clazz 对象的类
   * @param param 参数key
   * @param value 参数value
   * @return sql语句
   */
  public static String getSQLInFindOne(Class clazz, String param, Object value) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT * FROM ");
    String clazzName = clazz.getSimpleName();
    //表名
    String tableName = StringUtils.classNameToTableName(clazzName);
    sql.append(tableName);
    sql.append(" WHERE ");
    //参数名
    String paramName = StringUtils.humpToUnderline(param);
    sql.append(paramName);
    sql.append(" = ");
    sql.append(value);
    sql.append(";");
    return sql.toString();
  }

  /**
   * 拼接save的SQL语句
   *
   * @param clazz  1
   * @param params 2
   * @param values 3
   * @return 4
   */
  public static String getSQLInSave(Class clazz, String[] params, Object[] values) {
    StringBuilder sql = new StringBuilder();
    sql.append("INSERT INTO ");
    String clazzName = clazz.getSimpleName();
    //表名
    String tableName = StringUtils.classNameToTableName(clazzName);
    sql.append(tableName);
    sql.append("(");
    for (int i = 0; i < params.length - 1; i++) {
      sql.append(params[i]);
      sql.append(",");
    }
    sql.append(params[params.length - 1]);
    sql.append(") ");
    sql.append("values(");
    for (int i = 0; i < values.length - 1; i++) {
      sql.append(values[i]);
      sql.append(",");
    }
    sql.append(values[values.length - 1]);
    sql.append(");");
    return sql.toString();
  }

  /**
   * @param clazz
   * @param param
   * @return
   */
  public static String getSQLFindOne(Class clazz, String param) {
    return getSQLInFindOne(clazz, param, param);
  }
}