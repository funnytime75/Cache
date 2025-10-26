package LocalCache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineCache {
    Cache<String, String> simpleCache = Caffeine.newBuilder()
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
        return "添加成功";
    }

    //获取数据
    public String get(String key) {
        return simpleCache.get(key, CaffeineCache::getValueFromDB);
    }

    //删除数据
    public String delete(String key) {
        simpleCache.invalidate(key);
        return "删除成功";
    }



     static String getValueFromDB(String key) {
        return "缓存未命中，使用默认值";
    }
}
