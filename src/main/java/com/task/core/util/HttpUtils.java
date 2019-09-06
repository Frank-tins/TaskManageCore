package com.task.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * web工具
 *
 * @author Frank
 */
public class HttpUtils {

    private Logger logger = LogManager.getLogger(HttpUtils.class);


    public static Map<String, Object> getParameter(String... excludes){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();


        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> map = new HashMap<>();
        while (enumeration.hasMoreElements()) {

            String argsName = enumeration.nextElement();
            boolean isExclude = false;
            for (String exclude : excludes) {
                if(StringUtils.equalsString(exclude, argsName)){
                    isExclude = true;
                }
            }

            if(!isExclude){
                map.put(argsName, request.getParameter(argsName));
            }

        }

        return map;

    }

}
