package cn.addenda.component.base.test.allocator;

import cn.addenda.component.base.allocator.lock.ReentrantLockDelayedReleaseAllocator;
import org.junit.Test;

/**
 * @author addenda
 * @since 2023/6/3 12:36
 */
public class Test_ReentrantLockDelayedReleaseAllocator extends Test_Base_Allocator {

    public Test_ReentrantLockDelayedReleaseAllocator() {
        super(new ReentrantLockDelayedReleaseAllocator(1000L));
    }

    @Test
    public void test() {
        baseTest();
    }

}
