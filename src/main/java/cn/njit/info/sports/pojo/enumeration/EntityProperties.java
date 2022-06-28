package cn.njit.info.sports.pojo.enumeration;

/**
 * 实体接口属性值
 *
 * @author liuzw
 * @since 2019.3.26
 */
public final class EntityProperties {

  public final class Common {
    private Common() {
    }

    public static final String CREDENTIAL = "password";
  }

  public final class ResourceModel {
    private ResourceModel() {
    }

    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String ENTER_PRISE_ID = "enterPriseId";
  }

  public final class EnterPrise {
    private EnterPrise() {
    }

    public static final String ID = ResourceModel.ID;
    public static final String CODE = ResourceModel.CODE;
    public static final String NAME = ResourceModel.NAME;
  }

  public final class User {
    private User() {
    }

    public static final String USER_ID = "userId";
    public static final String PHONE = "phone";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String AGE = "age";
    public static final String TABLE_NAME = " USER ";
  }

  public final class Course {
    private Course() {
    }

    public static final String NAME = "name";
    public static final String LESSON = "lesson";
    public static final String TABLE_NAME = " COURSE ";

    public static final String AM = "am";
    public static final String PM = "pm";
    public static final String NIGHT = "night";
  }

  public final class CourseGroup {
    private CourseGroup() {
    }

    public static final String TABLE_NAME = " COURSE_GROUP";
  }

  public final class Coach {
    private Coach() {
    }

    public static final String TABLE_NAME = " COACH";
    public static final String MAN = "man";
    public static final String WOMAN = "woman";
  }

  public final class CoachCourseRelation {
    private CoachCourseRelation() {
    }

    public static final String TABLE_NAME = " COACH_COURSE_RELATION ";
  }

  public final class Room {
    private Room() {
    }

    public static final String TABLE_NAME = " ROOM ";
  }


  public final class Device {
    private Device() {
    }

    public static final String TABLE_NAME = " DEVICE ";
    public static final String TABLE_ROOM_ID = "ROOM_ID";
    public static final String TABLE_TYPE ="TYPE";
  }

  public final class CourseInfo{
    private CourseInfo() {
    }
    public static final String TABLE_TYPE =" COURSE_INFO ";
  }
}
