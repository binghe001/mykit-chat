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
package io.mykit.chat.code;

/**
 * @author binghe
 * @version 1.0.0
 * @description 状态码
 */
public class MykitChatCode {
    public static final int PING_CODE = 10015;
    public static final int PONG_CODE = 10016;

    public static final int AUTH_CODE = 10000;
    public static final int MESS_CODE = 10086;

    /**
     * 系统消息类型
     */
    public static final int SYS_CONNECTION_COUNT = 20001; // 在线用户数
    public static final int SYS_AUTH_STATE = 20002; // 认证结果
    public static final int SYS_OTHER_INFO = 20003; // 系统消息
}
