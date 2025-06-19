package cn.addenda.component.base.lambda.wrapper;

public class ExecuteOnceRunnable implements Runnable {

  private final Runnable runnable;
  private boolean b = false;

  public ExecuteOnceRunnable(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public void run() {
    synchronized (this) {
      if (!b) {
        b = true;
        runnable.run();
      }
    }
  }

  public static ExecuteOnceRunnable of(Runnable runnable) {
    return new ExecuteOnceRunnable(runnable);
  }

}