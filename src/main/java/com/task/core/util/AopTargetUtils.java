package com.task.core.util;

import com.task.core.excption.TargetNotFoundException;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

public class AopTargetUtils {


    /**
     * 获取 目标对象
     * @param proxy 代理对象
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy)  {

        if(!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        try {
            return getTargetObject(proxy, AopUtils.isJdkDynamicProxy(proxy));
        } catch (Exception e) {
            throw new TargetNotFoundException();
        }
    }


    private static Object getTargetObject(Object proxy, boolean b) throws Exception {
        Field h = proxy.getClass().getDeclaredField(b ? "h" : "CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }


}