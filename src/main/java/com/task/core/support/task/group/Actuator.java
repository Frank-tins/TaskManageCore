package com.task.core.support.task.group;

import com.task.core.util.AopTargetUtils;
import com.task.core.util.Audit;
import com.task.core.util.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * Actuator
 *
 * @author Frank
 */
public class Actuator {

    private Logger logger = LogManager.getLogger(Actuator.class);

    private Method method;

    private Object target;

    private Object [] args;

    private Class baseClass;

    Actuator(Method method, Object target, Object[] args) {
        this.method = method;
        this.target = target;
        this.args = args;
        init(method);
    }

    private void init(Method method) {

        this.baseClass = method.getDeclaringClass();

        if(target == null) {
            Object springTarget = SpringUtil.getBean(this.baseClass);
            Object target = AopTargetUtils.getTarget(springTarget);
            this.target = target;
        }
    }

    public Object invoke()throws Throwable{

        String unenforceableMethods = "Unenforceable methods.";

        Audit.isNotNull(unenforceableMethods, method, target);

        Object rel = method.invoke(target, args);

        return rel;

    }

    public Object invoke(Object [] args)throws Throwable{

        String unenforceableMethods = "Unenforceable methods.";

        Audit.isNotNull(unenforceableMethods, method, target);

        Object rel = method.invoke(target, args);

        return rel;

    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(Class baseClass) {
        this.baseClass = baseClass;
    }
}
