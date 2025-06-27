package cn.addenda.component.base.lambda.wrapper;

import java.util.function.Supplier;

public class ExecuteOnceSupplier<R> implements Supplier<R> {
  private final Supplier<R> supplier;
  private R r;

  public ExecuteOnceSupplier(Supplier<R> supplier) {
    this.supplier = supplier;
  }

  @Override
  public R get() {
    synchronized (this) {
      if (r == null) {
        r = supplier.get();
      }
      return r;
    }
  }

  public static <R> ExecuteOnceSupplier<R> of(Supplier<R> supplier) {
    return new ExecuteOnceSupplier<>(supplier);
  }

  @Override
  public String toString() {
    return "ExecuteOnceSupplier{" +
            "supplier=" + supplier +
            '}';
  }

}
