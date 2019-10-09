package com.task.core.support.data;

import com.task.core.annotation.TaskData;
import com.task.core.excption.DataExpException;
import com.task.core.util.ArrayUtils;
import com.task.core.util.ClassScanner;
import com.task.core.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;


/**
 * 表达式处理核心
 *
 * @author Frank
 */
public class ExpManageCore {

    private Logger logger = LoggerFactory.getLogger(ExpManageCore.class);

    public List analysisExp(String exp, Method baseMethod, Object[] parameterValues) throws DataExpException {
        //TODO 正则
        String expType = exp.substring(0,exp.indexOf("[")).toUpperCase();
        String expClass = exp.substring(exp.indexOf("[") + 1, exp.indexOf("("));
        String expMethod = exp.substring(exp.indexOf("(") + 1, exp.indexOf(")"));
        List<Object> rel ;

        Parameter[] parameters = baseMethod.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            TaskData taskData = parameter.getAnnotation(TaskData.class);
            if(taskData != null) {
                parameterValues = ArrayUtils.remove(parameterValues, i);
            }
        }

        Object obj ;

        Class type ;
        if("MAPPER".equals(expType)) {
            try {
                type = Class.forName(expClass, true, ClassLoader.getSystemClassLoader());
            } catch (ClassNotFoundException e) {
                throw new DataExpException(e.getMessage());
            }
            obj = SpringUtil.getBean(type);
        }else if("BEAN".equals(expType)) {
            obj = SpringUtil.getBean(expClass);
            if(obj == null){ throw new DataExpException("error : Bean not found.");}
            type = obj.getClass();
        }else {
            throw new DataExpException("Exp incorrect");
        }

        Method method = ClassScanner.findMethodByName(type, expMethod);

        Object expRel;
        try {
            expRel = method.invoke(obj, parameterValues);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new DataExpException(e.getMessage());
        } catch (Exception e){
            throw new DataExpException(e.getMessage());
        }

        if(expRel == null){
            logger.warn("task dataExp get is null");
        };

        if(expRel instanceof List){
             rel = (List) expRel;
            if(rel.size() == 0){
                logger.warn("task dataExp size 0");
            };
        } else {
            throw new DataExpException("");
        }

        return rel;
    }

}
