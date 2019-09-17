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
import io.mykit.chat.code.MykitChatCode;
import io.mykit.chat.config.MykitChatFileLoader;
import io.mykit.chat.constants.MykitChatConstants;
import io.mykit.netty.manager.NettyConnectionManager;
import io.mykit.netty.utils.NettyUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author binghe
 * @version 1.0.0
 * @description 连接认证处理器
 */
public class ConnectionAuthHandler extends SimpleChannelInboundHandler<Object> {
    private final Logger logger = LoggerFactory.getLogger(ConnectionAuthHandler.class);
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            this.handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            this.handleWebSocket(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent evnet = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (evnet.state().equals(IdleState.READER_IDLE)) {
                final String remoteAddress = NettyUtils.parseChannelRemoteAddr(ctx.channel());
                logger.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                NettyConnectionManager.removeChannel(ctx.channel());
                NettyConnectionManager.broadcastSystemMessage(MykitChatCode.SYS_CONNECTION_COUNT, NettyConnectionManager.getAuthConnectionCount());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    /**
     * 处理http协议的握手
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            logger.warn("protobuf don't support websocket");
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                MykitChatFileLoader.getStringValueByKey(MykitChatFileLoader.WEBSOCKET_URL), null, true);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 动态加入websocket的编解码处理
            handshaker.handshake(ctx.channel(), request);
            //存储已经连接的Channel
            NettyConnectionManager.addChannel(ctx.channel());
        }
    }

    /**
     * 处理WebSocket信息
     */
    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路命令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            NettyConnectionManager.removeChannel(ctx.channel());
            return;
        }
        // 判断是否Ping消息
        if (frame instanceof PingWebSocketFrame) {
            logger.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否Pong消息
        if (frame instanceof PongWebSocketFrame) {
            logger.info("pong message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        // 本程序目前只支持文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame type not supported");
        }
        String message = ((TextWebSocketFrame) frame).text();
        JSONObject json = JSONObject.parseObject(message);
        int code = json.getInteger("code");
        Channel channel = ctx.channel();
        switch (code) {
            case MykitChatCode.PING_CODE:
            case MykitChatCode.PONG_CODE:
                NettyConnectionManager.updateConnectionTime(channel);
//                UserInfoManager.sendPong(ctx.channel());
                logger.info("receive pong message, address: {}", NettyUtils.parseChannelRemoteAddr(channel));
                return;
            case MykitChatCode.AUTH_CODE:
                boolean isSuccess = NettyConnectionManager.saveConnection(channel, json.getString(MykitChatConstants.CONN_NAME));
                NettyConnectionManager.sendSystemMessage(channel, MykitChatCode.SYS_AUTH_STATE,isSuccess);
                if (isSuccess) {
                    NettyConnectionManager.broadcastSystemMessage(MykitChatCode.SYS_CONNECTION_COUNT, NettyConnectionManager.getAuthConnectionCount());
                }
                return;
            case MykitChatCode.MESS_CODE: //普通的消息留给MessageHandler处理
                break;
            default:
                logger.warn("The code [{}] can't be auth!!!", code);
                return;
        }
        //后续消息交给MessageHandler处理
        ctx.fireChannelRead(frame.retain());
    }
}
