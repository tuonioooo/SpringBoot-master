package com.optimistic.master.redisson.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * <p> 扣减库存redisson业务接口 </p>
 *
 * @author: daizhao 14:52 2023/2/15
 */
@Service("goodsInfoRedissonService")
@RequiredArgsConstructor
public class GoodsInfoRedissonService {

    private final RedissonClient client;

    /**
     * 扣减库存数量操作
     * @param code  订单编号
     * @param buys  扣减数量
     * @return
     */
    public Long incrBy(String code, int buys){
        RAtomicLong atomicLong = client.getAtomicLong(code);
        return atomicLong.addAndGet(buys);
    }

    public void addItem(String key, int value) {
        RBucket<Integer> bucket = client.getBucket(key);
        bucket.set(value);
    }

    public Long get(String code){
        RAtomicLong atomicLong = client.getAtomicLong(code);
        return atomicLong.get();
    }
}
