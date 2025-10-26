package RemoteCache;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisCache {
    private RedissonClient redissonClient;
    private RMap<String, String> simpleCache;
    private final String CACHE_NAME = "redisCacheInstance";

    public RedisCache() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
        simpleCache = redissonClient.getMap(CACHE_NAME);
    }

    public String put(String key, String value) {
        // 可选择是否添加过期时间，一般来说redis使用setWithTTL设置过期时间
        simpleCache.put(key, value);
        return "添加成功";
    }

    public String get(String key) {
        String value = simpleCache.get(key);
        if (value == null) {
            return "缓存未命中，使用默认值";
        } else {
            return "获取成功" + value;
        }
    }

    public String delete(String key) {
        simpleCache.remove(key);
        return "删除成功";
    }

    public String closeClient() {
        if (redissonClient != null) {
            redissonClient.shutdown();
            return "连接已关闭";
        } else {
            return "Redis 客户端未初始化";
        }
    }

}
