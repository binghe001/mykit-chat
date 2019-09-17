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
package io.mykit.chat.proto.wrapper;

import com.alibaba.fastjson.JSONObject;
import io.mykit.chat.constants.MykitChatConstants;
import io.mykit.chat.proto.MykitChatProto;
import io.mykit.chat.utils.date.DateUtils;

import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 消息协议的包装类
 * 消息协议格式如下：
 *  | head |    body   |
 *  |  4   | 消息体内容 |
 */
public class MykitChatProtoWrapper {

    /**
     * ping消息
     */
    public static final int PING_PROTO = 1 << 8 | 220;
    /**
     * pong消息
     */
    public static final int PONG_PROTO = 2 << 8 | 220;
    /**
     * 系统消息
     */
    public static final int SYST_PROTO = 3 << 8 | 220;
    /**
     * 错误消息
     */
    public static final int EROR_PROTO = 4 << 8 | 220;
    /**
     * 认证消息
     */
    public static final int AUTH_PROTO = 5 << 8 | 220;
    /**
     * 普通消息
     */
    public static final int MESS_PROTO = 6 << 8 | 220;

    public static String buildPingProto() {
        return buildProto(PING_PROTO, null);
    }

    public static String buildPongProto() {
        return buildProto(PONG_PROTO, null);
    }

    public static String buildSystProto(int code, Object mess) {
        MykitChatProto chatProto = new MykitChatProto(SYST_PROTO, null);
        chatProto.put(MykitChatConstants.CODE, code);
        chatProto.put(MykitChatConstants.MESSAGE, mess);
        return JSONObject.toJSONString(chatProto);
    }


    public static String buildAuthProto(int code, String connId, Object mess) {
        MykitChatProto chatProto = new MykitChatProto(SYST_PROTO, null);
        chatProto.put(MykitChatConstants.CODE, code);
        chatProto.put(MykitChatConstants.CONN_ID, connId);
        chatProto.put(MykitChatConstants.MESSAGE, mess);
        return JSONObject.toJSONString(chatProto);
    }

    public static String buildAuthProto(boolean isSuccess) {
        MykitChatProto chatProto = new MykitChatProto(AUTH_PROTO, null);
        chatProto.put(MykitChatConstants.IS_SUCCESS, isSuccess);
        return JSONObject.toJSONString(chatProto);
    }

    public static String buildErorProto(int code, String mess) {
        MykitChatProto chatProto = new MykitChatProto(EROR_PROTO, null);
        chatProto.put(MykitChatConstants.CODE, code);
        chatProto.put(MykitChatConstants.MESSAGE, mess);
        return JSONObject.toJSONString(chatProto);
    }

    public static String buildMessProto(int uid, String nick, String mess) {
        MykitChatProto chatProto = new MykitChatProto(MESS_PROTO, mess);
        chatProto.put(MykitChatConstants.CONN_ID, uid);
        chatProto.put(MykitChatConstants.CONN_NAME, nick);
        chatProto.put(MykitChatConstants.CURRENT_TIME, DateUtils.parseDateToString(new Date(), DateUtils.TIME_FORMAT));
        return JSONObject.toJSONString(chatProto);
    }

    public static String buildMessProto(String connId, String connName, String mess) {
        MykitChatProto chatProto = new MykitChatProto(MESS_PROTO, mess);
        chatProto.put(MykitChatConstants.CONN_ID, connId);
        chatProto.put(MykitChatConstants.CONN_NAME, connName);
        chatProto.put(MykitChatConstants.CURRENT_TIME, DateUtils.parseDateToString(new Date(), DateUtils.TIME_FORMAT));
        return JSONObject.toJSONString(chatProto);
    }

    public static String buildProto(int head, String body) {
        MykitChatProto chatProto = new MykitChatProto(head, body);
        return JSONObject.toJSONString(chatProto);
    }
}
