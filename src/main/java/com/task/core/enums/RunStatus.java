package com.task.core.enums;

import java.util.Date;

public class RunStatus {

    private Status status ;
    private String message;
    private Date runTime;
    private Object executeResult;
    private Object executeData;

    public RunStatus(Status status, String message, Object executeResult, Object executeData) {
        this.status = status;
        this.message = message;
        this.executeResult = executeResult;
        this.executeData = executeData;
        this.runTime = new Date();
    }

    public static RunStatus ACCOMPLISH(Object executeResult, Object executeData){
        return new RunStatus(Status.ACCOMPLISH, "", executeResult, executeData);
    }

    public static RunStatus MALFUNCTION(Object executeResult, String message){
        return new RunStatus(Status.MALFUNCTION, message, executeResult, null);
    }

    /**
     * 运行结果
     */

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public Date getRunTime() {
        return runTime;
    }

    public Object getExecuteResult() {
        return executeResult;
    }

    public Object getExecuteData() {
        return executeData;
    }

    public boolean equals(Status status) {
        return this.status == status;
    }
}
