package cn.addenda.component.base.lambda.wrapper;

import lombok.Getter;

public class AttachmentRunnable<A> implements Runnable {

  private final Runnable runnable;

  @Getter
  private final A attachment;

  private AttachmentRunnable(A attachment, Runnable runnable) {
    this.attachment = attachment;
    this.runnable = runnable;
  }

  @Override
  public void run() {
    runnable.run();
  }

  @Override
  public String toString() {
    return "AttachmentRunnable{" +
            "runnable=" + runnable +
            ", attachment=" + attachment +
            '}';
  }

  public static <A> AttachmentRunnable<A> of(A attachment, Runnable runnable) {
    return new AttachmentRunnable<>(attachment, runnable);
  }

}
