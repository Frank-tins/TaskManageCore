package com.task.core.util;

import java.util.UUID;

public class StringUtils extends org.springframework.util.StringUtils {

    public static String toString(Object obj){
        return isEmpty(obj) ? "" : obj.toString();
    }

    public static boolean isBlank(Object obj){
        return isEmpty(obj) || "".equals(obj.toString().trim());
    }

    public static boolean isNotBlank(Object obj){
        return !isBlank(obj);
    }

    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String format (String str, Object... args){
        String rel = str;
        for (int i = 0; i < args.length; i++) {
            rel.replace("%" + i , toString(args[i]));
        }
        return rel;
    }

    public static boolean equalsString(String obj1, String obj2) {

        if(isEmpty(obj1) && isEmpty(obj2)) {
            return true;
        }
        if(isEmpty(obj1)) {
            return false;
        }

        return obj1.equals(obj2);
    }
}
