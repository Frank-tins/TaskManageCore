package com.task.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 *
 * Spring 工具包 获取bean类
 *
 * @author Frank
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }
//    public static void addMapping() throws Exception {
//        requestMappingHandlerMapping =( RequestMappingHandlerMapping)applicationContext.getBean("requestMappingHandlerMapping");
//        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(com.example.dynamicmap.one.MappingService.class.getResource("/") + "1.jar")});
//        Class<?> myController = classLoader.loadClass("com.example.dynamicmap.MyController");
//        // 这里通过builder直接生成了mycontrooler的definition，然后注册进去
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(myController);
//        defaultListableBeanFactory.registerBeanDefinition("myc", beanDefinitionBuilder.getBeanDefinition());
//        Method method=requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods",Object.class);
//        method.setAccessible(true);
//        method.invoke(requestMappingHandlerMapping,"myc");
//    }

    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }


}
