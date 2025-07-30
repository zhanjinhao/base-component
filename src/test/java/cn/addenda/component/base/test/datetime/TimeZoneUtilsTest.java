package cn.addenda.component.base.test.datetime;

import cn.addenda.component.base.collection.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.List;

import static cn.addenda.component.base.datetime.TimeZoneUtils.*;

public class TimeZoneUtilsTest {

  @Test
  public void test1() {
    // 定义24个时区名称
    List<String> timeZones = ArrayUtils.asArrayList(
            "Africa/Cairo", "America/New_York", "America/Los_Angeles", "America/Chicago",
            "America/Denver", "America/Phoenix", "America/Anchorage", "America/Adak",
            "Asia/Shanghai", "Asia/Tokyo", "Asia/Kolkata", "Asia/Dubai",
            "Asia/Singapore", "Asia/Bangkok", "Asia/Hong_Kong", "Asia/Seoul",
            "Australia/Sydney", "Australia/Melbourne", "Australia/Brisbane", "Europe/London",
            "Europe/Berlin", "Europe/Paris", "Europe/Rome", "Europe/Moscow"
    );

    // 获取并打印每个时区的偏移值
    for (String timeZoneName : timeZones) {
      try {
        ZoneId zoneId = ZoneId.of(timeZoneName);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        ZoneOffset zoneOffset = zonedDateTime.getOffset();
        System.out.println("时区: " + timeZoneName + ", 偏移量: " + zoneOffset);
      } catch (Exception e) {
        System.out.println("时区: " + timeZoneName + ", 获取偏移量失败: " + e.getMessage());
      }
    }
  }


