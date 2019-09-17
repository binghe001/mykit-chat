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
package io.mykit.chat.core;

import io.mykit.netty.server.MykitChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author binghe
 * @version 1.0.0
 * @description 服务启动类
 */
public class MykitChatStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MykitChatStarter.class);

    public static void main(String[] args){
        String host = "";
        Integer port = -1;
        if(args != null){
            if(args.length > 0){
                host = args[0];
            }
            if(args.length > 1){
                port = Integer.parseInt(args[1]);
            }
        }
        startServer(host, port);
    }

    /**
     * 启动服务
     */
    private static void startServer(String host, Integer port){
        final MykitChatServer mykitChatServer = new MykitChatServer(host, port);
        mykitChatServer.start();
        // 注册进程钩子，在JVM进程关闭前释放资源
        // 注册进程钩子，在JVM进程关闭前释放资源
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                mykitChatServer.shutdown();
                LOGGER.warn(">>>>>>>>>> jvm shutdown");
                System.exit(0);
            }
        });
    }
}
