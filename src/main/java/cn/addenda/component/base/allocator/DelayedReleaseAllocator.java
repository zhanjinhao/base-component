package cn.addenda.component.base.allocator;

public interface DelayedReleaseAllocator<T> extends Allocator<T> {

  void delayRelease(String name);

}
