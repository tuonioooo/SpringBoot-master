package com.optimistic.master.redisson.service.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>使用 redisson 进行关于分布式锁相关</p>
 *
 * @author daizhao  2018/1/25 12:15
 */
@Component
@Slf4j
public class RedissonDistributedLockUtils {

    @Autowired
    private RedissonClient client;

    //region fair lock

    /**
     * <p>尝试获取分布式锁 fair lock </p>
     *
     * @param lock 对应的字符串 lock
     * @author daizhao  2018/5/5 10:37
     */
    public boolean tryGetDistributedFairLock(String lock) {
        RLock fairLock = client.getFairLock(lock);
        boolean locked = fairLock.isLocked();
        if (locked) {
            return false;
        } else {
            fairLock.lock();
            return true;
        }
    }

    /**
     * <p>释放分布式锁 fair lock </p>
     *
     * @param lock 对应的字符串 lock
     * @author daizhao  2018/5/5 10:37
     */
    public void releaseDistributedFairLock(String lock) {
        RLock fairLock = client.getFairLock(lock);
        boolean locked = fairLock.isLocked();
        if (locked && fairLock.isHeldByCurrentThread()) {
            fairLock.unlock();
        }
    }
    //endregion


}
