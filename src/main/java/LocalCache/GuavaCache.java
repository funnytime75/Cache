package LocalCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCache {
    Cache<String, String> simpleCache = CacheBuilder.newBuilder()
            //初始化缓存容量
            .initialCapacity(5)
            //设置缓存最大容量
            .maximumSize(10)
            //设置缓存的过期时间
            .expireAfterWrite(17, TimeUnit.SECONDS)
            .build();

    //缓存数据
    public String put(String key, String value) {
        simpleCache.put(key, value);
        return "缓存数据成功";
    }

    // 获取数据
    public String get(String key) throws ExecutionException {
        return "获取数据成功" + simpleCache.get(key, GuavaCache::getValueFromDB);
    }

    //删除数据
    public String delete(String key) {
        simpleCache.invalidate(key);
        return "删除数据成功";
    }

    static String getValueFromDB() {
        return "缓存未命中，使用默认值";
    }
}

