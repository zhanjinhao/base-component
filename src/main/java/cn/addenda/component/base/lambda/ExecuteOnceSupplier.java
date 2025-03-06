package cn.addenda.component.base.lambda;

import java.util.function.Supplier;

public class ExecuteOnceSupplier<R> implements Supplier<R> {
  private final Supplier<R> supplier;
  private R r;

  public ExecuteOnceSupplier(Supplier<R> supplier) {
    this.supplier = supplier;
  }

  @Override
  public R get() {
    return r == null ? (r = supplier.get()) : r;
  }

  public static <R> ExecuteOnceSupplier<R> of(Supplier<R> supplier) {
    return new ExecuteOnceSupplier<>(supplier);
  }

}
