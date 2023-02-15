package com.optimistic.master.redisson.dao.base;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p> 与节点网关数据同步-Long simple redis 项 通用数据访问层 </p>
 *
 * @author zhengpengfei  2019-08-16 16:27
 */
@Component
@Slf4j
public class LongRedisBaseDao {

    @Autowired
    private RedissonClient client;

    /**
     * <p> 获取 Sequence </p>
     *
     * @param key redis key
     * @return 调用结果
     * @author daizhao 2019/8/16 16:04
     */
    public Long getSequenceAtomic(String key) {
        RAtomicLong atomicLong = client.getAtomicLong(key);
        atomicLong.expire(24, TimeUnit.HOURS);
        return atomicLong.incrementAndGet();
    }
}
