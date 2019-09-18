package io.mykit.chat.cache.redis.config;

/**
 * @Author: binghe
 * @Description: 基础的类，存放常量
 */

public class BaseRedisProp {

    public static final String FILE_NAME = "redis.properties";
    public static final String HOST = "redis.host";
    public static final String PORT = "redis.port";
    public static final String MAX_IDLE = "redis.max_idle";
    public static final String MAX_WAIT = "redis.max_wait";
    public static final String MAX_TOTAL = "redis.max_total";
    public static final String TEST_ON_BORROW = "redis.test_on_borrow";
    public static final String TIMEOUT = "redis.timeout";


    //Redis集群配置
    public static final String CLUSTER_PASSWORD = "redis.cluster.password";
    public static final String CLUSTER_MAX_TOTAL = "redis.cluster.max.total";
    public static final String CLUSTER_MAX_IDLE = "redis.cluster.max.idle";
    public static final String CLUSTER_MIN_IDLE = "redis.cluster.min.idle";
    public static final String CLUSTER_TIMEOUT = "redis.cluster.timeout";
    public static final String CLUSTER_MAXATTEMPTS = "redis.cluster.maxAttempts";
    public static final String CLUSTER_REDISDEFAULTEXPIRATION = "redis.cluster.redisDefaultExpiration";
    public static final String CLUSTER_USEPREFIX = "redis.cluster.usePrefix";
    public static final String CLUSTER_BLOCKWHENEXHAUSTED = "redis.cluster.blockWhenExhausted";
    public static final String CLUSTER_MAXWAITMILLIS = "redis.cluster.maxWaitMillis";
    public static final String CLUSTER_TESTONBORROW = "redis.cluster.testOnBorrow";
    public static final String CLUSTER_TESTONRETURN = "redis.cluster.testOnReturn";
    public static final String CLUSTER_TESTWHILEIDLE = "redis.cluster.testWhileIdle";
    public static final String CLUSTER_MINEVICTABLEIDLETIMEMILLIS = "redis.cluster.minEvictableIdleTimeMillis";
    public static final String CLUSTER_TIMEBETWEENEVICTIONRUNSMILLIS = "redis.cluster.timeBetweenEvictionRunsMillis";
    public static final String CLUSTER_NUMTESTSPEREVICTIONRUN = "redis.cluster.numTestsPerEvictionRun";
    public static final String CLUSTER_DEFAULTEXPIRATIONKEY = "redis.cluster.defaultExpirationKey";
    public static final String CLUSTER_EXPIRATIONSECONDTIME = "redis.cluster.expirationSecondTime";
    public static final String CLUSTER_PRELOADSECONDTIME = "redis.cluster.preloadSecondTime";

    //集群节点信息
    public static final String CLUSTER_NODE_ONE = "redis.cluster.node.one";
    public static final String CLUSTER_NODE_ONE_PORT = "redis.cluster.node.one.port";

    public static final String CLUSTER_NODE_TWO = "redis.cluster.node.two";
    public static final String CLUSTER_NODE_TWO_PORT = "redis.cluster.node.two.port";

    public static final String CLUSTER_NODE_THREE = "redis.cluster.node.three";
    public static final String CLUSTER_NODE_THREE_PORT = "redis.cluster.node.three.port";

    public static final String CLUSTER_NODE_FOUR = "redis.cluster.node.four";
    public static final String CLUSTER_NODE_FOUR_PORT = "redis.cluster.node.four.port";

    public static final String CLUSTER_NODE_FIVE = "redis.cluster.node.five";
    public static final String CLUSTER_NODE_FIVE_PORT = "redis.cluster.node.five.port";

    public static final String CLUSTER_NODE_SIX = "redis.cluster.node.six";
    public static final String CLUSTER_NODE_SIX_PORT = "redis.cluster.node.six.port";

    public static final String CLUSTER_NODE_SEVEN = "redis.cluster.node.seven";
    public static final String CLUSTER_NODE_SEVEN_PORT = "redis.cluster.node.seven.port";

}
