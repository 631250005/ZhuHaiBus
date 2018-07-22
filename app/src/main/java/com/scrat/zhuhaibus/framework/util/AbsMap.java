package com.scrat.zhuhaibus.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbsMap<K, V> extends HashMap<K, V> {

    public V optValue(K key, V defaultValue) {
        V value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public String optStr(K key, String defaultValue) {
        Object obj = get(key);
        if (obj == null) {
            return defaultValue;
        }
        return String.valueOf(obj);
    }

    public String optStr(K key) {
        return optStr(key, "");
    }

    public int optInt(K key, int defaultValue) {
        String str = optStr(key, null);
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public long optInt(K key) {
        return optInt(key, 0);
    }

    public long optLong(K key, long defaultValue) {
        String str = optStr(key, null);
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public long optLong(K key) {
        return optLong(key, 0L);
    }

    public <T> List<T> optList(K key) {
        V list = get(key);
        if (list != null) {
            return (List<T>) list;
        }

        return new ArrayList<>();
    }
}
