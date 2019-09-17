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
package io.mykit.netty.utils;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * @author binghe
 * @version 1.0.0
 * @description Netty工具类
 */
public class NettyUtils {

    /**
     * 获取Channel的远程IP地址
     * @param channel Netty封装的Channel
     * @return Channel的远程IP地址
     */
    public static String parseChannelRemoteAddr(final Channel channel) {
        if (null == channel) {
            return "";
        }
        SocketAddress remote = channel.remoteAddress();
        final String addr = remote != null ? remote.toString() : "";

        if (addr.length() > 0) {
            int index = addr.lastIndexOf("/");
            if (index >= 0) {
                return addr.substring(index + 1);
            }

            return addr;
        }

        return "";
    }

    /**
     * 获取WebSocket URL地址
     */
    public static String getWebSocketUrl(String host, Integer port){
        StringBuilder sb = new StringBuilder();
        sb.append("ws://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append("/websocket");
        return sb.toString();
    }
}
