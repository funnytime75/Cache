package RemoteCache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MemCache {
    private MemcachedClient memcachedClient;
    private final String SERVER_ADDRESS = "39.105.143.99:11211";

    // 初始化连接
    public MemCache() {
        try {
            MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(SERVER_ADDRESS));
            builder.setConnectionPoolSize(3);
            memcachedClient = builder.build();
        } catch (IOException e) {
            throw new RuntimeException("Memcached连接失败" + e.getMessage());
        }
    }

    // 缓存数据
    public String put(String key, String value, int expiration) {
        try {
            memcachedClient.set(key, expiration, value);
            return "添加成功";
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return "添加失败:" + e.getMessage();
        }
    }

    //获取数据(带未命中处理)
    public String get(String key) {
        try {
            String value = memcachedClient.get(key);
            if (value == null) {
                return "缓存未命中，使用默认值";
            } else {
                return "获取成功" + value;
            }
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return "获取失败:" + e.getMessage();
        }
    }

    // 删除数据
    public String delete(String key) {
        try {
            memcachedClient.delete(key);
            return "删除成功";
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return "删除失败:" + e.getMessage();
        }
    }

    // 关闭连接
    public String closeClient() {
        try {
            if (memcachedClient != null) {
                memcachedClient.shutdown();
                return "关闭成功";
            }
            return "客户端未初始化";

        } catch (IOException e) {
            e.printStackTrace();
            return "关闭失败:" + e.getMessage();
        }
    }

}

