package com.task.core.start.component;

import com.task.core.annotation.CreateTask;
import com.task.core.annotation.TaskService;
import com.task.core.annotation.start.TaskPackage;
import com.task.core.bean.TaskManageConfigBean;
import com.task.core.enums.DataType;
import com.task.core.enums.LogLevel;
import com.task.core.support.task.TaskManageCoreSupervise;
import com.task.core.util.Audit;
import com.task.core.util.ClassScanner;
import com.task.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 任务注册器
 * @author Frank
 */
public class RegisterLoading  {

    private Logger logger  = LoggerFactory.getLogger(RegisterLoading.class);

    private final String DEFAULT_PARAMETER = "[null]";

    private final boolean DEFAULT_RUN_STATUS = true;

    private final int DEFAULT_THREAD_NUMBER = 100;

    public void run(TaskManageConfigBean taskManageConfigBean) throws Exception {
        //加载许可校验
        Audit.isNotNull("The task pool is not listening for any valid tasks", taskManageConfigBean);
        scannerLoading(taskManageConfigBean.getPackages());
    }

    public void run(AnnotationAttributes[] taskPackages) throws Exception {
        //加载许可校验
        Audit.arraysNotNull("The task pool is not listening for any valid tasks", taskPackages);
        scannerLoading(convertStringPackages(taskPackages));
    }

    /**
     * 注解配置转换
     * @param taskPackages
     * @return
     */
    private List<String> convertStringPackages(AnnotationAttributes[] taskPackages) {
        List<String> packages = new ArrayList<>();
        for (int i = 0; i < taskPackages.length; i++) {
            AnnotationAttributes annotationAttributes = taskPackages[i];
            packages.add(annotationAttributes.getString("packageName"));
        }
        return packages;
    }

    /**
     * 扫描加载
     */
    private void scannerLoading(List<String> packages){
        Audit.arrayNotNull("The task pool is not listening for any valid tasks", packages);

        //扫描包信息
        if( packages.size() > 0 ) {
            String [] temp = new String[]{};
            Set<Class> types = ClassScanner.scan(packages.toArray(temp), TaskService.class);

            Map<CreateTask, Method> map = ClassScanner.scannerAllMethod(types, CreateTask.class, false);

            map.forEach(
                (k, v) -> register(k.code() , k.value(), k.describe(), k.threadNumber(),k.isEnable(), k.dataType(), k.dataExp(), k.level(), v)
            );
        }
    }


    /**
     * 任务注册
     * @param sgtin
     * @param name
     * @param describe
     * @param threadNumber
     * @param enable
     * @param dataType
     * @param exp
     * @param level
     * @param method
     */
    private void register(String sgtin, String name, String describe, Integer threadNumber, Boolean enable, DataType dataType, String exp, LogLevel level, Method method){

        Audit.thereAreValidValues("Invalid task configuration ", sgtin, name, describe, threadNumber, enable);
        //默认值
        name = StringUtils.isBlank(name) ? DEFAULT_PARAMETER : name;
        describe = StringUtils.isBlank(describe) ? DEFAULT_PARAMETER : describe;
        threadNumber = threadNumber == null || threadNumber < 1 ? DEFAULT_THREAD_NUMBER : threadNumber;
        enable = enable == null ?  DEFAULT_RUN_STATUS : enable;
        dataType = dataType == null ? DataType.NOT_DATA : dataType;
        level = level == null ? LogLevel.ERROR_INFO : level;

        Audit.isNotNull("Invalid task configuration ", sgtin, name, describe, threadNumber, enable, dataType, level);
        //注册任务
        TaskManageCoreSupervise.register(sgtin, name, describe, threadNumber, enable, dataType, exp, level, method);
    }


}
