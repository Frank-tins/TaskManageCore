package com.task.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static final Gson gson = new Gson();

    private static final Gson dateGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static String toString(Object obj){
        if(obj == null) return "null";
        if(obj instanceof String) return obj.toString();
        return gson.toJson(obj);
    }

    public static String toStringConvertDate(Object obj){
        if(obj == null) return "null";
        if(obj instanceof String) return obj.toString();
        return dateGson.toJson(obj);
    }

}
