package cn.addenda.component.base.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author addenda
 * @since 2022/8/11
 */
public class UnLockAfterLockFailedTest {

  private static ReentrantLock lock = new ReentrantLock();

  public static void main(String[] args) {

    lock.unlock();

  }

}
