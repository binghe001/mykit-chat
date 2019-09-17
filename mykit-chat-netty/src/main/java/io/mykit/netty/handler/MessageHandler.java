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
package io.mykit.netty.handler;

import com.alibaba.fastjson.JSONObject;
import io.mykit.chat.cache.local.NativeCacheUitls;
import io.mykit.chat.code.MykitChatCode;
import io.mykit.chat.constants.MykitChatConstants;
import io.mykit.chat.entity.ConnectionInfo;
import io.mykit.netty.manager.NettyConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author binghe
 * @version 1.0.0
 * @description 消息处理器
 */
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        ConnectionInfo connInfo = NativeCacheUitls.getConnectionInfo(ctx.channel());
        //不为空，同时已经认证过
        if(connInfo != null && connInfo.getAuth()){
            JSONObject jsonObject = JSONObject.parseObject(textWebSocketFrame.text());
            //广播返回用户发送的消息文本
            NettyConnectionManager.broadcastGroupMessage(connInfo.getConnectionId(), connInfo.getConnectionName(), jsonObject.getString(MykitChatConstants.MESSAGE));
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //移除当前连接
        NettyConnectionManager.removeChannel(ctx.channel());
        NettyConnectionManager.broadcastSystemMessage(MykitChatCode.SYS_CONNECTION_COUNT, NettyConnectionManager.getAuthConnectionCount());
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("connection error and close the channel", cause);
        NettyConnectionManager.removeChannel(ctx.channel());
        NettyConnectionManager.broadcastSystemMessage(MykitChatCode.SYS_CONNECTION_COUNT, NettyConnectionManager.getAuthConnectionCount());
    }
}
