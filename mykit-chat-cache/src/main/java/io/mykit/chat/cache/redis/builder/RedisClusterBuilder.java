package io.mykit.chat.cache.redis.builder;

import io.mykit.chat.cache.redis.config.LoadRedisProp;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author binghe
 * @version 1.0.0
 * @description 集群缓存配置
 */
public class RedisClusterBuilder {

    private volatile static JedisCluster instance;

    static {
        instance = new JedisCluster(getHostAndPorts(), getJedisPoolConfig());
    }

    /**
     * 获取JedisCluster句柄对象
     * @return JedisCluster句柄对象
     */
    public static JedisCluster getInstance(){
        return instance;
    }

    /**
     * 构建Redis缓存池配置
     * @return JedisPoolConfig对象
     */
    private static JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_MAX_TOTAL));
        jedisPoolConfig.setMaxIdle(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_MAX_IDLE));
        jedisPoolConfig.setMinIdle(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_MIN_IDLE));
        jedisPoolConfig.setBlockWhenExhausted(LoadRedisProp.getBooleanValue(LoadRedisProp.CLUSTER_BLOCKWHENEXHAUSTED));
        jedisPoolConfig.setMaxWaitMillis(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_MAXWAITMILLIS));
        jedisPoolConfig.setTestOnBorrow(LoadRedisProp.getBooleanValue(LoadRedisProp.CLUSTER_TESTONBORROW));
        jedisPoolConfig.setTestOnReturn(LoadRedisProp.getBooleanValue(LoadRedisProp.CLUSTER_TESTONRETURN));
        jedisPoolConfig.setTestWhileIdle(LoadRedisProp.getBooleanValue(LoadRedisProp.CLUSTER_TESTWHILEIDLE));
        jedisPoolConfig.setMinEvictableIdleTimeMillis(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_MINEVICTABLEIDLETIMEMILLIS));
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_TIMEBETWEENEVICTIONRUNSMILLIS));
        jedisPoolConfig.setNumTestsPerEvictionRun(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NUMTESTSPEREVICTIONRUN));
        return jedisPoolConfig;
    }

    public static void main(String[] args){
        System.out.println(LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_MAX_TOTAL));
    }


    /**
     * 构建Redis集群所需要的IP和端口列表
     * @return Redis集群所需要的IP和端口列表
     */
   private static Set<HostAndPort> getHostAndPorts(){
       Set<HostAndPort> nodes = new HashSet<HostAndPort>();
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_ONE), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_ONE_PORT)));
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_TWO), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_TWO_PORT)));
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_THREE), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_THREE_PORT)));
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_FOUR), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_FOUR_PORT)));
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_FIVE), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_FIVE_PORT)));
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_SIX), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_SIX_PORT)));
       nodes.add(new HostAndPort(LoadRedisProp.getStringValue(LoadRedisProp.CLUSTER_NODE_SEVEN), LoadRedisProp.getIntegerValue(LoadRedisProp.CLUSTER_NODE_SEVEN_PORT)));
       return nodes;
   }
}
