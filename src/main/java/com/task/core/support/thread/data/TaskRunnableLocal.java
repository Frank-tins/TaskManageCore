package com.task.core.support.thread.data;

import com.task.core.bean.RunTaskInfo;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.util.Audit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务组数据
 *
 * TaskRunnableLocal 实例被任务切面器所持有
 *
 * runnableCacheMap = > 管理所有任务队列的运行情况
 *
 * taskGroupCache = >  管理任务组别
 *
 * save = > taskGroupCache init = > runnableCacheMap init = > start get Cache  = > runnable 主动销毁 = > 任务组销毁
 *
 *
 * @author Frank
 */
public class TaskRunnableLocal {

    private Logger logger = LogManager.getLogger(TaskRunnableLocal.class);

    //当前线程任务 所持缓存池
    private final Map<RunnableExtend, RunnableCache> runnableCacheMap;
    //当前任务信息 所持有的线程任务实例
    private final Map<RunTaskInfo, List<RunnableExtend>> taskGroupCache;

    private final static Object lock = new Object();

    private TaskRunnableLocal() {
        runnableCacheMap = new HashMap<>();
        taskGroupCache = new HashMap<>();
        logger.debug("TMC [Thread Cache Pool] [START] ");
    }

    final String notTaskError = "Error An error occurred while allocating a task cache. The task does not exist";

    private static TaskRunnableLocal taskRunnableLocal = new TaskRunnableLocal();

    public static TaskRunnableLocal getTaskRunnableLocal(){ return taskRunnableLocal; }

    /**
     * 注册任务缓存
     *
     * @param runTaskInfo 任务信息
     *
     * @return LocalRunnableBinding 操作绑定任务所持线程的初始对象
     */
    public LocalRunnableBinding getLocalRunnableBinding(RunTaskInfo runTaskInfo){
        Audit.isNotNull(notTaskError, runTaskInfo);
        return new LocalRunnableBinding(runTaskInfo);
    }

    /**
     * 初始化 任务信息所持有的缓存
     *
     *  @see com.task.core.support.thread.data.TaskRunnableLocal#runnableCacheMap [runnableCacheMap]
     *
     *  使任务信息所指向的 线程work 获得 runnableCacheMap 的注册并开辟对应的缓存区间
     *
     * @param runnableExtend 线程的work 对象
     * @param runTaskInfo 缓存挂载值
     *
     * @return 返回当前的线程work 实例
     */
     RunnableExtend runnableInit(RunnableExtend runnableExtend, RunTaskInfo runTaskInfo, Map<String, Object> cache){
        Audit.isNotNull(notTaskError, runnableExtend);
        synchronized (lock){
            runnableCacheMap.put(runnableExtend, new RunnableCacheImpl(runTaskInfo, cache));
        }
        return runnableExtend;
    }

    /**
     * 获取缓存信息
     *
     * @see com.task.core.support.thread.data.TaskRunnableLocal#runnableCacheMap [runnableCacheMap]
     *
     * 通过[runnableCacheMap] 获取当前带入的线程work对象所指向的缓存
     *
     * @exception IllegalArgumentException 当当前线程work 不存在 [runnableCacheMap] 时抛出 'Misdirect to cache key.'
     *
     * @param runnableExtend 线程的work 对象
     *
     * @return 返回当前的缓存区间
     */
    public RunnableCache cache(RunnableExtend runnableExtend){
        Audit.isNotNull(notTaskError, runnableExtend);
        if(!runnableCacheMap.containsKey(runnableExtend)) throw  new IllegalArgumentException("Misdirect to cache key.");
        return runnableCacheMap.get(runnableExtend);
    }

    /**
     * 移除 线程work 所持有的缓存区间
     *
     * @see com.task.core.support.thread.data.TaskRunnableLocal#runnableCacheMap [runnableCacheMap]
     *
     * 这会使 [runnableCacheMap] 中的缓存实例被移除 同时删除挂载值
     *
     * @param runnableExtend
     * @return
     */
    public RunnableExtend cleanRunnableCache(RunnableExtend runnableExtend){
        Audit.isNotNull(notTaskError, runnableExtend);
        RunnableCache runnableCache = runnableCacheMap.remove(runnableExtend);
        if(runnableCache.getAllCache().size() == 0) throw new IllegalArgumentException("cache is null.");
        runnableExtend.putRunResultsTheCache(runnableCache.getAllCache());
        return runnableExtend;
    }

