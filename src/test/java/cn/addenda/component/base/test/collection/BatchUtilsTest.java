package cn.addenda.component.base.test.collection;

import cn.addenda.component.base.collection.BatchUtils;
import cn.addenda.component.base.collection.IterableUtils;
import cn.addenda.component.stacktrace.StackTraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BatchUtilsTest {

  @Test
  public void test0() {
    List<String> list = BatchUtils.applyInBatches(Arrays.asList(1L, 2L, 3L, 4L, 5L),
            objects -> a(IterableUtils.toArrayList(objects)), 2, StackTraceUtils.getCallerInfo());
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  @Test
  public void test01() {
    List<String> list = BatchUtils.applyInBatches(Arrays.asList(1L, 2L, 3L, 4L, 5L),
            objects -> a(IterableUtils.castToList(objects)), 2, StackTraceUtils.getCallerInfo());
    Assert.assertEquals("[第1个, 第2个, 第3个, 第4个, 第5个]", list.toString());
  }

  public static List<String> a(List<Long> b) {
    if (b == null || b.isEmpty()) {
      return new ArrayList<>();
    }
    return b.stream().map(item -> "第" + item + "个").collect(Collectors.toList());
  }

}
