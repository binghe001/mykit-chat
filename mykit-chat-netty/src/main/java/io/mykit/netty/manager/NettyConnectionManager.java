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
package io.mykit.netty.manager;

import io.mykit.chat.cache.local.NativeCacheUitls;
import io.mykit.chat.config.MykitChatFileLoader;
import io.mykit.chat.constants.MykitChatConstants;
import io.mykit.chat.entity.ConnectionInfo;
import io.mykit.chat.proto.wrapper.MykitChatProtoWrapper;
import io.mykit.chat.utils.common.BlankUitls;
import io.mykit.chat.utils.lock.LockUtils;
import io.mykit.chat.utils.uuid.UUIDUtils;
import io.mykit.netty.utils.NettyUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author binghe
 * @version 1.0.0
 * @description 用户与Channel的管理器类
 */
public class NettyConnectionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyConnectionManager.class);

    /**
     * 添加Channel和客户端连接信息的对应关系
     */
    public static void addChannel(Channel channel){
        String remoteAddr = NettyUtils.parseChannelRemoteAddr(channel);
        if(!channel.isActive()){
            LOGGER.error("channel is not active, address: {}", remoteAddr);
        }
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setRemoteAddr(remoteAddr);
        connectionInfo.setChannel(channel);
        connectionInfo.setTimeStamp(System.currentTimeMillis());
        //缓存Channel和连接信息的对应关系
        NativeCacheUitls.saveConnectionInfo(channel, connectionInfo);
    }

    /**
     * 用户认证，这里只是简单的连接名称，之后加入聊天组
     */
    public static String saveConnectionInfo(Channel channel, String connectionName){
        String connId = MykitChatConstants.DEFAULT_CONNECTION_ID;
        //连接名称不能为空
        if (BlankUitls.isBlank(connectionName)){
            LOGGER.error("connectionName is null...");
            return connId;
        }
        //当前连接名称的缓存中是否存在当前连接名称，连接名称不能重复
        if(NativeCacheUitls.containsConnectionName(connectionName)){
            LOGGER.error("connectionName must not be same...");
            return connId;
        }
        //获取连接信息
        ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
        //连接信息为空
        if(connInfo == null){
            LOGGER.error("user info is null...");
            return connId;
        }

        //当前channel不活跃
        if(!channel.isActive()){
            LOGGER.error("channel is not active, address: {}, nick: {}", connInfo.getRemoteAddr(), connectionName);
            return connId;
        }
        //计数器+1
        NativeCacheUitls.incrementCount();
        //新增一个用户的认证信息
        connInfo.setConnectionName(connectionName);
        connInfo.setAuth(true);
        connId = UUIDUtils.getStringUUID();
        connInfo.setConnectionId(connId);
        connInfo.setUuid(UUIDUtils.getIntegerUUID());
        connInfo.setTimeStamp(System.currentTimeMillis());
        //将认证后的连接信息重新放入缓存
        NativeCacheUitls.saveConnectionInfo(channel, connInfo);
        //缓存当前客户端连接时用户填写的昵称，之后再次填写昵称时不得重复
        NativeCacheUitls.saveConnectionName(connectionName);
        return connId;
    }


    /**
     * 用户认证，这里只是简单的连接名称，之后加入聊天组
     */
    public static boolean saveConnection(Channel channel, String connectionName){
        return !MykitChatConstants.DEFAULT_CONNECTION_ID.equals(saveConnectionInfo(channel, connectionName));
    }

    /**
     * 验证认证是否成功
     */
    public static boolean isAuthSuccess(String connId){
        return !MykitChatConstants.DEFAULT_CONNECTION_ID.equals(connId);
    }

    /**
     * 从缓存中移除Channel，并关闭Channel
     * 同时将相应的用户连接信息从缓存中清除
     * 并且删除对应的昵称
     */
    public static void removeChannel(Channel channel){
        try{
            LOGGER.warn("channel will be remove, address is :{}", NettyUtils.parseChannelRemoteAddr(channel));
            //加写锁
            LockUtils.writeLock();
            ConnectionInfo connectionInfo = NativeCacheUitls.deleteConnectionInfo(channel);
            //说明删除成功，并且删除的连接信息是认证的连接信息
            if (connectionInfo != null && connectionInfo.getAuth()){
                //缓存计数器减去1
                NativeCacheUitls.decrementCount();
                //删除当前的连接名称
                NativeCacheUitls.deleteConnectionName(connectionInfo.getConnectionName());
            }
            //关闭Channel
            channel.close();
        }finally {
            LockUtils.unWriteLock();
        }
    }

    /**
     * 广播普通分组消息
     */
    public static void broadcastGroupMessage(Integer uuid, String connName, String message){
        //广播的消息不能为空
        if(!BlankUitls.isBlank(message)){
            try{
                LockUtils.readLock();
                Set<Channel> keySet = NativeCacheUitls.getKeyChannels();
                if(!BlankUitls.isBlank(keySet)){
                    for(Channel channel : keySet){
                        ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
                        //获取的连接信息不为空，同时当前的连接信息认证过，则发送消息
                        if(connInfo != null && connInfo.getAuth()){
                            channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildMessProto(uuid, connName, message)));
                        }
                    }
                }

            }finally {
                LockUtils.unReadLock();
            }
        }
    }
    /**
     * 广播普通分组消息
     */
    public static void broadcastGroupMessage(String connId, String connName, String message){
        //广播的消息不能为空
        if(!BlankUitls.isBlank(message)){
            try{
                LockUtils.readLock();
                Set<Channel> keySet = NativeCacheUitls.getKeyChannels();
                if(!BlankUitls.isBlank(keySet)){
                    for(Channel channel : keySet){
                        ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
                        //获取的连接信息不为空，同时当前的连接信息认证过，则发送消息
                        if(connInfo != null && connInfo.getAuth()){
                            channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildMessProto(connId, connName, message)));
                        }
                    }
                }

            }finally {
                LockUtils.unReadLock();
            }
        }
    }

    /**
     * 广播系统消息
     */
    public static void broadcastSystemMessage(int code, Object message){
        try{
            LockUtils.readLock();
            Set<Channel> keySet = NativeCacheUitls.getKeyChannels();
            if(!BlankUitls.isBlank(keySet)){
                for(Channel channel : keySet){
                    ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
                    if(connInfo != null && connInfo.getAuth()){
                        channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildSystProto(code, message)));
                    }
                }
            }
        }finally {
            LockUtils.unReadLock();
        }
    }

    /**
     * 广播ping消息
     */
    public static void broadcastPingMessage(){
        try{
            LockUtils.readLock();
            LOGGER.info("broadCastPing connection count: {}", NativeCacheUitls.getConnectionCount());
            Set<Channel> keySet = NativeCacheUitls.getKeyChannels();
            if(!BlankUitls.isBlank(keySet)){
                for(Channel channel : keySet){
                    ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
                    if(connInfo != null && connInfo.getAuth()){
                        channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildPingProto()));
                    }
                }
            }
        }finally {
            LockUtils.unReadLock();
        }
    }


    /**
     * 向单个连接发送消息
     */
    public static void sendSystemMessage(Channel channel, int code, Object message){
        channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildSystProto(code, message)));
    }

    /**
     * 向单个连接发送消息
     */
    public static void sendSystemMessage(Channel channel, int code, String connId,  Object message){
        channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildAuthProto(code, connId, message)));
    }

    /**
     * 发送pong消息
     */
    public static void sendPongMessage(Channel channel){
        channel.writeAndFlush(new TextWebSocketFrame(MykitChatProtoWrapper.buildPongProto()));
    }

    /**
     * 扫描并关闭失效的Channel
     */
    public static void scanNotActiveChannel(){
        Set<Channel> keySet = NativeCacheUitls.getKeyChannels();
        if(!BlankUitls.isBlank(keySet)){
            for(Channel channel : keySet){
                ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
                if(connInfo != null){
                    //移除符合条件的连接
                    if(!channel.isOpen() || !channel.isActive() || (!connInfo.getAuth() && (System.currentTimeMillis() - connInfo.getTimeStamp()) > MykitChatFileLoader.getLongValueByKey(MykitChatFileLoader.DEFAULT_TIME_OUT))){
                        //移除连接
                        removeChannel(channel);
                    }
                }
            }
        }
    }

    /**
     * 获取连接信息
     */
    public static ConnectionInfo getConnectionInfo(Channel channel){
        return NativeCacheUitls.getConnectionInfo(channel);
    }

    /**
     * 获取认证的连接数量
     */
    public static int getAuthConnectionCount(){
        return NativeCacheUitls.getAuthConnectionCount();
    }

    /**
     * 更新连接的时间
     */
    public static void updateConnectionTime(Channel channel){
        ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(channel);
        if(connInfo != null){
            connInfo.setTimeStamp(System.currentTimeMillis());
            NativeCacheUitls.saveConnectionInfo(channel, connInfo);
        }
    }
}
