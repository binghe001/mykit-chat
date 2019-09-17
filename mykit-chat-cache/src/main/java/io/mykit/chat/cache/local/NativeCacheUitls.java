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

import io.mykit.chat.constants.MykitChatConstants;
import io.mykit.chat.entity.ConnectionInfo;
import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.Set;


/**
 * @author binghe
 * @version 1.0.0
 * @description 本地缓存工具类
 */
public class NativeCacheUitls {

    /**
     * 保存Channel和连接信息的对应关系
     */
    public static void saveConnectionInfo(Channel channel, ConnectionInfo connectionInfo){
        NativeCacheFactory.ChannelConnectionCache.getInstance().put(channel, connectionInfo);
    }

    /**
     * 用过Channel获取连接信息
     */
    public static ConnectionInfo getConnectionInfo(Channel channel){
        return NativeCacheFactory.ChannelConnectionCache.getInstance().get(channel);
    }

    /**
     * 删除连接信息
     */
    public static ConnectionInfo deleteConnectionInfo(Channel channel){
        ConnectionInfo connectionInfo = getConnectionInfo(channel);
        if(connectionInfo != null){
            connectionInfo = NativeCacheFactory.ChannelConnectionCache.getInstance().remove(channel);
        }
        return connectionInfo;
    }

    /**
     * 保存连接名称
     */
    public static void saveConnectionName(String connectionName){
        Set<String> set = NativeCacheFactory.ConnectionNameCache.getInstance().get(MykitChatConstants.CONN_NAME_KEY);
        //Set为空，则实例化一个Set集合
        if(set == null){
            set = new HashSet<String>();
        }
        set.add(connectionName);
        NativeCacheFactory.ConnectionNameCache.getInstance().put(MykitChatConstants.CONN_NAME_KEY, set);
    }

    /**
     * 删除连接名称
     */
    public static void deleteConnectionName(String connectionName){
        Set<String> set = NativeCacheFactory.ConnectionNameCache.getInstance().get(MykitChatConstants.CONN_NAME_KEY);
        if(set != null){
            if(set.contains(connectionName)){
                set.remove(connectionName);
            }
            NativeCacheFactory.ConnectionNameCache.getInstance().put(MykitChatConstants.CONN_NAME_KEY, set);
        }
    }

    /**
     * 缓存中是否包含连接名称
     */
    public static boolean containsConnectionName(String connectionName){
        Set<String> set = NativeCacheFactory.ConnectionNameCache.getInstance().get(MykitChatConstants.CONN_NAME_KEY);
        if (set == null || set.isEmpty()){
            return false;
        }
        return set.contains(connectionName);
    }

    /**
     * 获取Channel和连接信息的对应关系的Key集合
     */
    public static Set<Channel> getKeyChannels(){
        return NativeCacheFactory.ChannelConnectionCache.getInstance().keySet();
    }

    /**
     * 计数器累加1
     */
    public static void incrementCount(){
        NativeCacheFactory.ConnectionCountCache.getInstance().incrementAndGet();
    }

    /**
     * 计数器累减1
     */
    public static void decrementCount(){
        NativeCacheFactory.ConnectionCountCache.getInstance().decrementAndGet();
    }

    /**
     * 获取系统的连接数量
     */
    public static Integer getConnectionCount(){
        return NativeCacheFactory.ConnectionCountCache.getInstance().intValue();
    }

    /**
     * 获取系统认证的连接数量
     */
    public static Integer getAuthConnectionCount(){
        return NativeCacheFactory.ConnectionCountCache.getInstance().get();
    }
}
