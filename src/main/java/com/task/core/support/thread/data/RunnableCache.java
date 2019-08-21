package com.task.core.support.thread.data;

import java.util.Map;

/**
 * 缓存结构
 * @param <M> info
 */
public interface RunnableCache<M> {

    /**
     * 追加缓存
     * @param k
     * @param v
     */
    void addCache(String k, Object v);

    /**
     * 获得缓存目录 [不包含挂载值]
     * @return
     */
    String[] cacheCatalog ();

    /**
     * 获取缓存值
     * @param k
     * @return
     */
    Object getCache(String k);

    /**
     * 获得泛型缓存值
     * @param k
     * @param cacheType
     * @param <T>
     * @return
     */
    <T> T getCache(String k, Class<T> cacheType);

    /**
     * 获得缓存挂载的core 值
     * @return
     */
    M getMountTheValue();


    Map<String, Object> getAllCache();
}
