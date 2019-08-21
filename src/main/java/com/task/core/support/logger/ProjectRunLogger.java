package com.task.core.support.logger;

import com.task.core.bean.RunLogger;
import com.task.core.bean.RunTaskInfo;
import com.task.core.util.Audit;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.*;

/**
 * 运行信息管理
 * @author Todd
 */
//@Component
public class ProjectRunLogger {

    private final RunLogger runLogger ;
    //所有任务
    private static Map<String, RunTaskInfo> threadAll = null;

    public void init(Map<String, RunTaskInfo> threadAll){
        this.threadAll = threadAll;
    }

    public ProjectRunLogger(){
        String ip = null;
        String port = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                    Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            port = objectNames.iterator().next().getKeyProperty("port");
        } catch (Exception e) {
            ip = "";
            port = "";
        }
        this.runLogger = new RunLogger(ip, port);
    }

    /**
     * 获得任务信息
     * @param treadId
     * @return
     * @throws IllegalAccessException
     */
    public static RunTaskInfo getRunThread(String treadId)throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunThreadLogger(treadId);
        runTaskInfo.removeLogger();
        return runTaskInfo;
    }

    /**
     * 获得任务所有信息 （克隆对象）
     * @param treadId
     * @return
     * @throws IllegalAccessException
     */
    public static RunTaskInfo getRunThreadLogger(String treadId)throws IllegalAccessException{
        return getRunThreadInfo(treadId).clone();
    }

    /**
     * 获得全局信息
     * @param enableRunTaskInfos
     * @return
     */
    public RunLogger getRunLogger(Collection<RunTaskInfo> enableRunTaskInfos){
        List<String> enables = new ArrayList<>();
        enableRunTaskInfos.forEach(runThreadInfo -> enables.add(runThreadInfo.getSgtin()));
        runLogger.setEnableThread(new ArrayList<>(enables));
        runLogger.setRunTaskInfos(new ArrayList<>(threadAll.values()));
        return runLogger;
    }

    private static RunTaskInfo getRunThreadInfo(String treadId) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = threadAll.get(treadId);
        Audit.isNotNull("The task doesn't exist", runTaskInfo);
        return runTaskInfo;
    }
}
