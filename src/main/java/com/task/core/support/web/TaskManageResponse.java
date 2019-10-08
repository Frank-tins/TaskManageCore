package com.task.core.support.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frank
 */
public class TaskManageResponse<T> {

    private int code ;

    private String msg;

    private T data;

    private TaskManageResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static TaskManageResponse SUCCESS(){

        return new TaskManageResponse(200, "success", null);
    }

    public static TaskManageResponse SUCCESS(String msg){

        return new TaskManageResponse(200, "success", null);
    }

    public static TaskManageResponse SUCCESS(String msg,Object data){

        return new TaskManageResponse(200, msg, data);
    }

    public static TaskManageResponse SUCCESSDATA(Object data){

        return new TaskManageResponse(200, "success", data);
    }

    public static TaskManageResponse ERROR(String msg){
        return new TaskManageResponse(500, "success", null);
    }

    public static TaskManageResponse ERROR(String msg,Object data){
        return new TaskManageResponse(500, msg, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
