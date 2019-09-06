package com.task.core.start;

import com.task.core.annotation.start.EnableTaskCore;
import com.task.core.annotation.start.TaskPackage;
import com.task.core.bean.TaskManageConfigBean;
import com.task.core.bean.ThreadPoolConfig;
import com.task.core.start.component.LogoLoading;
import com.task.core.start.component.RegisterLoading;
import com.task.core.start.component.TaskBeanLoading;
import com.task.core.support.thread.ThreadManageCore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 启用组件
 *
 * @author Frank
 */
public class TaskCoreStart implements ImportSelector, BeanFactoryAware {

    private Logger logger = LogManager.getLogger(TaskCoreStart.class);

    private Class<? extends Annotation> enableAnnotationType = EnableTaskCore.class;

    private Class<?> [] startClass = {TaskBeanLoading.class};

    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired(required = false)
    private TaskManageConfigBean taskManageConfigBean;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        try {
            start(annotationMetadata);
            return startClassName();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return new String[0];
    }

    public void start(AnnotationMetadata annotationMetadata) throws Exception{
        Map<String, Object> enableTaskCore = annotationMetadata.getAnnotationAttributes(enableAnnotationType.getName());
        outLog();
        taskRegister(enableTaskCore);
    }

    private void taskRegister(Map<String, Object> enableTaskCore) throws Exception{

        //获得包注解
        AnnotationAttributes [] annotationAttributesArray = (AnnotationAttributes[]) enableTaskCore.get("baseTaskPackage");

        //创建注册加载器
        RegisterLoading registerLoading = new RegisterLoading();
        boolean exits = false;

        try {
            //注册注解内容
            registerLoading.run(annotationAttributesArray);
            exits = true;
        } catch (Exception e) {
            logger.debug("annotation no task available");
        }
        try {
            //判断配置类是否存在
            if(taskManageConfigBean == null) {
                logger.warn( "TMC Configuration is not found. init [Task Register]");
                taskManageConfigBean = new TaskManageConfigBean();
                if (!exits) {StartStatus.warning();}
            }
            registerLoading.run(taskManageConfigBean);
            exits = true;
        } catch (Exception e) {
            logger.debug("configBean no task available");
        }
        //如果注解中没有注册到有效的值 且配置类也未找到时
        if(!exits){
            logger.warn("task not exits");
            StartStatus.warning();
        }

    }


    private void threadInit(Map<String, Object> enableTaskCore){
        Class<ThreadManageCore> threadManageCoreClass = ThreadManageCore.class;
        AnnotationAttributes annotationAttributesArray = (AnnotationAttributes) enableTaskCore.get("poolAnnotation");
        boolean configBeanVaild = taskManageConfigBean == null || taskManageConfigBean.getThreadPoolConfig() == null
                || taskManageConfigBean.getThreadPoolConfig().isEnpty();
        ThreadPoolConfig threadPoolConfig = null;
        if(configBeanVaild && !annotationAttributesArray.getBoolean("enable")){
            logger.warn("TMC Configuration is not found. init [ThreadManageCore]");
            threadPoolConfig = taskManageConfigBean.getThreadPoolConfig();
            StartStatus.warning();
        } else {
            if (annotationAttributesArray.getBoolean("enable")) {
                threadPoolConfig = new ThreadPoolConfig(annotationAttributesArray);
            }else{
                threadPoolConfig = taskManageConfigBean.getThreadPoolConfig();
            }
        }
        String name = threadManageCoreClass.getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);

        ThreadManageCore threadManageCore = new ThreadManageCore(threadPoolConfig);
        threadManageCore.init();

        defaultListableBeanFactory.registerSingleton(name, threadManageCore);
    }

    private void outLog(){
        LogoLoading logoLoading = new  LogoLoading();
        logoLoading.out();
    }

    private String[] startClassName(){
        String [] className = new String [startClass.length];

        for (int i = 0; i < startClass.length; i++) {
            className[i] = startClass[i].getName();
        }

        return className;
    }

}
