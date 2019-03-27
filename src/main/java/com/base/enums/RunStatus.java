package com.base.enums;

public class RunStatus {

    private Status status ;
    private String message;
    private long runTime;

    public RunStatus(Status status) {
        this.status = status;
    }

    public RunStatus(Status status, String message) {
        this.status = status;
        this.message = message;
        this.runTime = System.currentTimeMillis();
    }

    public static RunStatus ACCOMPLISH(){
        return new RunStatus(Status.ACCOMPLISH);
    }

    public static RunStatus MALFUNCTION(String message){
        return new RunStatus(Status.MALFUNCTION, message);
    }

    /**
     * 运行结果
     */
    public enum Status{
        //成功
        ACCOMPLISH,
        //失败
        MALFUNCTION
    }

    public String getMessage() {
        return message;
    }

    public long getRunTime() {
        return runTime;
    }

    public boolean equals(Status status) {
        return this.status == status;
    }
}
