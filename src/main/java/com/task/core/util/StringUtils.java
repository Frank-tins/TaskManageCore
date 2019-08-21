package com.task.core.util;

import java.util.UUID;

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

    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String format (String str, String... args){
        String rel = str;
        for (int i = 0; i < args.length; i++) {
            rel.replace("%" + i , args[i]);
        }
        return rel;
    }

}
