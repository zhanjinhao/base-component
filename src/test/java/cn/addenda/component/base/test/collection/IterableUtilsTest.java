package cn.addenda.component.base.test.collection;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.collection.IterableUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author addenda
 * @since 2022/11/17 19:18
 */
@Slf4j
public class IterableUtilsTest {

  @Test
  public void test0() {
    List<String> list = Arrays.asList("1", "2", "1");
    Assert.assertEquals(ArrayUtils.asArrayList("1", "2"), IterableUtils.deDuplicate(list, Comparator.comparing(Integer::valueOf)));
    Assert.assertEquals(ArrayUtils.asArrayList("1", "2"), IterableUtils.deDuplicate(list, String::toString));
    Assert.assertEquals(ArrayUtils.asArrayList("1", "2"), IterableUtils.deDuplicate(list, Comparator.comparing(String::toString), ArrayList::new));
  }

  @Test
  public void test1() {
    Assert.assertNull(IterableUtils.oneOrNull("a", (Function<String, List<String>>) s -> null));
    Assert.assertEquals("1", IterableUtils.oneOrNull("a", s -> ArrayUtils.asArrayList("1")));

    Assert.assertThrows("When invoking [IterableUtilsTest#test6], multi result of param[a] are returned.", IllegalStateException.class, () -> {
      System.out.println(IterableUtils.oneOrNull("a", (Function<String, List<String>>) s -> ArrayUtils.asArrayList("1", "2")));
    });
  }

  @Test
  public void test2() {
    Assert.assertNull(IterableUtils.oneOrNull("a", "b", (BiFunction<String, String, List<String>>) (s, s2) -> null));
    Assert.assertEquals("1", IterableUtils.oneOrNull("a", "b", (s, s2) -> ArrayUtils.asArrayList("1")));

    Assert.assertThrows("When invoking [IterableUtilsTest#test7], multi result of param[a,b] are returned.", IllegalStateException.class, () -> {
      System.out.println(IterableUtils.oneOrNull("a", "b", (BiFunction<String, String, List<String>>) (o, o2) -> ArrayUtils.asArrayList("1", "2")));
    });
  }

}
