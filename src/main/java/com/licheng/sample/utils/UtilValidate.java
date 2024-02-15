package com.licheng.sample.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 空校验工具类
 *
 * @author LiCheng
 * @data 2018.3.10
 */
@SuppressWarnings("all")
public class UtilValidate {
    private UtilValidate() {
    }

    public static boolean isEmpty(Object o) {
        return ObjectType.isEmpty(o);
    }

    public static boolean isNotEmpty(Object o) {
        return !ObjectType.isEmpty(o);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static <E> boolean isEmpty(Collection<E> c) {
        return c == null || c.isEmpty();
    }

    public static <K, E> boolean isEmpty(Map<K, E> m) {
        return m == null || m.isEmpty();
    }

    public static <E> boolean isEmpty(CharSequence c) {
        return c == null || c.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static <E> boolean isNotEmpty(Collection<E> c) {
        return c != null && !c.isEmpty();
    }

    public static <E> boolean isNotEmpty(CharSequence c) {
        return c != null && c.length() > 0;
    }
}

class ObjectType {
    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return ((String) value).length() == 0;
        } else if (value instanceof Collection) {
            return ((Collection) value).size() == 0;
        } else if (value instanceof Map) {
            return ((Map) value).size() == 0;
        } else if (value instanceof CharSequence) {
            return ((CharSequence) value).length() == 0;
        } else if (value instanceof Boolean) {
            return false;
        } else if (value instanceof Number) {
            return false;
        } else if (value instanceof Character) {
            return false;
        } else if (value instanceof Object[]) {
            return ((Object[]) value).length == 0;
        } else if (value instanceof int[]) {
            return ((int[]) value).length == 0;
        } else if (value instanceof double[]) {
            return ((double[]) value).length == 0;
        } else if (value instanceof byte[]) {
            return ((byte[]) value).length == 0;
        } else if (value instanceof long[]) {
            return ((long[]) value).length == 0;
        } else if (value instanceof char[]) {
            return ((char[]) value).length == 0;
        } else if (value instanceof float[]) {
            return ((float[]) value).length == 0;
        } else if (value instanceof short[]) {
            return ((short[]) value).length == 0;
        } else if (value instanceof boolean[]) {
            return ((boolean[]) value).length == 0;
        } else {
            return value instanceof Date ? false : false;
        }
    }
}