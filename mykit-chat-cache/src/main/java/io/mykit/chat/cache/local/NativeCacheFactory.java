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
package io.mykit.chat.cache.local;

import io.mykit.chat.entity.ConnectionInfo;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binghe
 * @version 1.0.0
 * @description 本地缓存单例
 */
public class NativeCacheFactory {

    /**
     * 缓存Channel与连接信息的对应关系
     */
    public static class ChannelConnectionCache {
        private static volatile ConcurrentMap<Channel, ConnectionInfo> instance;

        static {
            instance = new ConcurrentHashMap<Channel, ConnectionInfo>();
        }
        public static ConcurrentMap<Channel, ConnectionInfo> getInstance(){
            return instance;
        }
    }

    /**
     * 缓存接入消息系统的连接数量
     */
    public static class ConnectionCountCache {
        private static volatile AtomicInteger instance;
        static{
            instance = new AtomicInteger(0);
        }
        public static AtomicInteger getInstance(){
            return instance;
        }
    }

    /**
     * 存储连接名称，连接名称不能重复
     */
    public static class ConnectionNameCache {
        private static volatile Map<String, Set<String>> instance;
        static {
            instance = new HashMap<String, Set<String>>();
        }
        public static Map<String, Set<String>>  getInstance(){
            return instance;
        }
    }
}
