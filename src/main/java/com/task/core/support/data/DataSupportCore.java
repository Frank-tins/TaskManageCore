package com.task.core.support.data;

import com.task.core.annotation.TaskData;
import com.task.core.bean.RunTaskInfo;
import com.task.core.enums.DataType;
import com.task.core.excption.DataExpException;
import com.task.core.excption.DataParameterException;
import com.task.core.util.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 数据获取核心
 *
 * @author Frank
 */
public class DataSupportCore {

    private Logger logger = LoggerFactory.getLogger(DataSupportCore.class);

    private ExpManageCore expManageCore = new ExpManageCore();

    private ParameterManageCore parameterManageCore = new ParameterManageCore();

    public DataSupportCore(){ logger.debug("TMC [Data Support Core] [START]");}

    public List<Object> getData(DataType dataType, String exp, Method method, Object[] parameter) throws DataExpException, DataParameterException {

        if(dataType ==DataType.NOT_DATA){ return null;}

        List<Object> rel = null;
        try {
            switch (dataType){
                case EXP:
                    rel = expManageCore.analysisExp(exp, method, parameter);
                    break;
                case PARAMETER:
                    rel = parameterManageCore.analysisParameter(method, parameter);
                    break;
                default:
                    return null;
            };
        } catch (DataParameterException|DataExpException e) {
            logger.error("load task data error");
            throw e;
        }
        return rel;
    }

    public int getDataParameterIndex(Method method){

        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            TaskData taskData = parameter.getAnnotation(TaskData.class);
            if(taskData != null) {
                return i;
            }
        }
        return -1;
    }

}
