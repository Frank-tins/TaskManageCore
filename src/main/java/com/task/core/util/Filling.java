package com.task.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 装填配置
 *
 * @author Frank
 */
public class Filling {

    private Logger logger = LoggerFactory.getLogger(Filling.class);

    public static <T> T autoFilling(Class<T> type, T value, T defaultValue)throws NullPointerException{
        if(type == null) throw new NullPointerException();
        if(TypeUtils.isBaseClass(type)){
            if(StringUtils.isBlank(value)) return defaultValue;
            if(TypeUtils.isBaseDefaultValue(value)) return defaultValue;
        }
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object val = field.get(value);
                if(StringUtils.isBlank(val)) {
                    val = field.get(defaultValue);
                    field.set(value, val);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    };

}
