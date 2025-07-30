package cn.addenda.component.base.test.datetime;

import cn.addenda.component.base.datetime.WeekScheduleUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static cn.addenda.component.base.datetime.WeekScheduleUtils.convertWeekSchedule;

public class WeekScheduleUtilsTest {

  @Test
  public void testWeekScheduleConversion() {
    String l0 = convertWeekSchedule("123", 1);
    Assert.assertEquals("234", l0);

    String l1 = convertWeekSchedule("567", 1);
    Assert.assertEquals("167", l1);

    String l2 = convertWeekSchedule("456", 2);
    Assert.assertEquals("167", l2);

    String l3 = convertWeekSchedule("123", -1);
    Assert.assertEquals("127", l3);

    String l4 = convertWeekSchedule("234", -1);
    Assert.assertEquals("123", l4);

    String l5 = convertWeekSchedule("345", -3);
    Assert.assertEquals("127", l5);

    String l6 = convertWeekSchedule("123", 0);
    Assert.assertEquals("123", l6);

    String l7 = convertWeekSchedule("234", 0);
    Assert.assertEquals("234", l7);

    String l8 = convertWeekSchedule("345", 0);
    Assert.assertEquals("345", l8);

    String l9 = convertWeekSchedule("167", 1);
    Assert.assertEquals("127", l9);

    String l10 = convertWeekSchedule("167", 3);
    Assert.assertEquals("234", l10);
  }

  @Test
  public void testEmptyInput() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      convertWeekSchedule("", 1);
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonDigitInput() {
    convertWeekSchedule("1a3", 1);
  }

  @Test
  public void testDuplicateDigits() {
    String result = convertWeekSchedule("112", 1);
    Assert.assertEquals("223", result);
  }

  @Test
  public void testLargeOffsetPositive() {
    String result1 = convertWeekSchedule("123", 7);
    Assert.assertEquals("123", result1);

    String result2 = convertWeekSchedule("123", 8);
    Assert.assertEquals("234", result2);

    String result3 = convertWeekSchedule("123", 16);
    Assert.assertEquals("345", result3);
  }

  @Test
  public void testLargeOffsetNegative() {
    String result1 = convertWeekSchedule("123", -6);
    Assert.assertEquals("234", result1);

    String result2 = convertWeekSchedule("123", -7);
    Assert.assertEquals("123", result2);

    String result3 = convertWeekSchedule("123", -16);
    Assert.assertEquals("167", result3);
  }

  @Test
  public void testSingleDigit() {
    String result = convertWeekSchedule("7", 1);
    Assert.assertEquals("1", result);
  }

  @Test
  public void testAllSameDigit() {
    String result = convertWeekSchedule("111", 2);
    Assert.assertEquals("333", result);
  }

  @Test
  public void testGetWeekSchedule_Monday() {
    // 测试星期一，LocalDateTime中ordinal为0
    LocalDateTime monday = LocalDateTime.of(2025, 7, 21, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(monday);
    Assert.assertEquals("1", result);
  }

  @Test
  public void testGetWeekSchedule_Tuesday() {
    // 测试星期二，LocalDateTime中ordinal为1
    LocalDateTime tuesday = LocalDateTime.of(2025, 7, 22, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(tuesday);
    Assert.assertEquals("2", result);
  }

  @Test
  public void testGetWeekSchedule_Wednesday() {
    // 测试星期三，LocalDateTime中ordinal为2
    LocalDateTime wednesday = LocalDateTime.of(2025, 7, 23, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(wednesday);
    Assert.assertEquals("3", result);
  }

  @Test
  public void testGetWeekSchedule_Thursday() {
    // 测试星期四，LocalDateTime中ordinal为3
    LocalDateTime thursday = LocalDateTime.of(2025, 7, 24, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(thursday);
    Assert.assertEquals("4", result);
  }

  @Test
  public void testGetWeekSchedule_Friday() {
    // 测试星期五，LocalDateTime中ordinal为4
    LocalDateTime friday = LocalDateTime.of(2025, 7, 25, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(friday);
    Assert.assertEquals("5", result);
  }

  @Test
  public void testGetWeekSchedule_Saturday() {
    // 测试星期六，LocalDateTime中ordinal为5
    LocalDateTime saturday = LocalDateTime.of(2025, 7, 26, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(saturday);
    Assert.assertEquals("6", result);
  }

  @Test
  public void testGetWeekSchedule_Sunday() {
    // 测试星期日，LocalDateTime中ordinal为6
    LocalDateTime sunday = LocalDateTime.of(2025, 7, 27, 12, 0);
    String result = WeekScheduleUtils.getWeekSchedule(sunday);
    Assert.assertEquals("7", result);
  }

  @Test
  public void testGetWeekSchedule_DifferentTimeSameDate() {
    // 测试同一天的不同时间应该返回相同结果
    LocalDateTime morning = LocalDateTime.of(2023, 10, 2, 9, 30); // 星期一早上
    LocalDateTime evening = LocalDateTime.of(2023, 10, 2, 21, 45); // 星期一晚上
    String result1 = WeekScheduleUtils.getWeekSchedule(morning);
    String result2 = WeekScheduleUtils.getWeekSchedule(evening);
    Assert.assertEquals(result1, result2);
  }

}