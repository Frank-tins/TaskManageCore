package com.task.core.start;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 依赖组加载
 *
 * @author Frank
 */
@Component
public class RelyLoading {

    private Logger logger = LogManager.getLogger(RelyLoading.class);

    private final Map<String, String> RELY_CLASS = new HashMap<>();

//    private RelyExamineClassLoader relyExamineClassLoader;

    public RelyLoading(){
        initRely();
//        relyExamineClassLoader = new RelyExamineClassLoader();
    }
    private void initRely(){
        RELY_CLASS.clear();
        RELY_CLASS.put("SPRING MVC", "org.springframework.web.servlet.FrameworkServlet");
    }

    private void run(){

    }

    private void loadDependents(){
        List successArray = new ArrayList();
        List errorArray = new ArrayList();
        RELY_CLASS.entrySet().forEach( e -> {
            try {
                loadNode(e);
                successArray.add(e.getKey());
            } catch (ClassNotFoundException ex) {
                errorArray.add(e.getKey());
            }

        });
        //TODO 依赖处理后对部分插件进行初始化 如WEB BaseController
    }

    /**
     * 加载Class
     * 使用独立ClassLoader 实现非双亲委派情况下Class判别
     * 防止出现在用户自定义ClassLoader 加载的Class时 诱导系统Class加载产生问题
     * @param entry
     * @throws ClassNotFoundException
     */
    private void loadNode (Map.Entry<String, String> entry) throws ClassNotFoundException{
        try {
            //TODO 开发阶段先使用SystemClassLoader
            ClassLoader.getSystemClassLoader().loadClass(entry.getValue());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(entry.getKey());
        }
    }

}
