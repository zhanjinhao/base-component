package cn.addenda.component.base.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author addenda
 * @since 2022/8/11
 */
public class UnLockAfterLockFailedTest {

  private static ReentrantLock lock = new ReentrantLock();

  @Test
  public void test() {

    Assert.assertThrows(IllegalMonitorStateException.class, () -> {
      lock.unlock();
    });

  }

}
