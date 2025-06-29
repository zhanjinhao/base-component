package cn.addenda.component.base.lambda.wrapper;

import cn.addenda.component.base.datetime.DateUtils;
import cn.addenda.component.base.string.Slf4jUtils;
import lombok.Getter;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public abstract class AbstractCostedFunction {

  /**
   * todo 从系统变量里面拿
   */
  public static Long DEFAULT_THRESHOLD = 200L;

  /**
   * 任务创建时间
   */
  @Getter
  protected final LocalDateTime createDateTime;

  /**
   * 超时的阈值
   */
  @Getter
  protected final Long threshold;

  // ------------------------------------
  //  下面的属性是在线程池里面运行任务的时候会有
  // ------------------------------------

  @Getter
  protected Integer queueSize;

  @Getter
  protected Integer poolSize;

  @Getter
  protected Integer activeCount;

  protected AbstractCostedFunction(LocalDateTime createDateTime, Long threshold) {
    this.createDateTime = createDateTime;
    this.threshold = threshold;
  }

  protected AbstractCostedFunction(LocalDateTime createDateTime, Long threshold, Integer queueSize, Integer poolSize, Integer activeCount) {
    this.createDateTime = createDateTime;
    this.threshold = threshold;
    this.queueSize = queueSize;
    this.poolSize = poolSize;
    this.activeCount = activeCount;
  }

  protected AbstractCostedFunction(LocalDateTime createDateTime, Long threshold, ThreadPoolExecutor threadPoolExecutor) {
    this.createDateTime = createDateTime;
    this.threshold = threshold;
    this.queueSize = threadPoolExecutor.getQueue().size();
    this.poolSize = threadPoolExecutor.getPoolSize();
    this.activeCount = threadPoolExecutor.getActiveCount();
  }

  protected void log(LocalDateTime startDateTime, LocalDateTime endDateTime, String functionType, String functionName, Throwable throwable) {
    long totalCost = DateUtils.localDateTimeToTimestamp(endDateTime) - DateUtils.localDateTimeToTimestamp(createDateTime);
    long runCost = DateUtils.localDateTimeToTimestamp(endDateTime) - DateUtils.localDateTimeToTimestamp(startDateTime);
    Supplier<String> msgSupplier;
    if (queueSize == null) {
      msgSupplier = () -> Slf4jUtils.format(
              "{}[{}]: createDateTime[{}], startDateTime[{}], endDateTime[{}], totalCost[{}ms], runCost[{}ms]. ",
              functionType, functionName, DateUtils.format(createDateTime, DateUtils.yMdHmsS_FORMATTER),
              DateUtils.format(startDateTime, DateUtils.yMdHmsS_FORMATTER),
              DateUtils.format(endDateTime, DateUtils.yMdHmsS_FORMATTER),
              totalCost, runCost);
    } else {
      msgSupplier = () -> Slf4jUtils.format(
              "{}[{}]: createDateTime[{}], startDateTime[{}], endDateTime[{}], totalCost[{}ms], runCost[{}ms]. The state of the thread pool at the moment the task is submitted: queueSize[{}], poolSize[{}], activeCount[{}].",
              functionType, functionName, DateUtils.format(createDateTime, DateUtils.yMdHmsS_FORMATTER),
              DateUtils.format(startDateTime, DateUtils.yMdHmsS_FORMATTER), DateUtils.format(endDateTime, DateUtils.yMdHmsS_FORMATTER),
              totalCost, runCost, queueSize, poolSize, activeCount);
    }

    if (throwable != null) {
      getLogger().error(msgSupplier.get(), throwable);
    } else {
      boolean flag = false;
      // totalCost > cunCost两倍 或 totalCost - runCost > [threshold]ms时，打印error
      if (totalCost > 2 * runCost || totalCost - runCost > threshold) {
        getLogger().error(msgSupplier.get());
        flag = true;
      }
      if (!flag && getLogger().isDebugEnabled()) {
        getLogger().debug(msgSupplier.get());
      }
    }
  }

  protected abstract Logger getLogger();

}
