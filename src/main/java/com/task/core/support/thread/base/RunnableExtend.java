package com.task.core.support.thread.base;

import java.util.Map;

public interface RunnableExtend<R> extends Runnable {

    String PARAMETER_RUN_ID = "RUN_ID";

    String PARAMETER_PROCEEDING_JOIN_POINT = "PROCEEDING_JOIN_POINT";

    String PARAMETER_DATA = "PARAMETER_DATA";

    void over();

    void clean();

    void prepare();

    void error(Throwable throwable);

    Object execute() throws Throwable;

    void putRunResultsTheCache(Map allCache);

    R getRunResultsTheCache();
}
