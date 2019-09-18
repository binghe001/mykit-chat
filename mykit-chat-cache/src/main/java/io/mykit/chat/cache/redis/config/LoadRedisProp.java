package io.mykit.chat.cache.redis.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: binghe
 * @Description: 加载指定的redis.properties
 */

public class LoadRedisProp extends BaseRedisProp {

    private volatile static Properties instance;

    static {
        InputStream in = LoadRedisProp.class.getClassLoader().getResourceAsStream(FILE_NAME);
        instance = new Properties();
        try {
            instance.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringValue(String key){
        if(instance == null) return "";
        return instance.getProperty(key, "");
    }

    public static Integer getIntegerValue(String key){
       String v = getStringValue(key);
       return (v == null || v.trim().isEmpty()) ? 0 : Integer.parseInt(v);
    }

    public static Boolean getBooleanValue(String key){
       String v = getStringValue(key);
       return(v == null || v.trim().isEmpty()) ? false : Boolean.parseBoolean(key);
    }
}
