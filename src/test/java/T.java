import RemoteCache.HazelcastEmbedded;
import RemoteCache.IgniteEmbedded;
import RemoteCache.MemCache;
import RemoteCache.RedisCache;
import LocalCache.CaffeineCache;
import LocalCache.EnCache;
import LocalCache.GuavaCache;
import LocalCache.MapCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class T {
    @Test
    public void test() {
        MapCache cache = new MapCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(3));

    }

    @Test
    public void test1() throws ExecutionException {
        GuavaCache cache = new GuavaCache();
        cache.put("1", "1");
        cache.put("2", "2");
        System.out.println(cache.get("1"));

    }

    @Test
    public void test2() throws ExecutionException {
        CaffeineCache cache = new CaffeineCache();
        cache.put("1", "1");
        cache.put("2", "2");
        System.out.println(cache.get("1"));
    }

    @Test
    public void test3() throws ExecutionException {
        EnCache cache = new EnCache();
        cache.put("1", "1");
        cache.put("2", "2");
        System.out.println(cache.get("1"));
    }

    @Test
    public void test4() throws ExecutionException {
        RedisCache cache = new RedisCache();
        cache.put("1", "1");
        cache.put("2", "2");
        System.out.println(cache.get("1"));
    }

    @Test
    public void test5() throws ExecutionException {
        MemCache cache = new MemCache();
        cache.put("1", "1", 10);
        System.out.println(cache.get("1"));
    }

    @Test
    public void test6() throws ExecutionException {
        HazelcastEmbedded cache = new HazelcastEmbedded();
        cache.put("1", "1");
        System.out.println(cache.get("1"));
    }

    @Test
    public void test7() throws ExecutionException {
        /*
        需要添加启动参数，来绕过反射访问限制
        --add-opens
        java.base/java.nio=ALL-UNNAMED
        --add-opens
        java.base/java.util=ALL-UNNAMED
        --add-opens
        java.base/java.lang=ALL-UNNAMED

        升级到3.x版本能够解决该问题
         */
        IgniteEmbedded cache = new IgniteEmbedded();
        cache.put("1", "1");
        System.out.println(cache.get("1"));
    }
}
