package cn.addenda.component.base.datetime;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.string.Slf4jUtils;
import cn.addenda.component.base.string.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeZoneUtils {

  public static final String TIME_ZONE_PEK = "+08:00";
  public static final String TIME_ZONE_UTC = "+00:00";
  public static final int HALF_HOUR_MINUTES = 30;

  private static final Set<String> timeZoneSet = ArrayUtils.asHashSet(
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
          "+12:00", "+12:30",
          "+13:00", "+13:30",
          "+14:00"
  );

  /**
   * @param timeZone 时区
   * @param hours    小时
   */
  public static String offsetTimeZone(String timeZone, int hours) {
    assertTimeZone(timeZone);

    // 解析时区偏移量
    ZoneOffset offset = ZoneOffset.of(timeZone);

    // 获取总秒数并加上小时数对应的秒数
    int totalSeconds = offset.getTotalSeconds() + hours * 3600;

    try {
      // 将总秒数转换回 ZoneOffset
      ZoneOffset newOffset = ZoneOffset.ofTotalSeconds(totalSeconds);

      // 格式化为 ±HH:MM 格式
      return newOffset.getId();
    } catch (DateTimeException dateTimeException) {
      throw new IllegalArgumentException(
              Slf4jUtils.format("illegal timeZone offset argument. timeZone: {}, hours: {}.", timeZone, hours),
              dateTimeException);
    }
  }

  /**
   * @param timeZone 时区
   * @param hours    小时
   * @param minutes  分钟
   */
  public static String offsetTimeZone(String timeZone, int hours, int minutes) {
    assertTimeZone(timeZone);

    // 校验minutes必须是30的倍数
    if (minutes % 30 != 0) {
      throw new IllegalArgumentException(
              Slf4jUtils.format("illegal timeZone offset argument. timeZone: {}, hours: {}, minutes: {}.",
                      timeZone, hours, minutes));
    }


    // 解析时区偏移量
    ZoneOffset offset = ZoneOffset.of(timeZone);

    // 获取总秒数并加上小时数对应的秒数
    int totalSeconds = offset.getTotalSeconds() + hours * 3600 + minutes * 60;

    try {
      // 将总秒数转换回 ZoneOffset
      ZoneOffset newOffset = ZoneOffset.ofTotalSeconds(totalSeconds);

      // 格式化为 ±HH:MM 格式
      return newOffset.getId();
    } catch (DateTimeException dateTimeException) {
      throw new IllegalArgumentException(
              Slf4jUtils.format("illegal timeZone offset argument. timeZone: {}, hours: {}, minutes: {}.", timeZone, hours, minutes),
              dateTimeException);
    }
  }

  /**
   * 计算时间从原时区转到目标时区后日期的偏离值
   *
   * @param timeZoneSource 原时区
   * @param timeZoneTarget 目标时区
   * @param localTime      计算日期时的基准时间
   * @return localTime从源时区转到目标时区的日期差
   */
  public static int timeZoneOffset(String timeZoneSource, String timeZoneTarget, LocalTime localTime) {
    assertTimeZone(timeZoneSource);
    assertTimeZone(timeZoneTarget);

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
  public static LocalDate convertLocalDate(String timeZoneSource, String timeZoneTarget, LocalTime localTime, LocalDate localDate) {
    return localDate.plusDays(timeZoneOffset(timeZoneSource, timeZoneTarget, localTime));
  }


  /**
   * 将时间从原时区转到目标时区
   *
   * @param timeZoneSource 原时区
   * @param timeZoneTarget 目标时区
   * @param localTime      时间
   * @return localTime从原时区转到目标时区后的时间
   */
  public static LocalTime convertLocalTime(String timeZoneSource, String timeZoneTarget, LocalTime localTime) {
    assertTimeZone(timeZoneSource);
    assertTimeZone(timeZoneTarget);

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
   * 将日期时间从原时区转到目标时区
   *
   * @param timeZoneSource 原时区
   * @param timeZoneTarget 目标时区
   * @param localDateTime  日期时间
   * @return localDateTime从原时区转到目标时区后的时间
   */
  public static LocalDateTime convertLocalDateTime(String timeZoneSource, String timeZoneTarget, LocalDateTime localDateTime) {
    assertTimeZone(timeZoneSource);
    assertTimeZone(timeZoneTarget);

    // 解析源时区偏移量
    ZoneOffset sourceOffset = ZoneOffset.of(timeZoneSource);
    // 解析目标时区偏移量
    ZoneOffset targetOffset = ZoneOffset.of(timeZoneTarget);

    // 创建源时区的 ZonedDateTime 对象
    ZonedDateTime sourceDateTime = ZonedDateTime.of(localDateTime, sourceOffset);

    // 将 ZonedDateTime 对象转换为目标时区
    ZonedDateTime targetDateTime = sourceDateTime.withZoneSameInstant(targetOffset);

    // 获取目标时区的 LocalDateTime
    return targetDateTime.toLocalDateTime();
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

  private static void assertTimeZone(String timeZone) {
    if (!timeZoneSet.contains(timeZone)) {
      throw new IllegalArgumentException(Slf4jUtils.format("时区[{}]格式错误！", timeZone));
    }
  }

}
