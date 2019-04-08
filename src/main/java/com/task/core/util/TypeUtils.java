package com.task.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 类型工具
 *
 * @author Frank
 */
public class TypeUtils {

    private Logger logger = LogManager.getLogger(TypeUtils.class);

    public static boolean isBaseType(Object object) {
        Class className = object.getClass();
        return  className.equals(java.lang.Integer.class) ||
                className.equals(java.lang.Byte.class) ||
                className.equals(java.lang.Long.class) ||
                className.equals(java.lang.Double.class) ||
                className.equals(java.lang.Float.class) ||
                className.equals(java.lang.Character.class) ||
                className.equals(java.lang.Short.class) ||
                className.equals(java.lang.Boolean.class);
    }

    public static boolean isBaseDefaultValue(Object object) {
        Class className = object.getClass();
        String strClassName = "" + className;
        if(className.equals(java.lang.Integer.class)) {
            return (int)object == 0;
        } else if(className.equals(java.lang.Byte.class)) {
            return (byte)object == 0;
        } else if(className.equals(java.lang.Long.class)) {
            return (long)object == 0L;
        } else if(className.equals(java.lang.Double.class)) {
            return (double)object == 0.0d;
        } else if(className.equals(java.lang.Float.class)) {
            return (float)object == 0.0f;
        } else if(className.equals(java.lang.Character.class)) {
            return (char)object == '\u0000';
        } else if(className.equals(java.lang.Short.class)) {
            return (short)object == 0;
        } else if(className.equals(java.lang.Boolean.class)) {
            return (boolean)object == false;
        }
        throw new IllegalArgumentException("参数异常");
    }

}
