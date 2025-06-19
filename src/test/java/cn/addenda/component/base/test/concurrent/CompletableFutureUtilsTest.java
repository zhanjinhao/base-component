package cn.addenda.component.base.test.concurrent;

import cn.addenda.component.base.concurrent.CompletableFutureUtils;
import cn.addenda.component.base.concurrent.SimpleNamedThreadFactory;
import cn.addenda.component.base.concurrent.SleepUtils;
import cn.addenda.component.base.lambda.wrapper.NamedRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author addenda
 * @since 2023/10/3 9:26
 */
@Slf4j
public class CompletableFutureUtilsTest {

  @Test
  public void test1() {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2,
            2,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            new SimpleNamedThreadFactory("CompletableFutureUtilsTest"));

    log.info("start ");
    CompletableFuture<Void> f1 = CompletableFuture.runAsync(NamedRunnable.of("f1", name -> {
      SleepUtils.sleep(TimeUnit.SECONDS, 30);
      log.info(name + "执行完毕！");
    }), threadPoolExecutor);
    CompletableFutureUtils.orTimeout(f1, 10, TimeUnit.SECONDS).handle(
            (unused, throwable) -> {
              log.error("f1" + throwable.toString());
              return null;
            });

    CompletableFuture<Void> f2 = CompletableFuture.runAsync(NamedRunnable.of("f2", name -> {
      SleepUtils.sleep(TimeUnit.SECONDS, 30);
      log.info(name + "执行完毕！");
    }), threadPoolExecutor);
    CompletableFutureUtils.orTimeout(f2, 10, TimeUnit.SECONDS).handle(
            (unused, throwable) -> {
              log.error("f2" + throwable.toString());
              return null;
            });

    CompletableFuture<Void> f3 = CompletableFuture.runAsync(NamedRunnable.of("f3", name -> {
      SleepUtils.sleep(TimeUnit.SECONDS, 30);
      log.info(name + "执行完毕！");
    }), threadPoolExecutor);
    CompletableFutureUtils.orTimeout(f3, 10, TimeUnit.SECONDS).handle(
            (unused, throwable) -> {
              log.error("f3" + throwable.toString());
              return null;
            });

    CompletableFuture<Void> f4 = CompletableFuture.runAsync(NamedRunnable.of("f4", name -> {
      SleepUtils.sleep(TimeUnit.SECONDS, 30);
      log.info(name + "执行完毕！");
    }), threadPoolExecutor);
    CompletableFutureUtils.orTimeout(f4, 10, TimeUnit.SECONDS).handle(
            (unused, throwable) -> {
              log.error("f4" + throwable.toString());
              return null;
            });

    CompletableFuture<Void> f5 = CompletableFuture.runAsync(NamedRunnable.of("f5", name -> {
      SleepUtils.sleep(TimeUnit.SECONDS, 30);
      log.info(name + "执行完毕！");
    }), threadPoolExecutor);
    CompletableFutureUtils.orTimeout(f5, 10, TimeUnit.SECONDS).handle(
            (unused, throwable) -> {
              log.error("f5" + throwable.toString());
              return null;
            });

    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          SleepUtils.sleep(TimeUnit.SECONDS, 5);
          log.info(threadPoolExecutor.toString());
        }
      }
    }).start();

    // 结论：超时后，正在执行中的任务会执行完成，排队的任务不会被线程执行
    SleepUtils.sleep(TimeUnit.SECONDS, 300);

  }

}
