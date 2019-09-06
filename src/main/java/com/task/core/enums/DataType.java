package com.task.core.enums;

/**
 * 任务执行数据类型
 *
 */
public enum DataType {

    /**
     * 传入参数
     */
    PARAMETER,

    /**
     * 执行表达式
     *
     * 支持传入参数作为 获取目标数据参数
     *
     */
    EXP,

    /**
     * 不使用数据执行
     */
    NOT_DATA

}
