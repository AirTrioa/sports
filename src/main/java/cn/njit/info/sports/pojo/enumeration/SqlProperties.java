package cn.njit.info.sports.pojo.enumeration;

/**
 * SQL语句的辅助
 *
 * @author Liuzw
 * @since 2019/3/29
 */
public final class SqlProperties {
  private SqlProperties() {
  }

  public static final String SELECT = "SELECT * FROM ";
  public static final String INSERT = "INSERT INTO ";
  public static final String UPDATE = "UPDATE ";
  public static final String DELETE = "DELETE FROM ";
  public static final String WHERE = " WHERE ";
  public static final String AND = "AND ";
  public static final String ID = " ID = #{id} ";
  public static final String USER_ID = " USER_ID=#{userId} ";
  public static final String VALUES = "values";
  public static final String SET = "set ";
  public static final String MAX_ID = "SELECT MAX(ID) FROM ";
}
