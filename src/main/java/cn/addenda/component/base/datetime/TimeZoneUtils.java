package cn.addenda.component.base.datetime;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.string.Slf4jUtils;
import cn.addenda.component.base.string.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeZoneUtils {

  public static final String TIMEZONE_PEK = "+08:00";
  public static final String TIMEZONE_UTC = "+00:00";

  private static final Set<String> timeZoneOffsetList = ArrayUtils.asHashSet(
          "-12:00", "-11:30",
          "-11:00", "-10:30",
          "-10:00", "-09:30",
          "-09:00", "-08:30",
          "-08:00", "-07:30",
          "-07:00", "-06:30",
          "-06:00", "-05:30",
          "-05:00", "-04:30",
          "-04:00", "-03:30",
          "-03:00", "-02:30",
          "-02:00", "-01:30",
          "-01:00", "-00:30",

          "+00:00", "+00:30",
          "+01:00", "+01:30",
          "+02:00", "+02:30",
          "+03:00", "+03:30",
          "+04:00", "+04:30",
          "+05:00", "+05:30",
          "+06:00", "+06:30",
          "+07:00", "+07:30",
          "+08:00", "+08:30",
          "+09:00", "+09:30",
          "+10:00", "+10:30",
          "+11:00", "+11:30",
          "+12:00"
  );


  /**
   * 计算时间从原时区转到目标时区后日期的偏离值
   *
   * @param timeZoneSource 原时区
   * @param timeZoneTarget 目标时区
   * @param localTime      计算日期时的基准时间
   * @return localTime从源时区转到目标时区的日期差
   */
  public static int localDateOffset(String timeZoneSource, String timeZoneTarget, LocalTime localTime) {
    assertTimeZoneOffset(timeZoneSource);
    assertTimeZoneOffset(timeZoneTarget);

    // 解析源时区偏移量
    ZoneOffset sourceOffset = ZoneOffset.of(timeZoneSource);
    // 解析目标时区偏移量
    ZoneOffset targetOffset = ZoneOffset.of(timeZoneTarget);

    // 假设当前日期为今天
    LocalDate currentDate = LocalDate.now();

    // 创建源时区的 ZonedDateTime 对象
    ZonedDateTime sourceDateTime = ZonedDateTime.of(currentDate, localTime, sourceOffset);

    // 将 ZonedDateTime 对象转换为目标时区
    ZonedDateTime targetDateTime = sourceDateTime.withZoneSameInstant(targetOffset);

    // 计算sourceDateTime和targetDateTime的日期差
    return (int) ChronoUnit.DAYS.between(sourceDateTime.toLocalDate(), targetDateTime.toLocalDate());
  }


  /**
   * 将日期从原时区转到目标时区
   *
   * @param timeZoneSource 原时区
   * @param timeZoneTarget 目标时区
   * @param localTime      计算日期时的基准时间
   * @param localDate      待转换的日期
   * @return localDate从原时区转到目标时区后的日期
   */
  public static LocalDate localDateTransform(String timeZoneSource, String timeZoneTarget, LocalTime localTime, LocalDate localDate) {
    return localDate.plusDays(localDateOffset(timeZoneSource, timeZoneTarget, localTime));
  }


  /**
   * 将时间从原时区转到目标时区
   *
   * @param timeZoneSource 原时区
   * @param timeZoneTarget 目标时区
   * @param localTime      时间
   * @return localTime从原时区转到目标时区后的时间
   */
  public static LocalTime localTimeTransform(String timeZoneSource, String timeZoneTarget, LocalTime localTime) {
    assertTimeZoneOffset(timeZoneSource);
    assertTimeZoneOffset(timeZoneTarget);

    // 解析源时区偏移量
    ZoneOffset sourceOffset = ZoneOffset.of(timeZoneSource);
    // 解析目标时区偏移量
    ZoneOffset targetOffset = ZoneOffset.of(timeZoneTarget);

    // 创建源时区的 ZonedDateTime 对象
    ZonedDateTime sourceDateTime = ZonedDateTime.of(LocalDate.now(), localTime, sourceOffset);

    // 将 ZonedDateTime 对象转换为目标时区
    ZonedDateTime targetDateTime = sourceDateTime.withZoneSameInstant(targetOffset);

    // 获取目标时区的时间
    return targetDateTime.toLocalTime();
  }


  /**
   * 校验是否是HHmm格式的时间
   *
   * @param HHmm HHmm格式的时间
   * @return 是否是HHmm格式的时间
   */
  public static boolean checkHHmmValid(String HHmm) {
    String[] split = HHmm.split(":");
    if (split.length != 2) {
      return false;
    }

    String hh = split[0];
    String mm = split[1];
    if (!StringUtils.isStrictlyNumeric(hh) || !StringUtils.isStrictlyNumeric(mm)) {
      return false;
    }
    if (Integer.parseInt(hh) > 23 || Integer.parseInt(mm) > 59) {
      return false;
    }

    return true;
  }

  /**
   * 校验是否是HHmm格式的时间，不是则抛出异常
   *
   * @param HHmm HHmm格式的时间
   */
  public static void assertHHmmValid(String HHmm) {
    if (!checkHHmmValid(HHmm)) {
      throw new IllegalArgumentException(Slf4jUtils.format("HH:mm[{}]格式错误", HHmm));
    }
  }

  private static void assertTimeZoneOffset(String timeZoneOffset) {
    if (!timeZoneOffsetList.contains(timeZoneOffset)) {
      throw new IllegalArgumentException(Slf4jUtils.format("时区偏移量[{}]格式错误！", timeZoneOffset));
    }
  }

}
