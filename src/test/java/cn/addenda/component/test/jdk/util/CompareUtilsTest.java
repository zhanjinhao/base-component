package cn.addenda.component.test.jdk.util;

import cn.addenda.component.jdk.util.collection.CompareUtils;
import cn.addenda.component.jdk.util.collection.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author addenda
 * @since 2024/1/9 10:39
 */
public class CompareUtilsTest {

  List<String> sequence = ArrayUtils.asArrayList("1", "2", null, "3");

  @Test
  public void test1() {
    sequence.sort(CompareUtils::nullMaxCompare);
    Assert.assertEquals(ArrayUtils.asArrayList("1", "2", "3", null), sequence);
    sequence.sort(CompareUtils::nullMinCompare);
    Assert.assertEquals(ArrayUtils.asArrayList(null, "1", "2", "3"), sequence);
  }

}
