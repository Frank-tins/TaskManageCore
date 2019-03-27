package com.base.util;

public class StringUtils extends org.springframework.util.StringUtils {

    public static String toString(Object obj){
        return isEmpty(obj) ? "" : obj.toString();
    }

    public static boolean isBank(Object obj){
        return isEmpty(obj) || "".equals(obj.toString().trim());
    }

    public static boolean isNotBank(Object obj){
        return !isBank(obj);
    }

}
