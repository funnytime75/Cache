package LocalCache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EnCache {

    private static CacheManager cacheManager;
    private Cache<String, String> simpleCache;

    // 初始化缓存
    public EnCache() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("encacheInstance",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(20)))
                .build(true);
        simpleCache = cacheManager.getCache("encacheInstance", String.class, String.class);
    }

    // 缓存数据
    public String put(String key, String value) {
        simpleCache.put(key, value);
        return "添加成功";
    }

    // 不需要考虑缓存未命中，因为ehcache会自动处理，缓存未命中返回null,也可以通过返回值来判断缓存命中来进行手动处理
    // 获取数据
    public String get(String key) {
        String value = simpleCache.get(key);
        if (value == null) {
            return "缓存未命中，使用默认值";
        } else {
            return "获取成功" + value;
        }
    }

    // 删除数据
    public String delete(String key) {
        simpleCache.remove(key);
        return "删除成功";
    }

    //关闭连接
    public String closeCacheManager() {
        if (simpleCache != null) {
            cacheManager.close();
            return "关闭成功";
        } else {
            return "缓存未初始化";
        }


    }

}
