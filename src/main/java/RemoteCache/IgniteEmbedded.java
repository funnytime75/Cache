package RemoteCache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

public class IgniteEmbedded {
    private Ignite ignite;
    private IgniteCache<String,String> simpleCache;
    private final String CACHE_NAME = "myIgniteCache";
    public IgniteEmbedded(){
        IgniteConfiguration igniteCfg = new IgniteConfiguration();
        igniteCfg.setIgniteInstanceName("myIgniteCache");

        CacheConfiguration<String,String> cacheCfg = new CacheConfiguration<>(CACHE_NAME);
        igniteCfg.setCacheConfiguration(cacheCfg);
        ignite = Ignition.start(igniteCfg);
        simpleCache = ignite.getOrCreateCache(CACHE_NAME);

    }
    //缓存数据
    public String put(String key, String value){
        simpleCache.put(key,value);
        return "添加成功";
    }

    //获取数据(带未命中处理)
    public String get(String key){
        String value = simpleCache.get(key);
        if(value == null){
            return "缓存未命中，使用默认值";
        }else{
            return "获取成功"+value;
        }
    }

    //删除数据
    public String delete(String key){
        simpleCache.remove(key);
        return "删除成功";
    }

    //关闭连接
    public String closeClient(){
        if(ignite != null){
            ignite.close();
            return "连接已关闭";
        }else{
            return "Ignite 集群未初始化";
        }
    }
}
