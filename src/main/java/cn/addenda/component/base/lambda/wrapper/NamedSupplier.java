package cn.addenda.component.base.lambda.wrapper;

import cn.addenda.component.base.string.Slf4jUtils;
import lombok.Getter;

import java.util.function.Function;
import java.util.function.Supplier;

public class NamedSupplier<R> implements Supplier<R> {

  @Getter
  private final String name;

  private final Supplier<R> supplier;

  private NamedSupplier(String name, Supplier<R> supplier) {
    this.name = name;
    this.supplier = supplier;
  }

  private NamedSupplier(String name, Function<String, R> function) {
    this.name = name;
    this.supplier = new Supplier<R>() {
      @Override
      public R get() {
        return function.apply(name);
      }

      @Override
      public String toString() {
        return function.toString();
      }
    };
  }

  public static <R> NamedSupplier<R> of(String name, Supplier<R> supplier) {
    return new NamedSupplier<>(name, supplier);
  }

  public static <R> NamedSupplier<R> of(String name, Function<String, R> function) {
    return new NamedSupplier<>(name, function);
  }

  @Override
  public R get() {
    return supplier.get();
  }

  @Override
  public String toString() {
    return Slf4jUtils.format("NamedSupplier: supplier={}, name={}. ", supplier, getName());
  }

}
