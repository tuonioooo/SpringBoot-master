package com.optimistic.master.redisson.dao.base;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> 与节点网关数据同步-String simple redis 项 通用数据访问层 </p>
 *
 * @author daizhao  2019-05-22 16:27
 */
@Component
@Slf4j
public class StringRedisBaseDao {

    @Autowired
    private RedissonClient client;


    /**
     * <p>添加redis 项 </p>
     *
     * @param key   redis key
     * @param value redis value
     * @author daizhao  2019/1/28 11:47
     */
    public void addItem(String key, String value) {
        RBucket<String> bucket = client.getBucket(key);
        bucket.set(value);
    }

    /**
     * <p>添加redis 项 </p>
     *
     * @param key   redis key
     * @param value redis value
     * @param expireTime 过期时间
     * @param timeUnit 单位
     * @author daizhao  2019/1/28 11:47
     */
    public void addItem(String key, String value, long expireTime, TimeUnit timeUnit) {
        RBucket<String> bucket = client.getBucket(key);
        bucket.set(value, expireTime, timeUnit);
    }

    /**
     * <p>查询 redis 项 </p>
     *
     * @param key redis key
     * @author daizhao  2019/1/28 11:47
     */
    public String getItem(String key) {
        RBucket<String> bucket = client.getBucket(key);
        return bucket.get();
    }

    /**
     * <p>批量添加redis 项 </p>
     *
     * @param map redis key-value 对
     * @author daizhao  2019/1/28 11:47
     */
    public void addItems(Map<String, String> map) {
        RBuckets buckets = client.getBuckets();
        buckets.set(map);
    }

    /**
     * <p>删除redis 项 </p>
     *
     * @param key redis key
     * @author daizhao  2019/1/28 11:47
     */
    public void delItem(String key) {
        RBucket<String> bucket = client.getBucket(key);
        bucket.delete();
    }


}
