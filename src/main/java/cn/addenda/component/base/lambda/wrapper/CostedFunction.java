package cn.addenda.component.base.lambda.wrapper;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

public class CostedFunction<T, R> extends AbstractCostedFunction implements Function<T, R> {

  private static final Logger logger = LoggerFactory.getLogger(CostedFunction.class);

  /**
   * 真正运行的任务
   */
  @Getter
  private final Function<T, R> function;

  public CostedFunction(LocalDateTime createDateTime, Long threshold, Function<T, R> function) {
    super(createDateTime, threshold);
    this.function = function;
  }

  public CostedFunction(LocalDateTime createDateTime, Long threshold, Function<T, R> function, Integer queueSize, Integer poolSize, Integer activeCount) {
    super(createDateTime, threshold, queueSize, poolSize, activeCount);
    this.function = function;
  }

  public CostedFunction(LocalDateTime createDateTime, Long threshold, Function<T, R> function, ThreadPoolExecutor threadPoolExecutor) {
    super(createDateTime, threshold, threadPoolExecutor);
    this.function = function;
  }

  @Override
  public R apply(T t) {
    LocalDateTime startDateTime = LocalDateTime.now();
    try {
      R apply = function.apply(t);
      log(startDateTime, LocalDateTime.now(), function.getClass().getName(), function.toString(), null);
      return apply;
    } catch (Throwable throwable) {
      log(startDateTime, LocalDateTime.now(), function.getClass().getName(), function.toString(), throwable);
      throw throwable;
    }
  }

  @Override
  public String toString() {
    return "CostedFunction{" +
            "function=" + function +
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

  public static <T, R> CostedFunction<T, R> of(LocalDateTime createDateTime, Long threshold, Function<T, R> function) {
    return new CostedFunction<>(createDateTime, threshold, function);
  }

  public static <T, R> CostedFunction<T, R> of(LocalDateTime createDateTime, Long threshold, Function<T, R> function, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedFunction<>(createDateTime, threshold, function, queueSize, poolSize, activeCount);
  }

  public static <T, R> CostedFunction<T, R> of(LocalDateTime createDateTime, Long threshold, Function<T, R> function, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedFunction<>(createDateTime, threshold, function, threadPoolExecutor);
  }

  public static <T, R> CostedFunction<T, R> of(Long threshold, Function<T, R> function) {
    return new CostedFunction<>(LocalDateTime.now(), threshold, function);
  }

  public static <T, R> CostedFunction<T, R> of(Long threshold, Function<T, R> function, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedFunction<>(LocalDateTime.now(), threshold, function, queueSize, poolSize, activeCount);
  }

  public static <T, R> CostedFunction<T, R> of(Long threshold, Function<T, R> function, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedFunction<>(LocalDateTime.now(), threshold, function, threadPoolExecutor);
  }

  public static <T, R> CostedFunction<T, R> of(Function<T, R> function) {
    return new CostedFunction<>(LocalDateTime.now(), DEFAULT_THRESHOLD, function);
  }

  public static <T, R> CostedFunction<T, R> of(Function<T, R> function, Integer queueSize, Integer poolSize, Integer activeCount) {
    return new CostedFunction<>(LocalDateTime.now(), DEFAULT_THRESHOLD, function, queueSize, poolSize, activeCount);
  }

  public static <T, R> CostedFunction<T, R> of(Function<T, R> function, ThreadPoolExecutor threadPoolExecutor) {
    return new CostedFunction<>(LocalDateTime.now(), DEFAULT_THRESHOLD, function, threadPoolExecutor);
  }

}
