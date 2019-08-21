package com.task.core.start;

import com.task.core.util.Audit;
import com.task.core.util.SpringUtil;
import com.task.core.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 依赖组加载
 *
 * @author Frank
 */
@Component
public class RelyLoading {

    private Logger logger = LogManager.getLogger(RelyLoading.class);

    List<String> validRely(Map<String, Rely> rely){

        final List<String> valid = new LinkedList<>();

        rely.forEach( (k, y) ->  {

            String className = y.getClassPath();

            try {
                Audit.isNotBank("className is null", className);
                Class.forName(className);
                valid.add(k);
            } catch (IllegalArgumentException| NullPointerException e){
                logger.error(e.getMessage());
            } catch (ClassNotFoundException e) {
                logger.debug("[" + k + "] Rely not loading");
            }

        });

        return valid;
    }


    void expRely(Rely rely) throws RuntimeException{

        String errorMsg = "loading [" + rely.getName() + "] error. sours error : ";

        if(rely.getMethod() != null){

            try {
                rely.getMethod().invoke(null);
            } catch (Exception e){
                throw new RuntimeException(errorMsg + e.getMessage());
            }

        }else {

            String springExp = rely.getExpression();

            String beanName = springExp.substring(0, springExp.indexOf("("));

            String methodName = springExp.substring(springExp.indexOf("(") + 1, springExp.indexOf(")"));

            Object obj = SpringUtil.getBean("beanName");

            Class type = obj.getClass();

            Method method = null;

            try {
                method = type.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException( errorMsg + e.getMessage());
            }

            try {
                method.invoke(obj);
            } catch (Exception e) {
                throw new RuntimeException( errorMsg + e.getMessage());
            }

        }

    }

//
//    private void run(){
//
//    }
//
//    private void loadDependents(){
//        List successArray = new ArrayList();
//        List errorArray = new ArrayList();
//        RELY_CLASS.entrySet().forEach( e -> {
//            try {
//                loadNode(e);
//                successArray.add(e.getKey());
//            } catch (ClassNotFoundException ex) {
//                errorArray.add(e.getKey());
//            }
//
//        });
//        //TODO 依赖处理后对部分插件进行初始化 如WEB BaseController
//    }
//
//    /**
//     * 加载Class
//     * 使用独立ClassLoader 实现非双亲委派情况下Class判别
//     * 防止出现在用户自定义ClassLoader 加载的Class时 诱导系统Class加载产生问题
//     * @param entry
//     * @throws ClassNotFoundException
//     */
//    private void loadNode (Map.Entry<String, String> entry) throws ClassNotFoundException{
//        try {
//            ClassLoader.getSystemClassLoader().loadClass(entry.getValue());
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(entry.getKey());
//        }
//    }

    public class Rely{

        private String name ;

        private Method method;

        private String classPath;

        private String expression;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getClassPath() {
            return classPath;
        }

        public void setClassPath(String classPath) {
            this.classPath = classPath;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }

}