    /**
     * 移除 任务信息所持有的线程work 实例
     *
     * @see com.task.core.support.thread.data.TaskRunnableLocal#taskGroupCache [taskGroupCache]
     *
     * 这会使 [taskGroupCache] 中的缓存实例被移除 同时删除挂载值
     *
     * @param runTaskInfo
     * @return
     */
    public List<RunnableExtend> cleanTaskCache(RunTaskInfo runTaskInfo){
        Audit.isNotNull(notTaskError, runTaskInfo);
        List<RunnableExtend> runnableExtends = taskGroupCache.get(runTaskInfo);
        taskGroupCache.remove(runTaskInfo);
        return runnableExtends;
    }

    public void cleanTaskCacheAll(RunTaskInfo runTaskInfo){
        Audit.isNotNull(notTaskError, runTaskInfo);
        List<RunnableExtend> runnableExtends = taskGroupCache.get(runTaskInfo);
        for (int i = 0; i < runnableExtends.size(); i++) {
            try {
                cleanRunnableCache(runnableExtends.get(i));
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        taskGroupCache.remove(runTaskInfo);
    }

    /**
     * 实现 RunnableCache<M> 并完全遵循结构规范
     *
     * @see com.task.core.support.thread.data.RunnableCache
     *
     */
    class RunnableCacheImpl implements RunnableCache<RunTaskInfo>{

        private final RunTaskInfo taskInfo ;

        Map<String, Object> cache = null;

        public RunnableCacheImpl(RunTaskInfo taskInfo) {
            this.taskInfo = taskInfo;
        }

        public RunnableCacheImpl(RunTaskInfo taskInfo, Map<String, Object> cache) {
            this.taskInfo = taskInfo;
            this.cache = cache;
        }

        @Override
        public void addCache(String k, Object v){
            if(cache == null) cache = new HashMap<>();
            Audit.isNotBank("Null values are not allowed as cache keys.", k);
            Audit.isNotNull("Null values are not allowed as cache values.", v);
            cache.put(k,v);
        }

        @Override
        public String[] cacheCatalog() {
            return cache.keySet().toArray(new String[0]);
        }

        @Override
        public Object getCache(String k) {
            Audit.isNotBank("Misdirect to cache key.", k);
            if(cache.containsKey(k)) return cache.get(k);
            return null;
        }

        @Override
        public <T> T getCache(String k, Class<T> cacheType) {
            Audit.isNotBank("Misdirect to cache key.", k);
            Audit.isNotNull("cacheType not null.", cacheType);
            if(!cache.containsKey(k)) return null;//不存在直接返回
            Object value = cache.get(k);
            if(value == null) return null;//为空直接返回
            if(cacheType.equals(value.getClass())) return (T)value; // 当类型等于带入类型时返回强转T类型
            else throw new ClassCastException("class not equals values type");
        }

        @Override
        public RunTaskInfo getMountTheValue() {
            return taskInfo;
        }

        @Override
        public Map<String, Object> getAllCache(){
            return this.cache == null ? new HashMap<>() : this.cache;
        }

    }

    /**
     * 任务work 初始化操作对象
     */
    public class LocalRunnableBinding{

        private RunTaskInfo k;

        private Map<RunnableExtend, Map<String, Object>> runnables;

        LocalRunnableBinding(RunTaskInfo runTaskInfo) {
            this.k = runTaskInfo;
            runnables = new HashMap(runTaskInfo.getThreadNumber());
        }

        /**
         * 绑定传入的线程work 值
         * @param runnableExtendList
         * @throws IllegalArgumentException
         */
        public List<RunnableExtend> bindData(Map<RunnableExtend, Map<String, Object>> runnableExtendList) throws  IllegalArgumentException{
            if(k.getThreadNumber() != runnableExtendList.size()) throw new IllegalArgumentException("parameter error");
            List<RunnableExtend> list = new ArrayList<>();
            runnableExtendList.forEach( (key, v) -> {
                list.add(runnableInit(key, k, v));
            });
            taskGroupCache.put(k, list);
            return list;
        }

        /**
         * 确认绑定的线程work 值
         * @throws IllegalArgumentException
         */
        public List<RunnableExtend> bindData() throws  IllegalArgumentException{
            return bindData(runnables);
        }

        /**
         * 绑定一个传入的线程work 值
         * @param runnableExtend
         * @throws IllegalArgumentException
         */
        public void bind(RunnableExtend runnableExtend, Map<String, Object> cache) throws  IllegalArgumentException{
            Audit.isNotNull("runnableExtend is not null" , runnableExtend);
            runnables.put(runnableExtend, cache);
        }

    }

}
