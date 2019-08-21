package com.task.core.support.thread.base;

import java.util.Map;

public interface RunnableExtend extends Runnable {

    @Override
    default void run(){
        prepare();
        execute();
        clean();
        over();
    };

    void over();

    void clean();

    void prepare();

    void execute();

    void putRunResultsTheCache(Map allCache);

    void getRunResultsTheCache();
}
