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
package io.mykit.chat.proto;

import io.mykit.chat.utils.exception.MykitChatException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binghe
 * @version 1.0.0
 * @description 消息协议
 * | head |    body   |
 * |  4   | 消息体内容 |
 */
public class MykitChatProto {

    private Integer version = 1;
    private Integer uri;
    private String body;
    private Map<String,Object> extend = new HashMap<>();

    public MykitChatProto(int head, String body) {
        this.uri = head;
        this.body = body;
    }

    public Integer getUri() {
        return uri;
    }

    public void setUri(Integer uri) {
        this.uri = uri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public void put(String key, Object value){
       if (this.extend == null){
           throw new MykitChatException("MykitChatProto对象中的extendMap为空");
       }
       this.extend.put(key, value);
    }
}
