package cn.njit.info.sports.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 测试
 */
@TableName("USER")
public class User {
  @TableId("ID")
  private Integer id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 用户Id
   */
  private String userId;
  /**
   * 密码
   */
  private String password;
  /**
   * 手机号码
   */
  private String phone;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 用户类型
   */
  private String type;
  /**
   * 头像URL
   */
  private String url;

  public User() {
  }

  public User(Coach coach) {
    this.username = coach.getName();
    this.age = coach.getAge();
    this.type = "coach";
    this.url = "1.jpg";
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}