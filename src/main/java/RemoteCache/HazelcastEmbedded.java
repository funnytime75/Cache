package RemoteCache;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.concurrent.TimeUnit;

public class HazelcastEmbedded {
    private HazelcastInstance hazelcastInstance;
    private IMap<String, String> simpleCache;

    public HazelcastEmbedded() {
        hazelcastInstance = Hazelcast.newHazelcastInstance();
        simpleCache = hazelcastInstance.getMap("myDistributedCache");
    }

    // 缓存数据
    public String put(String key, String value) {
        simpleCache.put(key, value);
        return "添加成功";
    }

    // 缓存数据并设置过期时间
    public String putWithTTL(String key, String value, long ttlSeconds) {
        simpleCache.put(key, value, ttlSeconds, TimeUnit.SECONDS);
        return "添加成功";
    }

    // 获取数据(带未命中处理)
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

    // 关闭连接
    public String closeClient() {
        if (hazelcastInstance != null) {
            hazelcastInstance.shutdown();
            return "连接已关闭";
        } else {
            return "Hazelcast 集群未初始化";
        }
    }
}
