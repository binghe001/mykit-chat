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
package io.mykit.chat.config;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基础的文件加载类，主要是存放常量
 */
public class BaseFileLoader {

    /**
     * 默认主机名
     */
    public static final String DEFAULT_HOST = "default_host";
    /**
     * 默认端口为8088
     */
    public static final String DEFAULT_PORT = "default_port";

    /**
     * WebSocket发布的端口号默认为8099
     */
    public static final String WEB_SOCKET_PORT = "web_socket_port";
    /**
     * 默认WebSocket发布地址，端口为8099
     */
    public static final String WEBSOCKET_URL = "websocket_url";

    /**
     * Channel管道默认的超时时间
     */
    public static final String DEFAULT_TIME_OUT = "default_time_out";
}
