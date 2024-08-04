package cn.addenda.component.test.jdk.util;

import cn.addenda.component.jdk.util.DateUtils;
import org.junit.Test;

/**
 * @author addenda
 * @since 2023/3/3 9:39
 */
public class DateUtilsTest {

  @Test
  public void main() {

    System.out.println(DateUtils.parseLdt("2023-03-07 11:11:11", DateUtils.yMdHms_FORMATTER));
    System.out.println(DateUtils.parseLdt("2023-03-07 11:11:11.111", DateUtils.yMdHmsS_FORMATTER));
    System.out.println(DateUtils.parseLt("11:11:11", DateUtils.Hms_FORMATTER));
    System.out.println(DateUtils.parseLd("2023-03-07", DateUtils.yMd_FORMATTER));

  }

}
