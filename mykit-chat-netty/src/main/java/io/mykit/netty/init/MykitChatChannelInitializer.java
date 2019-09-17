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
package io.mykit.netty.init;

import io.mykit.netty.handler.ConnectionAuthHandler;
import io.mykit.netty.handler.MessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * @author binghe
 * @version 1.0.0
 * @description Initializer初始化器
 */
public class MykitChatChannelInitializer extends ChannelInitializer<SocketChannel> {
    private DefaultEventLoopGroup defaultEventLoopGroup;
    private String webSocketUrl;
    public MykitChatChannelInitializer(DefaultEventLoopGroup defaultEventLoopGroup, String webSocketUrl){
        this.defaultEventLoopGroup = defaultEventLoopGroup;
        this.webSocketUrl = webSocketUrl;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(defaultEventLoopGroup,
                new HttpServerCodec(),    //请求解码器
                new HttpObjectAggregator(65536),     //将多个消息转换成单一的消息对象的最大长度
                new ChunkedWriteHandler(),    //支持异步发送大的码流，一般用于发送文件流
                new IdleStateHandler(60, 0, 0),    //检测链路是否读空闲
                new ConnectionAuthHandler(this.webSocketUrl),    //处理握手和认证
                new MessageHandler()    //处理消息的认证
        );
    }
}
