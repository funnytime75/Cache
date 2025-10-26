package LocalCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapCache extends LinkedHashMap {
    /*
    可重入读写锁，保证并发读写安全性
     */
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    /*
    缓存大小限制
     */
    private int maxSize;

    public MapCache(int maxSize) {
        super(maxSize + 1, 1.0f, true);
        this.maxSize = maxSize;
    }
    // 获取数据
    @Override
    public Object get(Object key) {
        readLock.lock();
        try {
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }
    // 缓存数据
    @Override
    public Object put(Object key, Object value) {
        writeLock.lock();
        try {
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
    /*
    设置淘汰机制为最近最少使用淘汰
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return this.size() > maxSize;
    }
}
