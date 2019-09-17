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

import io.mykit.chat.utils.common.BlankUitls;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author binghe
 * @version 1.0.0
 * @description 文件加载器
 */
public class MykitChatFileLoader extends BaseFileLoader{

    private static volatile Properties instance;

    static{
        try {
            InputStream rs = MykitChatFileLoader.class.getClassLoader().getResourceAsStream("websocket.properties");
            instance = new Properties();
            instance.load(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringValueByKey(String key){
        return instance.getProperty(key, "");
    }

    public static Integer getIntValueByKey(String key){
        String str = getStringValueByKey(key);
        return BlankUitls.isBlank(str) ? 0 : Integer.parseInt(str);
    }

    public static Long getLongValueByKey(String key){
        String str = getStringValueByKey(key);
        return BlankUitls.isBlank(str) ? 0L : Long.parseLong(str);
    }
    public static void main(String[] args) {
        System.out.println(MykitChatFileLoader.getStringValueByKey(MykitChatFileLoader.DEFAULT_HOST));
    }
}
