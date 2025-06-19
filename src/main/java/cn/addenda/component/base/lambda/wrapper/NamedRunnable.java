package cn.addenda.component.base.lambda.wrapper;

import cn.addenda.component.base.AbstractNamed;
import cn.addenda.component.base.string.Slf4jUtils;

import java.util.function.Consumer;

public class NamedRunnable extends AbstractNamed implements Runnable {

  private final Runnable runnable;

  private NamedRunnable(String name, Runnable runnable) {
    super(name);
    this.runnable = runnable;
  }

  private NamedRunnable(String name, Consumer<String> consumer) {
    super(name);
    this.runnable = new Runnable() {
      @Override
      public void run() {
        consumer.accept(name);
      }

      @Override
      public String toString() {
        return consumer.toString();
      }
    };
  }

  public static NamedRunnable of(String name, Runnable runnable) {
    return new NamedRunnable(name, runnable);
  }

  public static NamedRunnable of(String name, Consumer<String> consumer) {
    return new NamedRunnable(name, consumer);
  }

  @Override
  public void run() {
    runnable.run();
  }

  @Override
  public String toString() {
    return Slf4jUtils.format("NamedRunnable: runnable={}, name={}. ", runnable, getName());
  }

}
