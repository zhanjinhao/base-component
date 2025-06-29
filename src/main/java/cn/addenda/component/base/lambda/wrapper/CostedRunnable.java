package cn.addenda.component.base.lambda.wrapper;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

public class CostedRunnable extends AbstractCostedFunction implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(CostedRunnable.class);

  /**
   * 真正运行的任务
   */
  @Getter
  private final Runnable runnable;

  private CostedRunnable(LocalDateTime createDateTime, Long threshold, Runnable runnable) {
    super(createDateTime, threshold);
    this.runnable = runnable;
  }

  private CostedRunnable(LocalDateTime createDateTime, Long threshold, Runnable runnable, Integer queueSize, Integer poolSize, Integer activeCount) {
    super(createDateTime, threshold, queueSize, poolSize, activeCount);
    this.runnable = runnable;
  }

  private CostedRunnable(LocalDateTime createDateTime, Long threshold, Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
    super(createDateTime, threshold, threadPoolExecutor);
    this.runnable = runnable;
  }

  @Override
  public void run() {
    LocalDateTime startDateTime = LocalDateTime.now();
    try {
      runnable.run();
      log(startDateTime, LocalDateTime.now(), runnable.getClass().getName(), runnable.toString(), null);
    } catch (Throwable throwable) {
      log(startDateTime, LocalDateTime.now(), runnable.getClass().getName(), runnable.toString(), throwable);
      throw throwable;
    }
  }

  @Override
  public String toString() {
    return "CostedRunnable{" +
            "runnable=" + runnable +
            ", createDateTime=" + createDateTime +
            ", threshold=" + threshold +
            ", queueSize=" + queueSize +
            ", poolSize=" + poolSize +
            ", activeCount=" + activeCount +
            '}';
  }

  @Override
  protected Logger getLogger() {
    return logger;
  }

  public static CostedRunnable of(LocalDateTime createDateTime, Long threshold, Runnable runnable) {
    return new CostedRunnable(createDateTime, threshold, runnable);
  }

  public static CostedRunnable of(LocalDateTime createDateTime, Long threshold, Runnable runnable, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedRunnable(createDateTime, threshold, runnable, queueSize, poolSize, activeCount);
  }

  public static CostedRunnable of(LocalDateTime createDateTime, Long threshold, Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedRunnable(createDateTime, threshold, runnable, threadPoolExecutor);
  }

  public static CostedRunnable of(Long threshold, Runnable runnable) {
    return new CostedRunnable(LocalDateTime.now(), threshold, runnable);
  }

  public static CostedRunnable of(Long threshold, Runnable runnable, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedRunnable(LocalDateTime.now(), threshold, runnable, queueSize, poolSize, activeCount);
  }

  public static CostedRunnable of(Long threshold, Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedRunnable(LocalDateTime.now(), threshold, runnable, threadPoolExecutor);
  }

  public static CostedRunnable of(Runnable runnable) {
    return new CostedRunnable(LocalDateTime.now(), DEFAULT_THRESHOLD, runnable);
  }

  public static CostedRunnable of(Runnable runnable, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedRunnable(LocalDateTime.now(), DEFAULT_THRESHOLD, runnable, queueSize, poolSize, activeCount);
  }

  public static CostedRunnable of(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedRunnable(LocalDateTime.now(), DEFAULT_THRESHOLD, runnable, threadPoolExecutor);
  }

}
