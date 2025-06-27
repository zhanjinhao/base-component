package cn.addenda.component.base.lambda.wrapper;

import lombok.Getter;

import java.util.function.Supplier;

public class AttachmentSupplier<A, R> implements Supplier<R> {

  private final Supplier<R> supplier;

  @Getter
  private final A attachment;

  private AttachmentSupplier(A attachment, Supplier<R> supplier) {
    this.attachment = attachment;
    this.supplier = supplier;
  }

  @Override
  public R get() {
    return supplier.get();
  }

  @Override
  public String toString() {
    return "AttachmentSupplier{" +
            "supplier=" + supplier +
            ", attachment=" + attachment +
            '}';
  }

  public static <A, R> AttachmentSupplier<A, R> of(A attachment, Supplier<R> supplier) {
    return new AttachmentSupplier<>(attachment, supplier);
  }

}
