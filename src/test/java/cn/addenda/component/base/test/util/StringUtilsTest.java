package cn.addenda.component.base.test.util;

import cn.addenda.component.base.string.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void test() {
    String example1 = ":example:";
    String example2 = "example:";
    String example3 = ":example";
    String example4 = "example";
    String example5 = "::example::";

    System.out.println(example1 + " -> " + StringUtils.biTrimSpecifiedChar(example1, ':'));
    System.out.println(example2 + " -> " + StringUtils.biTrimSpecifiedChar(example2, ':'));
    System.out.println(example3 + " -> " + StringUtils.biTrimSpecifiedChar(example3, ':'));
    System.out.println(example4 + " -> " + StringUtils.biTrimSpecifiedChar(example4, ':'));
    System.out.println(example5 + " -> " + StringUtils.biTrimSpecifiedChar(example5, ':'));

    Assert.assertEquals("example", StringUtils.biTrimSpecifiedChar(example1, ':'));
    Assert.assertEquals("example", StringUtils.biTrimSpecifiedChar(example2, ':'));
    Assert.assertEquals("example", StringUtils.biTrimSpecifiedChar(example3, ':'));
    Assert.assertEquals("example", StringUtils.biTrimSpecifiedChar(example4, ':'));
    Assert.assertEquals("example", StringUtils.biTrimSpecifiedChar(example5, ':'));
  }

}
