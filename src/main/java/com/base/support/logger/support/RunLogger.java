package com.base.support.logger.support;

import com.base.support.thread.support.RunTaskInfo;

import java.util.List;

/**
 * 运行信息
 */
public class RunLogger {

    //ip
    private String ip;
    //端口
    private String port;
    //可用的任务
    private List<RunTaskInfo> runTaskInfos;
    //启用的任务
    private List<String> enableThread;

    public void setRunTaskInfos(List<RunTaskInfo> runTaskInfos) {
        this.runTaskInfos = runTaskInfos;
    }

    public void setEnableThread(List<String> enableThread) {
        this.enableThread = enableThread;
    }

    public RunLogger(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<RunTaskInfo> getRunTaskInfos() {
        return runTaskInfos;
    }

    public List<String> getEnableThread() {
        return enableThread;
    }
}