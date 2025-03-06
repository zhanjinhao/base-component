package cn.addenda.component.base.test.collection;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.collection.CompareUtils;
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
