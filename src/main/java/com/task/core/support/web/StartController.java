package com.task.core.support.web;

//import com.zt.thread.ThreadResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class StartController {


    private Logger logger = LogManager.getLogger(StartController.class);

    public static final int AUTO_THREAD_NUMBER = -1;

    public static final String AUTO_RUN_ID = "AUTO_RUN_ID";

    //任务标识
    private String threadId;

    //当前运行标识
    private String runId;
//
//    protected  final void init(Class type){
//        threadId = ThreadSupervise.register(type);
//    }
//
//    protected final void init(String sgtin, String name, String describe, int threadNumber, boolean enable){
//        threadId = ThreadSupervise.register(sgtin, name, describe, threadNumber, enable);
//    }
//
//    protected  final void init(String sgtin, String name, String describe){
//        threadId = ThreadSupervise.register(sgtin, name, describe);
//    }
//
//    @ExceptionHandler(value = IllegalAccessException.class)
//    public ThreadResponse taskIllegalAccessException(IllegalAccessException e){
//        return ThreadResponse.ERROR(HttpUtils.getUrl() + " " + e.getMessage());
//    }
//
//    @PostMapping("/enable")
//    public final ThreadResponse enable() throws IllegalAccessException{
//        RunThreadSupervise.enableThread(threadId);
//        return ThreadResponse.SUCCESS(HttpUtils.getUrl() + " enable");
//    }
//
//    @PostMapping("/disable")
//    public final ThreadResponse disable() throws IllegalAccessException{
//        RunThreadSupervise.disableThread(threadId);
//        return ThreadResponse.SUCCESS(HttpUtils.getUrl() + " disable");
//    }
//
//    @PostMapping("/run")
//    public final ThreadResponse run() throws IllegalAccessException{
//        if(!RunThreadSupervise.isItRunning(threadId)) {
//            task();
//            return ThreadResponse.SUCCESS("尝试唤醒数据任务");
//        }else return ThreadResponse.ERROR("当前数据任务正在运行中");
//    }
//
//    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})
//    public final ThreadResponse info() throws IllegalAccessException{
//        return ThreadResponse.SUCCESS("SUCCESS", ProjectRunLogger.getRunThread(threadId));
//    }
//
//    @RequestMapping(value = "/logger", method = {RequestMethod.GET, RequestMethod.POST})
//    public final ThreadResponse logger() throws IllegalAccessException{
//        logger.info("ip:" + HttpUtils.getRequest().getLocalAddr() + "get Logger uri: " + HttpUtils.getUrl() + "");
//        return ThreadResponse.SUCCESS("SUCCESS", ProjectRunLogger.getRunThreadLogger(threadId));
//    }
//
//    @RequestMapping(value = "/power", method = {RequestMethod.GET, RequestMethod.POST})
//    public ThreadResponse power(Integer threadNumber) throws IllegalAccessException{
//        int historyThreadNumber = RunThreadSupervise.setRunThreadNumber(threadId, threadNumber);
//        String log = HttpUtils.getUrl() +" this threadNumber [ "
//                + historyThreadNumber + " => " + threadNumber + " ]";
//        return ThreadResponse.SUCCESS(log);
//    }
//
//    public String getThreadId() {
//        return threadId;
//    }
//
//    protected int getThreadNumber(){
//        try {
//            return ProjectRunLogger.getRunThread(getThreadId()).getThreadNumber();
//        } catch (IllegalAccessException e) {
//            return ThreadSupervise.DEFAULT_THREAD_NUMBER;
//        }
//    }
//
//    public abstract void task();
//


}
