package cn.addenda.component.base.test.allocator;

import cn.addenda.component.base.allocator.lock.ReentrantLockIdleExpirationAllocator;
import org.junit.Test;

/**
 * @author addenda
 * @since 2023/6/3 12:36
 */
public class Test_ReentrantLockIdelExpirationAllocator extends Test_Base_Allocator {

  public Test_ReentrantLockIdelExpirationAllocator() {
    super(new ReentrantLockIdleExpirationAllocator(1L, false));
  }

  @Test
  public void test() {
    baseTest();
  }

}
