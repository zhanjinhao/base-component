package cn.addenda.component.base.collection;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.component.base.lambda.FunctionConverter;
import cn.addenda.component.base.lambda.NullBiFunction;
import cn.addenda.component.base.lambda.NullFunction;
import cn.addenda.component.base.util.Assert;
import cn.addenda.component.stacktrace.StackTraceUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BatchUtils {

  private static final int BATCH_SIZE = 100;

  public static <T> void acceptListInBatches(List<T> params, Consumer<List<T>> consumer) {
    acceptInBatches(params, iterable -> consumer.accept(IterableUtils.castToList(iterable)));
  }

  public static <R, T> List<R> applyListInBatches(List<T> params, Function<List<T>, List<R>> function) {
    Function<Iterable<T>, Iterable<R>> iterableFunction = ts -> function.apply(IterableUtils.castToList(ts));
    return IterableUtils.castToList(applyInBatches(params, iterableFunction));
  }

  public static <T> void acceptListInBatches(List<T> params, Consumer<List<T>> consumer, String name) {
    acceptInBatches(params, iterable -> consumer.accept(IterableUtils.castToList(iterable)), name);
  }

  public static <R, T> List<R> applyListInBatches(List<T> params, Function<List<T>, List<R>> function, String name) {
    Function<Iterable<T>, Iterable<R>> iterableFunction = ts -> function.apply(IterableUtils.castToList(ts));
    return IterableUtils.castToList(applyInBatches(params, iterableFunction, name));
  }

  public static <T> void acceptListInBatches(List<T> params, Consumer<List<T>> consumer, int batchSize, String name) {
    acceptInBatches(params, iterable -> consumer.accept(IterableUtils.castToList(iterable)), batchSize, name);
  }

  public static <R, T> List<R> applyListInBatches(List<T> params, Function<List<T>, List<R>> function, int batchSize, String name) {
    Function<Iterable<T>, Iterable<R>> iterableFunction = ts -> function.apply(IterableUtils.castToList(ts));
    return IterableUtils.castToList(applyInBatches(params, iterableFunction, batchSize, name));
  }

  public static <T> void acceptInBatches(Iterable<T> params, Consumer<Iterable<T>> consumer) {
    applyInBatches(params, FunctionConverter.toFunction(consumer));
  }

  public static <R, T> Iterable<R> applyInBatches(Iterable<T> params, Function<Iterable<T>, Iterable<R>> function) {
    return applyInBatches(params, function, BATCH_SIZE, null);
  }

  public static <T> void acceptInBatches(Iterable<T> params, Consumer<Iterable<T>> consumer, String name) {
    applyInBatches(params, FunctionConverter.toFunction(consumer), name);
  }

  public static <R, T> Iterable<R> applyInBatches(Iterable<T> params, Function<Iterable<T>, Iterable<R>> function, String name) {
    return applyInBatches(params, function, BATCH_SIZE, name);
  }

  public static <T> void acceptInBatches(Iterable<T> params, Consumer<Iterable<T>> consumer, int batchSize, String name) {
    applyInBatches(params, FunctionConverter.toFunction(consumer), batchSize, name);
  }

  public static <R, T> Iterable<R> applyInBatches(Iterable<T> params, Function<Iterable<T>, Iterable<R>> function, int batchSize, String name) {
    Assert.isTrue(batchSize > 0, "`batchSize` should be greater than !`0");
    if (params == null) {
      return new ArrayList<>();
    }
    if (name == null) {
      name = StackTraceUtils.getCallerInfo();
    }
    long start = System.currentTimeMillis();
    List<R> result = new ArrayList<>();
    List<List<T>> paramsList = IterableUtils.splitToListList(params, batchSize);
    for (int i = 0; i < paramsList.size(); i++) {
      List<T> paramSeg = paramsList.get(i);
      if (log.isDebugEnabled()) {
        log.debug("applyInBatches [{}] Seg-param-{}: {}", name, i, JacksonUtils.toStr(paramSeg));
      }
      Iterable<R> resultSeg = function.apply(paramSeg);
      if (!(function instanceof NullFunction)) {
        if (log.isDebugEnabled()) {
          log.debug("applyInBatches [{}] Seg-result-{}: {}", name, i, JacksonUtils.toStr(resultSeg));
        }
      }
      if (resultSeg != null) {
        resultSeg.forEach(result::add);
      }
    }
    log.info("applyInBatches [{}] operation execute [{}] ms. ", name, System.currentTimeMillis() - start);
    return result;
  }

  // -------------------------
  //  BiConsumer & BiFunction
  // -------------------------

  public static <T1, T2> void acceptListInBatches(
          List<T1> param1s, List<T2> param2s, BiConsumer<List<T1>, List<T2>> biConsumer) {
    acceptInBatches(param1s, param2s,
            (ts1, ts2) -> biConsumer.accept(IterableUtils.castToList(ts1), IterableUtils.castToList(ts2)));
  }

  public static <R, T1, T2> List<R> applyListInBatches(
          List<T1> param1s, List<T2> param2s, BiFunction<List<T1>, List<T2>, List<R>> biFunction) {
    BiFunction<Iterable<T1>, Iterable<T2>, Iterable<R>> iterableBiFunction =
            (ts1, ts2) -> biFunction.apply(IterableUtils.castToList(ts1), IterableUtils.castToList(ts2));
    return IterableUtils.castToList(applyInBatches(param1s, param2s, iterableBiFunction));
  }

  public static <T1, T2> void acceptListInBatches(
          List<T1> param1s, List<T2> param2s, BiConsumer<List<T1>, List<T2>> biConsumer, String name) {
    acceptInBatches(param1s, param2s,
            (ts1, ts2) -> biConsumer.accept(IterableUtils.castToList(ts1), IterableUtils.castToList(ts2)), name);
  }

  public static <R, T1, T2> List<R> applyListInBatches(
          List<T1> param1s, List<T2> param2s, BiFunction<List<T1>, List<T2>, List<R>> biFunction, String name) {
    BiFunction<Iterable<T1>, Iterable<T2>, Iterable<R>> iterableBiFunction =
            (ts1, ts2) -> biFunction.apply(IterableUtils.castToList(ts1), IterableUtils.castToList(ts2));
    return IterableUtils.castToList(applyInBatches(param1s, param2s, iterableBiFunction, name));
  }

  public static <T1, T2> void acceptListInBatches(
          List<T1> param1s, List<T2> param2s, BiConsumer<List<T1>, List<T2>> biConsumer, int batchSize, String name) {
    acceptInBatches(param1s, param2s,
            (ts1, ts2) -> biConsumer.accept(IterableUtils.castToList(ts1), IterableUtils.castToList(ts2)), batchSize, name);
  }

  public static <R, T1, T2> List<R> applyListInBatches(
          List<T1> param1s, List<T2> param2s, BiFunction<List<T1>, List<T2>, List<R>> biFunction, int batchSize, String name) {
    BiFunction<Iterable<T1>, Iterable<T2>, Iterable<R>> iterableBiFunction =
            (ts1, ts2) -> biFunction.apply(IterableUtils.castToList(ts1), IterableUtils.castToList(ts2));
    return IterableUtils.castToList(applyInBatches(param1s, param2s, iterableBiFunction, batchSize, name));
  }

  public static <T1, T2> void acceptInBatches(
          Iterable<T1> param1s, Iterable<T2> param2s, BiConsumer<Iterable<T1>, Iterable<T2>> biConsumer) {
    applyInBatches(param1s, param2s, FunctionConverter.toBiFunction(biConsumer));
  }

  public static <R, T1, T2> Iterable<R> applyInBatches(
          Iterable<T1> param1s, Iterable<T2> param2s, BiFunction<Iterable<T1>, Iterable<T2>, Iterable<R>> biFunction) {
    return applyInBatches(param1s, param2s, biFunction, BATCH_SIZE, null);
  }

  public static <T1, T2> void acceptInBatches(
          Iterable<T1> param1s, Iterable<T2> param2s, BiConsumer<Iterable<T1>, Iterable<T2>> biConsumer, String name) {
    applyInBatches(param1s, param2s, FunctionConverter.toBiFunction(biConsumer), name);
  }

  public static <R, T1, T2> Iterable<R> applyInBatches(
          Iterable<T1> param1s, Iterable<T2> param2s, BiFunction<Iterable<T1>, Iterable<T2>, Iterable<R>> biFunction, String name) {
    return applyInBatches(param1s, param2s, biFunction, BATCH_SIZE, name);
  }

  public static <T1, T2> void acceptInBatches(
          Iterable<T1> param1s, Iterable<T2> param2s, BiConsumer<Iterable<T1>, Iterable<T2>> biConsumer, int batchSize, String name) {
    applyInBatches(param1s, param2s, FunctionConverter.toBiFunction(biConsumer), batchSize, name);
  }

  public static <R, T1, T2> Iterable<R> applyInBatches(
          Iterable<T1> param1s, Iterable<T2> param2s, BiFunction<Iterable<T1>, Iterable<T2>, Iterable<R>> biFunction, int batchSize, String name) {
    Assert.isTrue(batchSize > 0, "`batchSize` should be greater than !`0");
    if (param1s == null || param2s == null) {
      return new ArrayList<>();
    }
    if (name == null) {
      name = StackTraceUtils.getCallerInfo();
    }
    long start = System.currentTimeMillis();
    List<R> result = new ArrayList<>();
    List<List<T1>> param1sList = IterableUtils.splitToListList(param1s, batchSize);
    List<List<T2>> param2sList = IterableUtils.splitToListList(param2s, batchSize);
    for (int i = 0; i < param1sList.size(); i++) {
      List<T1> param1Seg = param1sList.get(i);
      for (int j = 0; j < param2sList.size(); j++) {
        List<T2> param2Seg = param2sList.get(j);
        if (log.isDebugEnabled()) {
          log.debug("applyInBatches [{}] Seg-param-{}-{}: [{}], [{}]", name, i, j, JacksonUtils.toStr(param1Seg), JacksonUtils.toStr(param2Seg));
        }
        Iterable<R> resultSeg = biFunction.apply(param1Seg, param2Seg);
        if (!(biFunction instanceof NullBiFunction)) {
          if (log.isDebugEnabled()) {
            log.debug("applyInBatches [{}] Seg-result-{}-{}: [{}]", name, i, j, JacksonUtils.toStr(resultSeg));
          }
        }
        if (resultSeg != null) {
          resultSeg.forEach(result::add);
        }
      }
    }
    log.info("applyInBatches [{}] operation execute [{}] ms. ", name, System.currentTimeMillis() - start);
    return result;
  }

}
