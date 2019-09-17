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
package io.mykit.chat.entity;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author binghe
 * @version 1.0.0
 * @description 连接信息
 */
public class ConnectionInfo implements Serializable {
    private static final long serialVersionUID = -6658779960089071152L;

    private Integer uuid;
    /**
     * 是否认证
     */
    private Boolean isAuth = Boolean.FALSE;

    /**
     * 连接时间戳
     */
    private Long timeStamp = 0L;
    /**
     * 用户id
     */
    private String connectionId;
    /**
     * 用户昵称
     */
    private String connectionName;
    /**
     * 远程客户端连接的主机名或IP地址
     */
    private String remoteAddr;
    /**
     * Netty连接通道
     */
    private Channel channel;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Boolean getAuth() {
        return isAuth;
    }

    public void setAuth(Boolean auth) {
        isAuth = auth;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
