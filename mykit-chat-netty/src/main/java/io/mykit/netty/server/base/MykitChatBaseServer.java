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
package io.mykit.netty.server.base;

import io.mykit.chat.config.MykitChatFileLoader;
import io.mykit.chat.utils.common.BlankUitls;
import io.mykit.netty.init.MykitChatChannelInitializer;
import io.mykit.netty.utils.NettyUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基础的服务器类，实现NettyServer接口
 */
public abstract class MykitChatBaseServer implements NettyServer{
    protected String host;
    protected Integer port;
    protected DefaultEventLoopGroup defaultEventLoopGroup;
    protected NioEventLoopGroup bossEventLoopGroup;
    protected NioEventLoopGroup workEventLoopGroup;
    protected NioServerSocketChannel serverSocketChannel;
    protected ChannelFuture channelFuture;
    protected ServerBootstrap serverBootstrap;
    private String webSocketUrl;

    public MykitChatBaseServer(String host, Integer port){
        if(!BlankUitls.isBlank(host)){
            this.host = host;
        }else{
            this.host = MykitChatFileLoader.getStringValueByKey(MykitChatFileLoader.DEFAULT_HOST);
        }
        if(!BlankUitls.isBlank(port)){
            this.port = port;
        }else{
            this.port = MykitChatFileLoader.getIntValueByKey(MykitChatFileLoader.DEFAULT_PORT);
        }
        this.webSocketUrl = NettyUtils.getWebSocketUrl(this.host, this.port);
        this.init();
    }
    /**
     * 初始化方法
     */
    private void init(){
        defaultEventLoopGroup = new DefaultEventLoopGroup(8, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "DEFAULTEVENTLOOPGROUP_" + index.incrementAndGet());
            }
        });
        bossEventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "BOSS_" + index.incrementAndGet());
            }
        });
        workEventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 10, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        });

        serverBootstrap = new ServerBootstrap();
    }

    @Override
    public void start() {
        serverBootstrap.group(bossEventLoopGroup, workEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .localAddress(new InetSocketAddress(host, port))
                .childHandler(new MykitChatChannelInitializer(this.defaultEventLoopGroup, this.webSocketUrl));
    }

    @Override
    public void shutdown() {
        if (defaultEventLoopGroup != null) {
            defaultEventLoopGroup.shutdownGracefully();
        }
        bossEventLoopGroup.shutdownGracefully();
        workEventLoopGroup.shutdownGracefully();
    }
}
