package cn.addenda.component.base.datetime;

import cn.addenda.component.base.string.Slf4jUtils;
import cn.addenda.component.base.string.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeekScheduleUtils {

  /**
   * 将周班期按天数偏移
   *
   * @param weekSchedule 原班期
   * @param offsetDay    偏移天数
   * @return 原班期偏移指定天数之后的班期
   */
  public static String weekScheduleTransform(String weekSchedule, int offsetDay) {
    assertWeekSchedule(weekSchedule);

    if (offsetDay == 0) {
      return weekSchedule;
    }

    offsetDay = offsetDay % 7;
    offsetDay = add7UntilNonNegative(offsetDay);

    List<Integer> weekSchedules = new ArrayList<>();
    int length = weekSchedule.length();
    for (int i = 0; i < length; i++) {
      weekSchedules.add(Integer.parseInt(String.valueOf(weekSchedule.charAt(i))));
    }

    List<Integer> newWeekSchedules = new ArrayList<>();

    for (Integer i : weekSchedules) {
      int temp = (i + offsetDay) % 7;
      newWeekSchedules.add(temp == 0 ? 7 : temp);
    }

    newWeekSchedules.sort(Comparator.comparing(Integer::intValue));
    return newWeekSchedules.stream().map(String::valueOf).collect(Collectors.joining(""));
  }

  private static void assertWeekSchedule(String weekSchedule) {
    if (!StringUtils.isStrictlyNumeric(weekSchedule)) {
      throw new IllegalArgumentException(Slf4jUtils.format("班期[{}]格式错误", weekSchedule));
    }
  }

  private static int add7UntilNonNegative(int offset) {
    while (offset < 0) {
      offset += 7;
    }
    return offset;
  }

}
