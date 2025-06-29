package cn.addenda.component.base.test.util;

import cn.addenda.component.base.util.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author addenda
 * @since 2023/3/9 19:45
 */
@Slf4j
public class SleepUtilsTest {

  @Test
  public void test1() {
    AtomicLong atomicLong = new AtomicLong(0);
    Thread thread = new Thread(() -> {
      log.info("start. ");
      long start = System.currentTimeMillis();
      SleepUtils.sleep(TimeUnit.SECONDS, 10, false);
      log.info("end. ");
      long end = System.currentTimeMillis();
      if (Thread.currentThread().isInterrupted()) {
        atomicLong.set(end - start);
        log.info("睡眠期间被打断了！");
      }
    });
    thread.start();

    while (thread.isAlive()) {
      SleepUtils.sleep(TimeUnit.SECONDS, 3, false);
      thread.interrupt();
    }

    System.out.println(atomicLong.get());
    Assert.assertTrue(atomicLong.get() >= 10000);
  }

  @Test
  public void test2() {
    AtomicLong atomicLong = new AtomicLong(0);
    Thread thread = new Thread(() -> {
      log.info("start. ");
      long start = System.currentTimeMillis();
      SleepUtils.sleep(Duration.ofSeconds(10), false);
      log.info("end. ");
      long end = System.currentTimeMillis();
      if (Thread.currentThread().isInterrupted()) {
        atomicLong.set(end - start);
        log.info("睡眠期间被打断了！");
      }
    });
    thread.start();

    while (thread.isAlive()) {
      SleepUtils.sleep(Duration.ofSeconds(3), false);
      thread.interrupt();
    }

    System.out.println(atomicLong.get());
    Assert.assertTrue(atomicLong.get() >= 10000);
  }

}
