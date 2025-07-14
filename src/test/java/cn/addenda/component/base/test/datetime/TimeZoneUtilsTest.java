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
    int l0 = localDateOffset("+08:00", "+00:00", LocalTime.of(7, 59));
    Assert.assertEquals(-1, l0);

    int l1 = localDateOffset("+08:00", "+00:00", LocalTime.of(8, 0));
    Assert.assertEquals(0, l1);

    int l2 = localDateOffset("+08:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(0, l2);

    int l3 = localDateOffset("+00:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(0, l3);

    int l4 = localDateOffset("+00:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(1, l4);

    int l5 = localDateOffset("+00:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(1, l5);

    int l6 = localDateOffset("+08:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(0, l6);

    int l7 = localDateOffset("+08:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(0, l7);

    int l8 = localDateOffset("+08:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(0, l8);

    int l9 = localDateOffset("+00:00", "+00:00", LocalTime.of(15, 59));
    Assert.assertEquals(0, l9);

    int l10 = localDateOffset("+00:00", "+00:00", LocalTime.of(16, 0));
    Assert.assertEquals(0, l10);

    int l11 = localDateOffset("+00:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(0, l11);

    int l12 = localDateOffset("+12:00", "-12:00", LocalTime.of(23, 59));
    Assert.assertEquals(-1, l12);

    int l13 = localDateOffset("-12:00", "+12:00", LocalTime.of(23, 59));
    Assert.assertEquals(1, l13);

    int l14 = localDateOffset("+12:00", "-12:00", LocalTime.of(0, 0));
    Assert.assertEquals(-1, l14);

    int l15 = localDateOffset("-12:00", "+12:00", LocalTime.of(0, 0));
    Assert.assertEquals(1, l15);
  }


  @Test
  public void testLocalDateTransform() {
    LocalDate pre = LocalDate.of(2025, 1, 4);
    LocalDate base = LocalDate.of(2025, 1, 5);
    LocalDate next = LocalDate.of(2025, 1, 6);

    LocalDate l0 = localDateTransform("+08:00", "+00:00", LocalTime.of(7, 59), base);
    Assert.assertEquals(pre, l0);

    LocalDate l1 = localDateTransform("+08:00", "+00:00", LocalTime.of(8, 0), base);
    Assert.assertEquals(base, l1);

    LocalDate l2 = localDateTransform("+08:00", "+00:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(base, l2);

    LocalDate l3 = localDateTransform("+00:00", "+08:00", LocalTime.of(15, 59), base);
    Assert.assertEquals(base, l3);

    LocalDate l4 = localDateTransform("+00:00", "+08:00", LocalTime.of(16, 0), base);
    Assert.assertEquals(next, l4);

    LocalDate l5 = localDateTransform("+00:00", "+08:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(next, l5);

    LocalDate l6 = localDateTransform("+08:00", "+08:00", LocalTime.of(15, 59), base);
    Assert.assertEquals(base, l6);

    LocalDate l7 = localDateTransform("+08:00", "+08:00", LocalTime.of(16, 0), base);
    Assert.assertEquals(base, l7);

    LocalDate l8 = localDateTransform("+08:00", "+08:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(base, l8);

    LocalDate l9 = localDateTransform("+00:00", "+00:00", LocalTime.of(15, 59), base);
    Assert.assertEquals(base, l9);

    LocalDate l10 = localDateTransform("+00:00", "+00:00", LocalTime.of(16, 0), base);
    Assert.assertEquals(base, l10);

    LocalDate l11 = localDateTransform("+00:00", "+00:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(base, l11);

    LocalDate l12 = localDateTransform("+12:00", "-12:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(pre, l12);

    LocalDate l13 = localDateTransform("-12:00", "+12:00", LocalTime.of(23, 59), base);
    Assert.assertEquals(next, l13);

    LocalDate l14 = localDateTransform("+12:00", "-12:00", LocalTime.of(0, 0), base);
    Assert.assertEquals(pre, l14);

    LocalDate l15 = localDateTransform("-12:00", "+12:00", LocalTime.of(0, 0), base);
    Assert.assertEquals(next, l15);
  }


  @Test
  public void testLocalTimeTransform() {
    LocalTime l0 = localTimeTransform("+08:00", "+00:00", LocalTime.of(7, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l0);

    LocalTime l1 = localTimeTransform("+08:00", "+00:00", LocalTime.of(8, 0));
    Assert.assertEquals(LocalTime.of(0, 0), l1);

    LocalTime l2 = localTimeTransform("+08:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(15, 59), l2);

    LocalTime l3 = localTimeTransform("+00:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l3);

    LocalTime l4 = localTimeTransform("+00:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(LocalTime.of(0, 0), l4);

    LocalTime l5 = localTimeTransform("+00:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(7, 59), l5);

    LocalTime l6 = localTimeTransform("+08:00", "+08:00", LocalTime.of(15, 59));
    Assert.assertEquals(LocalTime.of(15, 59), l6);

    LocalTime l7 = localTimeTransform("+08:00", "+08:00", LocalTime.of(16, 0));
    Assert.assertEquals(LocalTime.of(16, 0), l7);

    LocalTime l8 = localTimeTransform("+08:00", "+08:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l8);

    LocalTime l9 = localTimeTransform("+00:00", "+00:00", LocalTime.of(15, 59));
    Assert.assertEquals(LocalTime.of(15, 59), l9);

    LocalTime l10 = localTimeTransform("+00:00", "+00:00", LocalTime.of(16, 0));
    Assert.assertEquals(LocalTime.of(16, 0), l10);

    LocalTime l11 = localTimeTransform("+00:00", "+00:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l11);

    LocalTime l12 = localTimeTransform("+12:00", "-12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l12);

    LocalTime l13 = localTimeTransform("-12:00", "+12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l13);

    LocalTime l14 = localTimeTransform("+12:00", "-12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l14);

    LocalTime l15 = localTimeTransform("-12:00", "+12:00", LocalTime.of(23, 59));
    Assert.assertEquals(LocalTime.of(23, 59), l15);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFromZone() {
    localDateOffset(null, "+00:00", LocalTime.now());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToZone() {
    localDateOffset("+08:00", "invalid-zone", LocalTime.now());
  }

  @Test
  public void testCrossMonthTransform() {
    LocalDate baseDate = LocalDate.of(2023, 12, 31);
    LocalDate result = localDateTransform("+00:00", "+12:00", LocalTime.of(23, 59), baseDate);
    Assert.assertEquals(LocalDate.of(2024, 1, 1), result);

    LocalDate result1 = localDateTransform("+12:00", "+00:00", LocalTime.of(11, 59), LocalDate.of(2024, 1, 1));
    Assert.assertEquals(baseDate, result1);
  }

  @Test
  public void testCrossYearTransform() {
    LocalDate baseDate = LocalDate.of(2023, 12, 31);
    LocalDate result = localDateTransform("+00:00", "+01:00", LocalTime.of(23, 59), baseDate);
    Assert.assertEquals(LocalDate.of(2024, 1, 1), result);

    LocalDate result1 = localDateTransform("+12:00", "+00:00", LocalTime.of(11, 59), LocalDate.of(2024, 1, 1));
    Assert.assertEquals(baseDate, result1);
  }

  @Test
  public void testMinTimeTransform() {
    LocalTime result = localTimeTransform("+00:00", "+08:00", LocalTime.MIN);
    Assert.assertEquals(LocalTime.of(8, 0), result);
  }

  @Test
  public void testMaxTimeTransform() {
    LocalTime result = localTimeTransform("+00:00", "+08:00", LocalTime.MAX);
    Assert.assertEquals(LocalTime.of(7, 59, 59, 999_999_999), result);
  }

  @Test
  public void testMidnightEdgeCase() {
    LocalDate result = localDateTransform("+08:00", "+00:00", LocalTime.of(0, 0), LocalDate.of(2025, 1, 5));
    Assert.assertEquals(LocalDate.of(2025, 1, 4), result);
  }

}