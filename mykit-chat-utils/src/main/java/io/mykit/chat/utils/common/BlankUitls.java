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
package io.mykit.chat.utils.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author binghe
 * @version 1.0.0
 * @description 判断字符串、集合、哈希、数组对象是否为空
 */
public final class BlankUitls {
    /**
     * 判断字符串是否为空
     */
    public static boolean isBlank(final String str) {
        return (str == null) || (str.trim().length() <= 0);
    }

    /**
     * 判断字符是否为空
     */
    public static boolean isBlank(final Character cha) {
        return (cha == null) || cha.equals(' ');
    }

    /**
     * 判断对象是否为空
     */
    public static boolean isBlank(final Object obj) {
        return (obj == null);
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isBlank(final Object[] objs) {
        return (objs == null) || (objs.length <= 0);
    }

    /**
     * 判断Collectionj是否为空
     */
    public static boolean isBlank(final Collection<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * 判断Set是否为空
     */
    public static boolean isBlank(final Set<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * 判断整数的包装类型是否为空
     */
    public static boolean isBlank(Integer i) {
        return i == null || i < 1;
    }

    /**
     * 判断Serializable是否为空
     *
     */
    public static boolean isBlank(final Serializable obj) {
        return obj == null;
    }

    /**
     * 判断Map是否为空
     */
    public static boolean isBlank(final Map<?, ?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }
}
