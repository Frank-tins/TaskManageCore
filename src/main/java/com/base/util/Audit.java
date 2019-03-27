package com.base.util;

import java.util.Map;

/**
 *
 * @author Frank
 */
public class Audit {

    /**
     * 不存在空值
     * @param msg
     * @param objs
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static void isNotNull(String msg, Object... objs) throws IllegalArgumentException, NullPointerException{
        if(objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++)
            if (objs[i] == null) throw new IllegalArgumentException(msg);
    }

    /**
     * 不存在空字符串
     * @param msg
     * @param objs
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static void isNotBank(String msg, String... objs) throws IllegalArgumentException, NullPointerException{
        if(objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++)
            if (StringUtils.isBank(objs[i])) throw new IllegalArgumentException(msg);
    }

    /**
     * 存在至少一个非空数据
     * @param msg
     * @param objs
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static void thereAreValidValues(String msg, Object... objs) throws IllegalArgumentException, NullPointerException {
        if (objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++)
            if (objs[i] != null) return;
        throw new IllegalArgumentException(msg);
    }

    /**
     * 集合组非空
     * @param msg
     * @param objs
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static void arrayNotNull(String msg, Object... objs) throws IllegalArgumentException, NullPointerException{
        if(objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++) {
            Boolean rel = ArrayUtils.isBank(objs[i]);
            if(rel == null) throw new IllegalArgumentException("obj not a Array");
            if (rel) throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 对象与集合组 非空
     * @param msg
     * @param objs
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static void objectAndArrayIsNotNull(String msg, Object... objs) throws IllegalArgumentException, NullPointerException{
        if(objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++) {
            Boolean rel = ArrayUtils.isBank(objs[i]);
            if(rel == null || rel) throw new IllegalArgumentException(msg);
        }
    }

}
