package com.base.util;

import java.util.*;

public final class ArrayUtils {

    public static boolean contain(Object[] objs, Object obj){
        Boolean isString = false;
        isString = objs.length > 0 && objs[0] instanceof String;
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == obj) return true;
            if(objs[i].equals(obj)) return true;
            if(isString && objs[i].equals(obj == null ? "null" : obj.toString())) return true;
        }
        return false;
    }

    public static Boolean isBank(Object obj) throws IllegalArgumentException{
        if(obj == null) return true;
        if(obj instanceof Map) return ((Map) obj).size() == 0 ;
        if(obj instanceof Collection) return ((Collection) obj).size() == 0 ;
        if(obj.getClass().isArray()){
            Object [] array = (Object[]) obj;
            return array.length == 0;
        }
        return null;
    }


}
