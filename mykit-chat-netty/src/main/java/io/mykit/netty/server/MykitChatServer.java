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
package io.mykit.netty.server;

import io.mykit.chat.config.MykitChatFileLoader;
import io.mykit.netty.manager.NettyConnectionManager;
import io.mykit.netty.server.base.MykitChatBaseServer;
import io.mykit.netty.server.base.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author binghe
 * @version 1.0.0
 * @description 消息系统的服务类
 */
public class MykitChatServer extends MykitChatBaseServer implements NettyServer {
    protected Logger logger = LoggerFactory.getLogger(MykitChatServer.class);

    private ScheduledExecutorService executorService;

    public MykitChatServer(){
        super();
        executorService = Executors.newScheduledThreadPool(2);
        //初始化
        this.init();
    }

    @Override
    public void start() {
        super.start();
        try {
            channelFuture = serverBootstrap.bind().sync();
            logger.info("Mykit chat system start success, hostname is:{},  port is:{}",
                    MykitChatFileLoader.getStringValueByKey(MykitChatFileLoader.DEFAULT_HOST),
                    MykitChatFileLoader.getIntValueByKey(MykitChatFileLoader.DEFAULT_PORT));

            // 定时扫描所有的Channel，关闭失效的Channel
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    logger.info("scanNotActiveChannel --------");
                    NettyConnectionManager.scanNotActiveChannel();
                }
            }, 3, 60, TimeUnit.SECONDS);

            // 定时向所有客户端发送Ping消息
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    NettyConnectionManager.broadcastPingMessage();
                }
            }, 3, 50, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            logger.error("WebSocketServer start fail,", e);
        }

    }

    @Override
    public void shutdown(){
        if(executorService != null){
            executorService.shutdown();
        }
        super.shutdown();
    }
}