  @Test
  public void testLocalDateOffset() {
    int l0 = timeZoneOffset("+08:00", "+00:00", LocalTime.of(7, 59));
    Assert.assertEquals(-1, l0);

    int l1 = timeZoneOffset("+08:00", "+00:00", LocalTime.of(8, 0));
    Assert.assertEquals(0, l1);

    int l2 = timeZoneOffset("+08:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(0, l2);

    int l3 = timeZoneOffset("+00:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(0, l3);

    int l4 = timeZoneOffset("+00:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(1, l4);

    int l5 = timeZoneOffset("+00:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(1, l5);

    int l6 = timeZoneOffset("+08:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(0, l6);

    int l7 = timeZoneOffset("+08:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(0, l7);

    int l8 = timeZoneOffset("+08:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(0, l8);

    int l9 = timeZoneOffset("+00:00", "+00:00", LocalTime.of(15, 59));
    Assert.assertEquals(0, l9);

    int l10 = timeZoneOffset("+00:00", "+00:00", LocalTime.of(16, 0));
    Assert.assertEquals(0, l10);

    int l11 = timeZoneOffset("+00:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(0, l11);

    int l12 = timeZoneOffset("+12:00", "-12:00", LocalTime.of(23, 59));
    Assert.assertEquals(-1, l12);

    int l13 = timeZoneOffset("-12:00", "+12:00", LocalTime.of(23, 59));
    Assert.assertEquals(1, l13);

    int l14 = timeZoneOffset("+12:00", "-12:00", LocalTime.of(0, 0));
    Assert.assertEquals(-1, l14);

    int l15 = timeZoneOffset("-12:00", "+12:00", LocalTime.of(0, 0));
    Assert.assertEquals(1, l15);
  }


  @Test
  public void testLocalDateConversion() {
    LocalDate pre = LocalDate.of(2025, 1, 4);
    LocalDate base = LocalDate.of(2025, 1, 5);
    LocalDate next = LocalDate.of(2025, 1, 6);

    LocalDate l0 = convertLocalDate("+08:00", "+00:00", LocalTime.of(7, 59), base);
    Assert.assertEquals(pre, l0);

    LocalDate l1 = convertLocalDate("+08:00", "+00:00", LocalTime.of(8, 0), base);
    Assert.assertEquals(base, l1);

    LocalDate l2 = convertLocalDate("+08:00", "+00:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(base, l2);

    LocalDate l3 = convertLocalDate("+00:00", "+08:00", LocalTime.of(15, 59), base);
    Assert.assertEquals(base, l3);

    LocalDate l4 = convertLocalDate("+00:00", "+08:00", LocalTime.of(16, 0), base);
    Assert.assertEquals(next, l4);

    LocalDate l5 = convertLocalDate("+00:00", "+08:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(next, l5);

    LocalDate l6 = convertLocalDate("+08:00", "+08:00", LocalTime.of(15, 59), base);
    Assert.assertEquals(base, l6);

    LocalDate l7 = convertLocalDate("+08:00", "+08:00", LocalTime.of(16, 0), base);
    Assert.assertEquals(base, l7);

    LocalDate l8 = convertLocalDate("+08:00", "+08:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(base, l8);

    LocalDate l9 = convertLocalDate("+00:00", "+00:00", LocalTime.of(15, 59), base);
    Assert.assertEquals(base, l9);

    LocalDate l10 = convertLocalDate("+00:00", "+00:00", LocalTime.of(16, 0), base);
    Assert.assertEquals(base, l10);

    LocalDate l11 = convertLocalDate("+00:00", "+00:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(base, l11);

    LocalDate l12 = convertLocalDate("+12:00", "-12:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(pre, l12);

    LocalDate l13 = convertLocalDate("-12:00", "+12:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(next, l13);

    LocalDate l14 = convertLocalDate("+12:00", "-12:00", LocalTime.of(0, 0), base);
    Assert.assertEquals(pre, l14);

    LocalDate l15 = convertLocalDate("-12:00", "+12:00", LocalTime.of(0, 0), base);
    Assert.assertEquals(next, l15);
  }


  @Test
  public void testLocalTimeConversion() {
    LocalTime l0 = convertLocalTime("+08:00", "+00:00", LocalTime.of(7, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l0);

    LocalTime l1 = convertLocalTime("+08:00", "+00:00", LocalTime.of(8, 0));
    Assert.assertEquals(LocalTime.of(0, 0), l1);

    LocalTime l2 = convertLocalTime("+08:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(15, 59), l2);

    LocalTime l3 = convertLocalTime("+00:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l3);

    LocalTime l4 = convertLocalTime("+00:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(LocalTime.of(0, 0), l4);

    LocalTime l5 = convertLocalTime("+00:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(7, 59), l5);

    LocalTime l6 = convertLocalTime("+08:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(LocalTime.of(15, 59), l6);

    LocalTime l7 = convertLocalTime("+08:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(LocalTime.of(16, 0), l7);

    LocalTime l8 = convertLocalTime("+08:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l8);

    LocalTime l9 = convertLocalTime("+00:00", "+00:00", LocalTime.of(15, 59));
    Assert.assertEquals(LocalTime.of(15, 59), l9);

    LocalTime l10 = convertLocalTime("+00:00", "+00:00", LocalTime.of(16, 0));
    Assert.assertEquals(LocalTime.of(16, 0), l10);

    LocalTime l11 = convertLocalTime("+00:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l11);

    LocalTime l12 = convertLocalTime("+12:00", "-12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l12);

    LocalTime l13 = convertLocalTime("-12:00", "+12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l13);

    LocalTime l14 = convertLocalTime("+12:00", "-12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l14);

    LocalTime l15 = convertLocalTime("-12:00", "+12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l15);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFromZone() {
    timeZoneOffset(null, "+00:00", LocalTime.now());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToZone() {
    timeZoneOffset("+08:00", "invalid-zone", LocalTime.now());
  }

  @Test
  public void testCrossMonthConversion() {
    LocalDate baseDate = LocalDate.of(2023, 12, 31);
    LocalDate result = convertLocalDate("+00:00", "+12:00", LocalTime.of(23, 59), baseDate);
    Assert.assertEquals(LocalDate.of(2024, 1, 1), result);

    LocalDate result1 = convertLocalDate("+12:00", "+00:00", LocalTime.of(11, 59), LocalDate.of(2024, 1, 1));
    Assert.assertEquals(baseDate, result1);
  }

  @Test
  public void testCrossYearConversion() {
    LocalDate baseDate = LocalDate.of(2023, 12, 31);
    LocalDate result = convertLocalDate("+00:00", "+01:00", LocalTime.of(23, 59), baseDate);
    Assert.assertEquals(LocalDate.of(2024, 1, 1), result);

    LocalDate result1 = convertLocalDate("+12:00", "+00:00", LocalTime.of(11, 59), LocalDate.of(2024, 1, 1));
    Assert.assertEquals(baseDate, result1);
  }

  @Test
  public void testMinTimeConversion() {
    LocalTime result = convertLocalTime("+00:00", "+08:00", LocalTime.MIN);
    Assert.assertEquals(LocalTime.of(8, 0), result);
  }

  @Test
  public void testMaxTimeConversion() {
    LocalTime result = convertLocalTime("+00:00", "+08:00", LocalTime.MAX);
    Assert.assertEquals(LocalTime.of(7, 59, 59, 999_999_999), result);
  }

  @Test
  public void testMidnightEdgeCase() {
    LocalDate result = convertLocalDate("+08:00", "+00:00", LocalTime.of(0, 0), LocalDate.of(2025, 1, 5));
    Assert.assertEquals(LocalDate.of(2025, 1, 4), result);
  }

  @Test
  public void testLocalDateTimeConversion_NormalCase_Back() {
    // 测试正常的时区转换
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    LocalDateTime result = convertLocalDateTime("+08:00", "+00:00", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(2023, 6, 15, 6, 30, 0);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testLocalDateTimeConversion_NormalCase_Forward() {
    // 测试正常的时区转换
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 6, 30, 0);
    LocalDateTime result = convertLocalDateTime("+00:00", "+08:00", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testLocalDateTimeConversion_SameTimeZone() {
    // 测试相同时区的转换
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    LocalDateTime result = convertLocalDateTime("+08:00", "+08:00", inputDateTime);
    Assert.assertEquals(inputDateTime, result);
  }

  @Test
  public void testLocalDateTimeConversion_DateCrossing_Back() {
    // 测试跨日期的时区转换
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 10, 30, 0);
    LocalDateTime result = convertLocalDateTime("+08:00", "-08:00", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(2023, 6, 14, 18, 30, 0);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testLocalDateTimeConversion_DateCrossing_Forward() {
    // 测试向未来日期的时区转换
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 23, 30, 0);
    LocalDateTime result = convertLocalDateTime("-08:00", "+08:00", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(2023, 6, 16, 15, 30, 0);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testLocalDateTimeConversion_HalfHourTimeZone() {
    // 测试半小时间 zone 转换
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    LocalDateTime result = convertLocalDateTime("+08:00", "+05:30", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(2023, 6, 15, 12, 0, 0);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testLocalDateTimeConversion_InvalidSourceTimeZone() {
    // 测试无效的源时区
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      convertLocalDateTime("+25:00", "+08:00", inputDateTime);
    });
  }

  @Test
  public void testLocalDateTimeConversion_InvalidTargetTimeZone() {
    // 测试无效的目标时区
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      convertLocalDateTime("+08:00", "UTC+8", inputDateTime);
    });
  }

  @Test
  public void testLocalDateTimeConversion_BothInvalidTimeZones() {
    // 测试源和目标时区都无效
    LocalDateTime inputDateTime = LocalDateTime.of(2023, 6, 15, 14, 30, 0);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      convertLocalDateTime("GMT+8", "UTC+8", inputDateTime);
    });
  }

  @Test
  public void testLocalDateTimeConversion_MinDateTime() {
    // 测试最小日期时间
    LocalDateTime inputDateTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
    LocalDateTime result = convertLocalDateTime("+00:00", "+08:00", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(1, 1, 1, 8, 0, 0);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testLocalDateTimeConversion_MaxDateTime() {
    // 测试最大日期时间
    LocalDateTime inputDateTime = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
    LocalDateTime result = convertLocalDateTime("+08:00", "+00:00", inputDateTime);
    LocalDateTime expected = LocalDateTime.of(9999, 12, 31, 15, 59, 59);
    Assert.assertEquals(expected, result);
  }


  @Test
  public void testOffsetTimeZone_hours() {
    // 正常情况：给+08:00时区增加2小时
    Assert.assertEquals("+10:00", offsetTimeZone("+08:00", 2));

    // 正常情况：给-05:00时区增加3小时
    Assert.assertEquals("-02:00", offsetTimeZone("-05:00", 3));

    // 跨越0点：给+02:30时区减少4小时
    Assert.assertEquals("-01:30", offsetTimeZone("+02:30", -4));

    // 跨越0点：给-11:00时区增加15小时（跨过±12时区）
    Assert.assertEquals("+04:00", offsetTimeZone("-11:00", 15));

    // 减少小时数：给+03:00时区减少5小时
    Assert.assertEquals("-02:00", offsetTimeZone("+03:00", -5));

    // 增加半小时时区
    Assert.assertEquals("+08:00", offsetTimeZone("+08:00", 0));

    // 测试边界值：+14:00减少24小时
    Assert.assertEquals("-10:00", offsetTimeZone("+14:00", -24));

    // 测试边界值：+12:00减少24小时
    Assert.assertEquals("-12:00", offsetTimeZone("+12:00", -24));

    // 测试边界值：-12:00增加24小时
    Assert.assertEquals("+12:00", offsetTimeZone("-12:00", 24));

    // 给+05:30增加2小时
    Assert.assertEquals("+07:30", offsetTimeZone("+05:30", 2));

    // 给-09:30减少3小时
    Assert.assertEquals("-12:30", offsetTimeZone("-09:30", -3));

    // 给+00:30增加1小时
    Assert.assertEquals("+01:30", offsetTimeZone("+00:30", 1));

    // 测试边界值：-12:00增加36小时
    Assert.assertThrows(
            IllegalArgumentException.class,
            () -> {
              offsetTimeZone("-12:00", 36);
            });
  }

  @Test
  public void testOffsetTimeZone_hours_minutes() {
    Assert.assertEquals("+10:30", offsetTimeZone("+08:00", 2, 30));

    Assert.assertEquals("-01:30", offsetTimeZone("-05:00", 3, 30));

    Assert.assertEquals("-01:00", offsetTimeZone("+02:30", -4, 30));

    Assert.assertEquals("+04:30", offsetTimeZone("-11:00", 15, 30));

    Assert.assertEquals("-01:30", offsetTimeZone("+03:00", -5, 30));

    Assert.assertEquals("+08:30", offsetTimeZone("+08:00", 0, 30));

    Assert.assertEquals("-09:30", offsetTimeZone("+14:00", -24, 30));

    Assert.assertEquals("-11:30", offsetTimeZone("+12:00", -24, 30));

    Assert.assertEquals("+12:30", offsetTimeZone("-12:00", 24, 30));

    Assert.assertEquals("+07:00", offsetTimeZone("+05:30", 2, -30));

    Assert.assertEquals("-13:00", offsetTimeZone("-09:30", -3, -30));

    Assert.assertEquals("+01:00", offsetTimeZone("+00:30", 1, -30));

//    Assert.assertEquals("+00:50", offsetTimeZone("+00:30", 1, -40));

//    Assert.assertEquals("+02:10", offsetTimeZone("+00:30", 1, 40));

    // 测试边界值：-12:00增加36小时
    Assert.assertThrows(
            IllegalArgumentException.class,
            () -> {
              offsetTimeZone("-12:00", 36, 30);
            });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOffsetTimeZoneWithInvalidTimeZone() {
    // 测试无效时区格式
    offsetTimeZone("+25:00", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOffsetTimeZoneWithInvalidTimeZoneFormat() {
    // 测试无效时区格式
    offsetTimeZone("0800", 2);
  }

}