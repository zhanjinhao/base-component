package cn.addenda.component.base.lambda.wrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExecuteOnceFunction<T, R> implements Function<T, R> {

  private final Function<T, R> function;
  private final Map<T, R> resultMap = new HashMap<>();

  public ExecuteOnceFunction(Function<T, R> function) {
    this.function = function;
  }

  @Override
  public R apply(T t) {
    synchronized (this) {
      return resultMap.computeIfAbsent(t, function);
    }
  }

  public static <T, R> ExecuteOnceFunction<T, R> of(Function<T, R> function) {
    return new ExecuteOnceFunction<>(function);
  }

}
