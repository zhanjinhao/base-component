package cn.addenda.component.test.cache.lru;

import cn.addenda.component.cache.ExpiredHashMapKVCache;
import cn.addenda.component.cache.KVCache;
import cn.addenda.component.cache.eviction.lru.LruKKVCache;

/**
 * 通过leetCode测试
 */
public class LRUCache_2 {

  private int capacity;

  KVCache<Integer, Integer> kvCache;

  public LRUCache_2(int capacity) {
    this.capacity = capacity;
    kvCache = new LruKKVCache<>(capacity, 1, new ExpiredHashMapKVCache<>());
  }

  public int get(int key) {
    Integer a = kvCache.get(key);
    return a == null ? -1 : a;
  }

  public void put(int key, int value) {
    kvCache.set(key, value);
  }

}

