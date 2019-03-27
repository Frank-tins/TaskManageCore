package com.base.util;

public final class ConventUtils {

    public static Integer convertInteger(Object obj){
        if(obj == null) return null;
        return convertInt(obj);
    }

    public static int convertInt(Object obj){
        if(obj == null) return 0;
        if(obj instanceof Number) return ((Number) obj).intValue();
        if(StringUtils.isNotBank(obj)) return Integer.parseInt(obj.toString().trim());

        return 0;
    }

    public static Double convertDoubleObject(Object obj){
        if(obj == null) return null;
        return convertDouble(obj);
    }

    public static double convertDouble(Object obj){
        if(obj == null) return 0.0D;
        if(obj instanceof Number) return ((Number) obj).doubleValue();
        if(StringUtils.isNotBank(obj)) return Double.parseDouble(obj.toString().trim());

        return 0.0D;
    }

    public static long convertLong(Object obj){
        if(obj == null) return 0L;
        if(obj instanceof Number) return ((Number) obj).longValue();
        if(StringUtils.isNotBank(obj)) return Long.parseLong(obj.toString().trim());
        return 0L;
    }

    public static Long convertLongObject(Object obj){
        if(obj == null) return null;
        return convertLong(obj);
    }

}
