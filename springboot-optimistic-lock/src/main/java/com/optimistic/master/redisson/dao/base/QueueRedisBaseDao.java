package com.optimistic.master.redisson.dao.base;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p> redis队列</p>
 *
 * @author limm  2019/11/6 15:14
 */
@Component
@Slf4j
public class QueueRedisBaseDao<T> {

    @Autowired
    RedissonClient redissonClient;

    /**
     * 非阻塞队列
     *
     * @param key   key
     * @param value value
     */
    public Boolean addQueue(String key, T value) {
        RQueue<T> blockingQueue = redissonClient.getQueue(key);
        return blockingQueue.add(value);
    }

    /**
     * del队列
     *
     * @param key key
     */
    public void delQueue(String key) {
        RQueue<T> queue = redissonClient.getQueue(key);
        if (queue.isExists()) {
            queue.delete();
        }
    }

    /**
     * <p>获取队列</p>
     *
     * @param key key
     * @return org.apache.poi.ss.formula.functions.T
     * @author limm  2019/11/6 15:38
     */
    public T getQueue(String key) {
        RQueue<T> queue = redissonClient.getQueue(key);
        return queue.poll();
    }


    /**
     * 向队列中添加数据(队列满时会抛出异常)
     *
     * @param key   key
     * @param value value
     * @author limm  2019/11/6 15:26
     */
    public Boolean addObj(String key, T value) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        return blockingQueue.add(value);
    }

    /**
     * <p>向队列中添加数据(插入方法会返回是否成功，成功则返回true。)</p>
     *
     * @param key   key
     * @param value value
     * @author limm  2019/11/6 15:26
     */
    public Boolean offerObj(String key, T value) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        return blockingQueue.offer(value);
    }

    /**
     * <p>向队列中添加数据(为空时:一直阻塞)</p>
     *
     * @param key   key
     * @param value value
     * @author limm  2019/11/6 15:28
     */
    public void putObj(String key, T value) throws InterruptedException {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        blockingQueue.put(value);
    }

    /**
     * <p>向队列中添加数据(超时时:退出)</p>
     *
     * @param time     超时时间
     * @param key      key
     * @param value    value
     * @param timeUnit 单位
     * @author limm  2019/11/6 15:32
     */
    public Boolean offerObj(String key, T value, long time, TimeUnit timeUnit) throws InterruptedException {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        return blockingQueue.offer(value, time, timeUnit);
    }

    /**
     * <p>移除数据,队列空时会抛出异常</p>
     *
     * @param key   key
     * @param value value
     * @author limm  2019/11/6 15:43
     */
    public Boolean removeObj(String key, T value) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        if (blockingQueue.size() > 0) {
            return blockingQueue.remove(value);
        }
        return true;
    }

    /**
     * <p>获取元素</p>
     *
     * @param key key
     * @return org.apache.poi.ss.formula.functions.T
     * @author limm  2019/11/6 15:46
     */
    public T pollObj(String key) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        return blockingQueue.poll();
    }

    /**
     * <p>批量新增元素</p>
     *
     * @param key  kye
     * @param list list
     * @return java.lang.Boolean
     * @author limm  2019/11/6 15:54
     */
    public Boolean addObjs(String key, List<T> list) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        return blockingQueue.addAll(list);
    }

    /**
     * <p>批量删除</p>
     *
     * @param key   key
     * @param value value
     * @return java.lang.Boolean
     * @author limm  2019/11/6 15:55
     */
    public Boolean removeObjs(String key, List<T> value) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(key);
        if (blockingQueue.size() > 0) {
            return blockingQueue.removeAll(value);
        }
        return true;
    }
}
