package cn.addenda.component.base.lambda.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class CostedSupplier<R> extends AbstractCostedFunction implements Supplier<R> {

  private static final Logger logger = LoggerFactory.getLogger(CostedSupplier.class);

  /**
   * 真正运行的任务
   */
  private final Supplier<R> supplier;

  private CostedSupplier(LocalDateTime createDateTime, Long threshold, Supplier<R> supplier) {
    super(createDateTime, threshold);
    this.supplier = supplier;
  }

  private CostedSupplier(LocalDateTime createDateTime, Long threshold, Supplier<R> supplier, Integer queueSize, Integer poolSize, Integer activeCount) {
    super(createDateTime, threshold, queueSize, poolSize, activeCount);
    this.supplier = supplier;
  }

  private CostedSupplier(LocalDateTime createDateTime, Long threshold, Supplier<R> supplier, ThreadPoolExecutor threadPoolExecutor) {
    super(createDateTime, threshold, threadPoolExecutor);
    this.supplier = supplier;
  }

  @Override
  public R get() {
    LocalDateTime startDateTime = LocalDateTime.now();
    try {
      R r = supplier.get();
      log(startDateTime, LocalDateTime.now(), supplier.getClass().getName(), supplier.toString(), null);
      return r;
    } catch (Throwable throwable) {
      log(startDateTime, LocalDateTime.now(), supplier.getClass().getName(), supplier.toString(), throwable);
      throw throwable;
    }
  }

  @Override
  public String toString() {
    return "CostedSupplier{" +
            "supplier=" + supplier +
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

  public static <R> CostedSupplier<R> of(LocalDateTime createDateTime, Long threshold, Supplier<R> supplier) {
    return new CostedSupplier<>(createDateTime, threshold, supplier);
  }

  public static <R> CostedSupplier<R> of(LocalDateTime createDateTime, Long threshold, Supplier<R> supplier, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedSupplier<>(createDateTime, threshold, supplier, queueSize, poolSize, activeCount);
  }

  public static <R> CostedSupplier<R> of(LocalDateTime createDateTime, Long threshold, Supplier<R> supplier, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedSupplier<>(createDateTime, threshold, supplier, threadPoolExecutor);
  }

  public static <R> CostedSupplier<R> of(Long threshold, Supplier<R> supplier) {
    return new CostedSupplier<>(LocalDateTime.now(), threshold, supplier);
  }

  public static <R> CostedSupplier<R> of(Long threshold, Supplier<R> supplier, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedSupplier<>(LocalDateTime.now(), threshold, supplier, queueSize, poolSize, activeCount);
  }

  public static <R> CostedSupplier<R> of(Long threshold, Supplier<R> supplier, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedSupplier<>(LocalDateTime.now(), threshold, supplier, threadPoolExecutor);
  }

  public static <R> CostedSupplier<R> of(Supplier<R> supplier) {
    return new CostedSupplier<>(LocalDateTime.now(), DEFAULT_THRESHOLD, supplier);
  }

  public static <R> CostedSupplier<R> of(Supplier<R> supplier, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedSupplier<>(LocalDateTime.now(), DEFAULT_THRESHOLD, supplier, queueSize, poolSize, activeCount);
  }

  public static <R> CostedSupplier<R> of(Supplier<R> supplier, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedSupplier<>(LocalDateTime.now(), DEFAULT_THRESHOLD, supplier, threadPoolExecutor);
  }

}
