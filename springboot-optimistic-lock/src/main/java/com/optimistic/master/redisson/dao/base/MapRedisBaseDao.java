package com.optimistic.master.redisson.dao.base;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p> mapRedis</p>
 *
 * @author limm  2019/11/8 11:19
 */
@Component
@Slf4j
public class MapRedisBaseDao<K, T> {

    @Autowired
    private RedissonClient redissonClient;


    /**
     * <p>添加单个数据对象</p>
     *
     * @param key    key
     * @param value  值
     * @param mapKey mapKey
     * @author limm  2019/11/8 11:36
     */
    public void putObj(String key, T value, K mapKey) {
        RMap<K, T> map = redissonClient.getMap(key);
        map.put(mapKey, value);
    }

    /**
     * <p>添加单个数据对象</p>
     *
     * @param key    key
     * @param value  值
     * @param mapKey mapKey
     * @param modelClass 封装的model
     * @author limm  2019/11/8 11:36
     */
    public void putObj(String key, T value, K mapKey, Class modelClass) {
        RMap<K, T> map = redissonClient.getMap(key, new TypedJsonJacksonCodec(key.getClass(), modelClass));
        map.put(mapKey, value);
    }

    /**
     * <p>批量添加数据对象</p>
     *
     * @param key   key
     * @param value 批量的数据对象值
     * @author limm  2019/11/8 11:36
     */
    public void putObj(String key, Map<K, T> value) {
        RMap<K, T> map = redissonClient.getMap(key);
        map.putAll(value);
    }

    /**
     * <p>批量添加数据对象</p>
     *
     * @param key   key
     * @param value 批量的数据对象值
     * @param modelClass 返回封装Model
     * @author limm  2019/11/8 11:36
     */
    public void putObj(String key, Map<K, T> value, Class modelClass) {
        RMap<K, T> map = redissonClient.getMap(key, new TypedJsonJacksonCodec(key.getClass(), modelClass));
        map.putAll(value);
    }

    /**
     * <p>获取单个数据对象</p>
     *
     * @param key    key
     * @param mapKey mapKey
     * @author limm  2019/11/8 11:36
     */
    public T getObj(String key, K mapKey) {
        RMap<K, T> map = redissonClient.getMap(key);
        return map.get(mapKey);
    }

    /**
     * <p>获取单个数据对象</p>
     *
     * @param key    key
     * @param mapKey mapKey
     * @param resultClass 返回封装Model
     * @author limm  2019/11/8 11:36
     */
    public T getObj(String key, K mapKey, Class resultClass) {
        RMap<K, T> map = redissonClient.getMap(key, new TypedJsonJacksonCodec(key.getClass(), resultClass));
        return map.get(mapKey);
    }

    /**
     * <p>获取整个map数据对象</p>
     * @param key    key
     * @author limm  2019/11/8 11:36
     */
    public RMap<K, T> getObj(String key){
        RMap<K, T> map = redissonClient.getMap(key);
        return map;
    }

    /**
     * <p>获取整个map数据对象</p>
     * @param key    key
     * @param resultClass 返回封装Model
     * @author limm  2019/11/8 11:36
     */
    public RMap<K, T> getObj(String key, Class resultClass){
        RMap<K, T> map = redissonClient.getMap(key, new TypedJsonJacksonCodec(key.getClass(), resultClass));
        return map;
    }

    /**
     * <p>删除数据对象</p>
     *
     * @param key    key
     * @param mapKey mapKey
     * @author limm  2019/11/8 11:36
     */
    public void removeObj(String key, K mapKey) {
        RMap<K, T> map = redissonClient.getMap(key);
        map.remove(mapKey);
    }

    /**
     * <p>increment自增value值</p>
     * @param key       集合Key
     * @param mapKey    map的Key
     * @param delta     自增序列化值
     */
    public void addAndGet(String key, K mapKey, Number delta){
        RMap<K, T> map = redissonClient.getMap(key);
        map.addAndGet(mapKey, delta);
    }

    /**
     * <p>清空数据对象</p>
     *
     * @param key    key
     * @author limm  2019/11/8 11:36
     */
    public void clear(String key) {
        RMap<K, T> map = redissonClient.getMap(key);
        if(map.isExists()){
            map.clear();
        }
    }

    /**
     * <p>批量移除数据对象</p>
     *
     * @param key     key
     * @param mapKeys mapKeys
     * @author limm  2019/11/8 11:36
     */
    public void removeObj(String key, K[] mapKeys) {
        RMap<K, T> map = redissonClient.getMap(key);
        map.fastRemove(mapKeys);
    }
}
