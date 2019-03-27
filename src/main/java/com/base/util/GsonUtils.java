package com.base.util;

import com.google.gson.Gson;

public class GsonUtils {

    private static final Gson gson = new Gson();

    public static String toString(Object obj){
        if(obj == null) return "null";
        if(obj instanceof String) return obj.toString();
        return gson.toJson(obj);
    }

}
