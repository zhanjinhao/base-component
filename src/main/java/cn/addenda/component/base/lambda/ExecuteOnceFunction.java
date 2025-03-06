package cn.addenda.component.base.lambda;

import java.util.function.Function;

public class ExecuteOnceFunction<T, R> implements Function<T, R> {

  private final Function<T, R> function;
  private R r;

  public ExecuteOnceFunction(Function<T, R> function) {
    this.function = function;
  }

  @Override
  public R apply(T t) {
    return r == null ? (r = function.apply(t)) : r;
  }

  public static <T, R> ExecuteOnceFunction<T, R> of(Function<T, R> function) {
    return new ExecuteOnceFunction<>(function);
  }

}
