package cn.addenda.component.base.util;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.component.base.lambda.TFunction;
import cn.addenda.component.base.lambda.TRunnable;
import cn.addenda.component.base.lambda.TSupplier;
import cn.addenda.component.stacktrace.StackTraceUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 只有传入的行为是幂等行为才能使用此工具。
 *
 * @author addenda
 * @since 2023/10/5 23:29
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetryUtils {

  private static final String LOG_MSG = "Caller: [{}]. Attachment: [{}]. Retry Interval: [{}].";

  public static <R> R retryWhenException(
          TSupplier<R> supplier, Object attachment) throws Throwable {
    try {
      return supplier.get();
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(), format(attachment), "PT0S", throwable);
      return supplier.get();
    }
  }

  public static void retryWhenException(
          TRunnable runnable, Object attachment) throws Throwable {
    try {
      runnable.run();
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(), format(attachment), "PT0S", throwable);
      runnable.run();
    }
  }

  public static <T, R> R retryWhenException(
          TFunction<T, R> function, T t, Object attachment) throws Throwable {
    try {
      return function.apply(t);
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(), format(attachment), "PT0S", throwable);
      return function.apply(t);
    }
  }

  public static <R> R retryWhenException(
          TSupplier<R> supplier, Object attachment, Duration interval) throws Throwable {
    try {
      return supplier.get();
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(), format(attachment), interval, throwable);
      SleepUtils.sleep(interval);
      return supplier.get();
    }
  }

  public static void retryWhenException(
          TRunnable runnable, Object attachment, Duration interval) throws Throwable {
    try {
      runnable.run();
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(), format(attachment), interval, throwable);
      SleepUtils.sleep(interval);
      runnable.run();
    }
  }

  public static <T, R> R retryWhenException(
          TFunction<T, R> function, T t, Object attachment, Duration interval) throws Throwable {
    try {
      return function.apply(t);
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(), format(attachment), interval, throwable);
      SleepUtils.sleep(interval);
      return function.apply(t);
    }
  }

  public static <R> R retryWhenException(
          TSupplier<R> supplier, Object attachment, TimeUnit timeUnit, long interval) throws Throwable {
    try {
      return supplier.get();
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(),
              format(attachment), Duration.ofMillis(timeUnit.toMillis(interval)), throwable);
      SleepUtils.sleep(timeUnit, interval);
      return supplier.get();
    }
  }

  public static void retryWhenException(
          TRunnable runnable, Object attachment, TimeUnit timeUnit, long interval) throws Throwable {
    try {
      runnable.run();
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(),
              format(attachment), Duration.ofMillis(timeUnit.toMillis(interval)), throwable);
      SleepUtils.sleep(timeUnit, interval);
      runnable.run();
    }
  }

  public static <T, R> R retryWhenException(
          TFunction<T, R> function, T t, Object attachment, TimeUnit timeUnit, long interval) throws Throwable {
    try {
      return function.apply(t);
    } catch (Throwable throwable) {
      log.error(LOG_MSG, StackTraceUtils.getDetailedCallerInfo(),
              format(attachment), Duration.ofMillis(timeUnit.toMillis(interval)), throwable);
      SleepUtils.sleep(timeUnit, interval);
      return function.apply(t);
    }
  }

  private static String format(Object attachment) {
    if (attachment == null) {
      return null;
    }
    if (attachment instanceof CharSequence) {
      return String.valueOf(attachment);
    }
    return JacksonUtils.toStr(attachment);
  }

}
