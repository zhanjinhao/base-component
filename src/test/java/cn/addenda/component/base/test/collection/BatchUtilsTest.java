package cn.addenda.component.base.test.collection;

import cn.addenda.component.base.collection.ArrayUtils;
import cn.addenda.component.base.collection.BatchUtils;
import cn.addenda.component.base.collection.IterableUtils;
import cn.addenda.component.base.string.Slf4jUtils;
import cn.addenda.component.stacktrace.StackTraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Slf4j
public class BatchUtilsTest {

  @Test
  public void testList1() {
    BatchUtils.acceptListInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> Assert.assertEquals("[1, 2, 3, 4, 5]", Slf4jUtils.format("{}", objects)));
  }

  @Test
  public void testList2() {
    List<String> list = BatchUtils.applyListInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> a(objects));
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  @Test
  public void testList3() {
    BatchUtils.acceptListInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> Assert.assertEquals("[1, 2, 3, 4, 5]", Slf4jUtils.format("{}", objects)),
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void testList4() {
    List<String> list = BatchUtils.applyListInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> a(objects),
            StackTraceUtils.getDetailedCallerInfo());
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  private List<String> testList5Result = ArrayUtils.asArrayList("[1]", "[2]", "[3]", "[4]", "[5]");

  @Test
  public void testList5() {
    BatchUtils.acceptListInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> Assert.assertEquals(testList5Result.get(objects.get(0) - 1), objects.toString()),
            1,
            StackTraceUtils.getCallerInfo());
  }

  @Test
  public void testList6() {
    List<String> list = BatchUtils.applyListInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> a(objects),
            1,
            StackTraceUtils.getDetailedCallerInfo());
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  @Test
  public void test1() {
    BatchUtils.acceptInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> Assert.assertEquals("[1, 2, 3, 4, 5]", Slf4jUtils.format("{}", objects)));
  }

  @Test
  public void test2() {
    Iterable<String> list = BatchUtils.applyInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> a(IterableUtils.castToList(objects)));
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  @Test
  public void test3() {
    BatchUtils.acceptInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> Assert.assertEquals("[1, 2, 3, 4, 5]", Slf4jUtils.format("{}", objects)),
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void test4() {
    Iterable<String> list = BatchUtils.applyInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> a(IterableUtils.castToList(objects)),
            StackTraceUtils.getDetailedCallerInfo());
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  private List<String> test5Result = ArrayUtils.asArrayList("[1]", "[2]", "[3]", "[4]", "[5]");

  @Test
  public void test5() {
    BatchUtils.acceptInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> Assert.assertEquals(test5Result.get(IterableUtils.castToList(objects).get(0) - 1), objects.toString()),
            1,
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void test6() {
    Iterable<String> list = BatchUtils.applyInBatches(Arrays.asList(1, 2, 3, 4, 5),
            objects -> a(IterableUtils.castToList(objects)), 1, StackTraceUtils.getCallerInfo());
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  public static List<String> a(List<Integer> b) {
    if (b == null || b.isEmpty()) {
      return new ArrayList<>();
    }
    return b.stream().map(item -> "第" + item + "个").collect(Collectors.toList());
  }

  @Test
  public void testBiList1() {
    BatchUtils.acceptListInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            new BiConsumer<List<String>, List<Integer>>() {
              @Override
              public void accept(List<String> objects1, List<Integer> objects2) {
                Assert.assertEquals("[a, b][1, 2]", objects1.toString() + objects2.toString());
              }
            });
  }

  @Test
  public void testBiList2() {
    List<String> strings = BatchUtils.applyListInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            (b1, b2) -> b(b1, b2));

    Assert.assertEquals("[a1, a2, b1, b2]", strings.toString());
  }

  @Test
  public void testBiList3() {
    BatchUtils.acceptListInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            new BiConsumer<List<String>, List<Integer>>() {
              @Override
              public void accept(List<String> objects1, List<Integer> objects2) {
                Assert.assertEquals("[a, b][1, 2]", objects1.toString() + objects2.toString());
              }
            },
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void testBiList4() {
    List<String> strings = BatchUtils.applyListInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            (b1, b2) -> b(b1, b2),
            StackTraceUtils.getDetailedCallerInfo());

    Assert.assertEquals("[a1, a2, b1, b2]", strings.toString());
  }

  private List<String> testBiList5Result = ArrayUtils.asArrayList("[a][1]", "[a][2]", "[b][1]", "[b][2]");

  @Test
  public void testBiList5() {
    AtomicInteger counter = new AtomicInteger(0);
    BatchUtils.acceptListInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            new BiConsumer<List<String>, List<Integer>>() {
              @Override
              public void accept(List<String> objects1, List<Integer> objects2) {
                Assert.assertEquals(testBiList5Result.get(counter.getAndIncrement()), objects1.toString() + objects2.toString());
              }
            },
            1,
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void testBiList6() {
    List<String> strings = BatchUtils.applyListInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            (b1, b2) -> b(b1, b2),
            1,
            StackTraceUtils.getDetailedCallerInfo());

    Assert.assertEquals("[a1, a2, b1, b2]", strings.toString());
  }

  @Test
  public void testBi1() {
    BatchUtils.acceptInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            new BiConsumer<Iterable<String>, Iterable<Integer>>() {
              @Override
              public void accept(Iterable<String> objects1, Iterable<Integer> objects2) {
                Assert.assertEquals("[a, b][1, 2]", objects1.toString() + objects2.toString());
              }
            });
  }

  @Test
  public void testBi2() {
    Iterable<String> strings = BatchUtils.applyInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            (b1, b2) -> b(IterableUtils.castToList(b1), IterableUtils.castToList(b2)));

    Assert.assertEquals("[a1, a2, b1, b2]", strings.toString());
  }

  @Test
  public void testBi3() {
    BatchUtils.acceptInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            new BiConsumer<Iterable<String>, Iterable<Integer>>() {
              @Override
              public void accept(Iterable<String> objects1, Iterable<Integer> objects2) {
                Assert.assertEquals("[a, b][1, 2]", objects1.toString() + objects2.toString());
              }
            },
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void testBi4() {
    Iterable<String> strings = BatchUtils.applyInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            (b1, b2) -> b(IterableUtils.castToList(b1), IterableUtils.castToList(b2)),
            StackTraceUtils.getDetailedCallerInfo());

    Assert.assertEquals("[a1, a2, b1, b2]", strings.toString());
  }

  private List<String> testBi5Result = ArrayUtils.asArrayList("[a][1]", "[a][2]", "[b][1]", "[b][2]");

  @Test
  public void testBi5() {
    AtomicInteger counter = new AtomicInteger(0);
    BatchUtils.acceptInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            new BiConsumer<Iterable<String>, Iterable<Integer>>() {
              @Override
              public void accept(Iterable<String> objects1, Iterable<Integer> objects2) {
                Assert.assertEquals(testBi5Result.get(counter.getAndIncrement()), objects1.toString() + objects2.toString());
              }
            },
            1,
            StackTraceUtils.getDetailedCallerInfo());
  }

  @Test
  public void testBi6() {
    Iterable<String> strings = BatchUtils.applyInBatches(
            ArrayUtils.asArrayList("a", "b"),
            ArrayUtils.asArrayList(1, 2),
            (b1, b2) -> b(IterableUtils.castToList(b1), IterableUtils.castToList(b2)),
            1,
            StackTraceUtils.getDetailedCallerInfo());

    Assert.assertEquals("[a1, a2, b1, b2]", strings.toString());
  }

  public static List<String> b(List<String> b1, List<Integer> b2) {
    if (b1 == null || b1.isEmpty() || b2 == null || b2.isEmpty()) {
      return new ArrayList<>();
    }
    List<String> result = new ArrayList<>();
    for (String b1Item : b1) {
      for (Integer b2Item : b2) {
        result.add(b1Item + b2Item);
      }
    }
    return result;
  }

}
