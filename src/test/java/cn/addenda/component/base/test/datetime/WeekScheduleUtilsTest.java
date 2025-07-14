package cn.addenda.component.base.test.datetime;

import org.junit.Assert;
import org.junit.Test;

import static cn.addenda.component.base.datetime.WeekScheduleUtils.weekScheduleTransform;

public class WeekScheduleUtilsTest {

  @Test
  public void testWeekScheduleTransform() {
    String l0 = weekScheduleTransform("123", 1);
    Assert.assertEquals("234", l0);

    String l1 = weekScheduleTransform("567", 1);
    Assert.assertEquals("167", l1);

    String l2 = weekScheduleTransform("456", 2);
    Assert.assertEquals("167", l2);

    String l3 = weekScheduleTransform("123", -1);
    Assert.assertEquals("127", l3);

    String l4 = weekScheduleTransform("234", -1);
    Assert.assertEquals("123", l4);

    String l5 = weekScheduleTransform("345", -3);
    Assert.assertEquals("127", l5);

    String l6 = weekScheduleTransform("123", 0);
    Assert.assertEquals("123", l6);

    String l7 = weekScheduleTransform("234", 0);
    Assert.assertEquals("234", l7);

    String l8 = weekScheduleTransform("345", 0);
    Assert.assertEquals("345", l8);

    String l9 = weekScheduleTransform("167", 1);
    Assert.assertEquals("127", l9);

    String l10 = weekScheduleTransform("167", 3);
    Assert.assertEquals("234", l10);
  }

  @Test
  public void testEmptyInput() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      weekScheduleTransform("", 1);
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonDigitInput() {
    weekScheduleTransform("1a3", 1);
  }

  @Test
  public void testDuplicateDigits() {
    String result = weekScheduleTransform("112", 1);
    Assert.assertEquals("223", result);
  }

  @Test
  public void testLargeOffsetPositive() {
    String result1 = weekScheduleTransform("123", 7);
    Assert.assertEquals("123", result1);

    String result2 = weekScheduleTransform("123", 8);
    Assert.assertEquals("234", result2);

    String result3 = weekScheduleTransform("123", 16);
    Assert.assertEquals("345", result3);
  }

  @Test
  public void testLargeOffsetNegative() {
    String result1 = weekScheduleTransform("123", -6);
    Assert.assertEquals("234", result1);

    String result2 = weekScheduleTransform("123", -7);
    Assert.assertEquals("123", result2);

    String result3 = weekScheduleTransform("123", -16);
    Assert.assertEquals("167", result3);
  }

  @Test
  public void testSingleDigit() {
    String result = weekScheduleTransform("7", 1);
    Assert.assertEquals("1", result);
  }

  @Test
  public void testAllSameDigit() {
    String result = weekScheduleTransform("111", 2);
    Assert.assertEquals("333", result);
  }


}