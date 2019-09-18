/**
 * Copyright 2019-2999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.chat.cache.redis;

import io.mykit.chat.cache.redis.builder.RedisClusterBuilder;
import redis.clients.jedis.JedisCluster;

/**
 * @author binghe
 * @version 1.0.0
 * @description 对外提供的Redis工具类
 */
public class RedisUtils {
    /**
     * 保存数据到redis
     * @param key 保存的数据key
     * @param value 保存的value
     * @param expireTime 过期时间,单位秒
     */
    public static void saveValueToRedis(String key, String value, int expireTime){
        JedisCluster jedisCluster = RedisClusterBuilder.getInstance();
        if(jedisCluster != null){
            String ret =  jedisCluster.setex(key, expireTime, value);
        }
    }

    /**
     * 保存数据到redis
     * @param key 保存的数据key
     * @param value 保存的value
     */
    public static void saveValueToRedis(String key, String value){
        JedisCluster jedisCluster = RedisClusterBuilder.getInstance();
        if(jedisCluster != null){
            String ret =  jedisCluster.set(key, value);
        }
    }
    /**
     * 从Redis中获取数据
     * @param key 获取数据的key
     * @return redis中缓存的key
     */
    public static String getValueFromRedis(String key){
        JedisCluster jedisCluster = RedisClusterBuilder.getInstance();
        if (jedisCluster == null || !jedisCluster.exists(key)) return "";
        return jedisCluster.get(key);
    }
}
