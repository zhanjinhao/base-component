package cn.addenda.component.base.lambda.wrapper;

import cn.addenda.component.base.AbstractNamed;
import cn.addenda.component.base.string.Slf4jUtils;
import cn.addenda.component.stacktrace.StackTraceUtils;

import java.util.function.BiFunction;
import java.util.function.Function;

public class NamedFunction<T, R> extends AbstractNamed implements Function<T, R> {

  private final Function<T, R> function;

  private NamedFunction(String name, Function<T, R> function) {
    super(name);
    this.function = function;
  }

  private NamedFunction(String name, BiFunction<String, T, R> biFunction) {
    super(name);
    this.function = new Function<T, R>() {
      @Override
      public R apply(T t) {
        return biFunction.apply(name, t);
      }

      @Override
      public String toString() {
        return biFunction.toString();
      }
    };
  }

  @Override
  public R apply(T t) {
    return function.apply(t);
  }

  public static <T, R> NamedFunction<T, R> of(String name, Function<T, R> function) {
    return new NamedFunction<>(name, function);
  }

  public static <T, R> NamedFunction<T, R> of(String name, BiFunction<String, T, R> biFunction) {
    return new NamedFunction<>(name, biFunction);
  }

  public static <T, R> NamedFunction<T, R> of(Function<T, R> function) {
    return new NamedFunction<>(StackTraceUtils.getCallerInfo(), function);
  }

  public static <T, R> NamedFunction<T, R> of(BiFunction<String, T, R> biFunction) {
    return new NamedFunction<>(StackTraceUtils.getCallerInfo(), biFunction);
  }

  @Override
  public String toString() {
    return Slf4jUtils.format("NamedFunction: function={}, name={}. ", function, getName());
  }

}
