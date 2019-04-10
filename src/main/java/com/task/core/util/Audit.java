package com.task.core.util;

import com.google.gson.Gson;

/**
 *
 * DATAChecked
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
    public static void arraysNotNull(String msg, Object... objs) throws IllegalArgumentException{
        if(objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++)  arrayNotNull(msg, objs[i]);
    }

    public static void arrayNotNull(String msg, Object objs) throws IllegalArgumentException{
        Boolean rel = ArrayUtils.isBank(objs);
        if(rel == null) throw new IllegalArgumentException("obj not a Array");
        if (rel) throw new IllegalArgumentException(msg);
    }

    /**
     * 数据集中存在不为空的数据则 正常 否则抛出异常
     * @param msg
     * @param objs
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public static void validDataObjects(String msg, Object... objs) throws IllegalArgumentException, NullPointerException{
        if(objs == null) throw new NullPointerException();
        for (int i = 0; i < objs.length; i++) {
            //为空跳过
            if (objs[i] == null) continue;
            Boolean arrayRel = ArrayUtils.isBank(objs[i]);
            //为空表示为Object 且非空
            if(arrayRel == null) return;
            //不为空表示 为集合类型 如果为false 表示集合存在内容
            if(!arrayRel) return;
        }
        throw new IllegalArgumentException(msg);
    }


    public static <T extends Number> void numberValid(String msg, T min, T max , T... values) throws IllegalArgumentException, NullPointerException{
        if((min == null && max == null) || (values.length == 0)) throw new NullPointerException();
        for (int i = 0; i < values.length; i++) {
            if(values[i] == null) continue;
            //
//            if(values[i] > max) throw new IllegalArgumentException(msg);
        }
    }

}
