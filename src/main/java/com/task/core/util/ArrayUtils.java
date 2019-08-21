package com.task.core.util;

import java.util.*;

public final class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

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

    /**
     * Object 是否为空集合
     *
     *      null                    true
     *      []                      true    (Collection , Map , Array)
     *      object                  null
     *      [object]                false
     *      Map[{k:"", v: ""}]      false
     *
     * @param obj
     * @return
     * @throws IllegalArgumentException
     */
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
