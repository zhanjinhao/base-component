package cn.addenda.component.base.lambda.wrapper;

import lombok.Getter;

import java.util.function.Function;

public class AttachmentFunction<A, T, R> implements Function<T, R> {

  @Getter
  private final Function<T, R> function;

  @Getter
  private final A attachment;

  private AttachmentFunction(A attachment, Function<T, R> function) {
    this.attachment = attachment;
    this.function = function;
  }

  @Override
  public R apply(T t) {
    return function.apply(t);
  }

  @Override
  public String toString() {
    return "AttachmentFunction{" +
            "function=" + function +
            ", attachment=" + attachment +
            '}';
  }

  public static <A, T, R> AttachmentFunction<A, T, R> of(A attachment, Function<T, R> function) {
    return new AttachmentFunction<>(attachment, function);
  }

}
