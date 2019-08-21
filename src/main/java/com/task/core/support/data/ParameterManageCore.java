package com.task.core.support.data;

import com.task.core.annotation.TaskData;
import com.task.core.excption.DataExpException;
import com.task.core.excption.DataParameterException;
import com.task.core.util.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 表达式处理核心
 *
 * @author Frank
 */
public class ParameterManageCore {

    private Logger logger = LogManager.getLogger(ParameterManageCore.class);

    public List analysisParameter(Method method, Object[] parameterValues) throws DataParameterException {
        List rel = new ArrayList();
        Parameter [] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            TaskData taskData = parameter.getAnnotation(TaskData.class);
            if(taskData != null) {
                Object value = parameterValues[i];
                if(value instanceof List){
                    return (List)value;
                }else{
                    throw new DataParameterException("parameter convert error. [" + value.getClass().toString() + "] to [java.util.List]");
                }
            }
        }
        return rel;
    }

}
