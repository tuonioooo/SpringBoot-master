package com.optimistic.master.service;

import com.optimistic.master.entity.GoodsInfoEntity;
import com.optimistic.master.mapper.GoodsInfoMapper;
import com.optimistic.master.redisson.service.GoodsInfoRedissonService;
import com.optimistic.master.utils.MemcachedUtils;
import com.optimistic.master.utils.RedisTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p> 业务接口 </p>
 *
 * @author daizhao 2023/02/14 22:25
 */
@Service("goodsInfoService")
@RequiredArgsConstructor
@Slf4j
public class GoodsInfoService {

    private final GoodsInfoMapper goodsInfoMapper;

    private final GoodsInfoRedissonService goodsInfoRedissonService;

    private final RedisTemplateUtil redisTemplateUtil;

    private final RedissonClient redissonClient;

    private static MemcachedClient client = MemcachedUtils.getClient();

    /**
     * 更新库存数量(不加锁的方法实现——错误演示，仅仅是演示)
     * @param code
     * @param buys
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAmount(String code, int buys){
        //获取商品库存对象
        GoodsInfoEntity goodsInfoEntity = get(code);
        //获取库存数量
        Integer amount = goodsInfoEntity.getAmount();
        //如果库存不够直接返回
        if(amount < buys){
            return false;
        }
        //不加锁直接访问数据
        return goodsInfoMapper.updateAmountByProvider(code, buys) > 0 ? true: false;
    }

    /**
     *秒杀服务，修改库存（基于数据库乐观锁的版本号实现并发控制——正确演示）
     */
    public boolean updateGoodsAmount1(String code, int buys){
        //获取商品库存对象
        GoodsInfoEntity goodsInfoEntity = get(code);
        //获取库存数量
        Integer amount = goodsInfoEntity.getAmount();
        //如果库存不够直接返回
        if(amount < buys){
            return false;
        }
        //获取版本号
        Integer version = goodsInfoEntity.getVersion();
        int result = goodsInfoMapper.updateByVersion(code, buys, version);
        if(result > 0) return true;

        //如果更新失败，当前线程休眠，措峰执行
        waitForLock();
        //递归调用本身
        return updateGoodsAmount1(code, buys);
    }

    /**
     *秒杀服务，修改库存（基于数据库乐观锁的状态实现并发控制——正确演示）
     */
    public boolean updateGoodsAmount2(String code, int buys){
        //获取商品库存对象
        GoodsInfoEntity goodsInfoEntity = get(code);
        //获取库存数量
        Integer amount = goodsInfoEntity.getAmount();
        //如果库存不够直接返回
        if(amount < buys){
            return false;
        }
        int result = goodsInfoMapper.updateByStatus(code, buys);
        if(result > 0) return true;
        //如果更新失败，当前线程休眠，措峰执行
        waitForLock();
        //递归调用本身
        return updateGoodsAmount2(code, buys);
    }

    /**
     *秒杀服务，修改库存（基于Redisson实现并发控制——正确演示）
     */
    public boolean updateGoodsAmount3(String code, int buys){
        //用Redisson的原子操作和加锁的方式 减库存，防止并发操作
        RLock rLock = redissonClient.getLock("-code-");
        try{
            rLock.lock(1, TimeUnit.SECONDS);//加锁 ，以及锁的过期时间
            Long stock = goodsInfoRedissonService.incrBy(code, -buys);
            if(stock == null || stock < 0){
                //还原库存里的库存
                goodsInfoRedissonService.incrBy(code, +buys);
                return false;
            }
        }finally {
            rLock.unlock();//解锁
        }
        return true;
    }

    /**
     *秒杀服务，修改库存（基于Redis实现并发控制——正确演示）
     */
    public boolean updateGoodsAmount4(String code, int buys){
        //用Redis的原子操作减库存，防止并发操作
        Long stock = redisTemplateUtil.incrBy(code, -buys);
        if(stock == null || stock < 0){
            //还原库存里的库存
            redisTemplateUtil.incrBy(code, +buys);
            return false;
        }
        return true;
    }

    /**
     *基于Memcache缓存实现——秒杀服务
     */
    public boolean updateGoodsAmount5(String code, int buys){
        //1.获取商品库存对象
        CASValue<Object> tgoodsinfo = client.gets(code);
        //2.获取版本号
        long version = tgoodsinfo.getCas();
        //3.获取库存数量
        Integer amout = Integer.parseInt(tgoodsinfo.getValue().toString().trim());

        //如果库存不够直接返回
        if(amout < buys){
            return false;
        }

        //使用cas更新数据时带上版本号
        CASResponse casResponse = client.cas(code, version, amout-buys);
        System.out.println("casResponse = " + casResponse);  //OK 、EXISTS
        if(casResponse.toString().equals("OK")){
            return true;
        }

        //如果更新失败，当前线程休眠，措峰执行
        waitForLock();
        //递归调用本身
        return updateGoodsAmount5(code, buys);
    }

    private void waitForLock(){
        try {
            Thread.sleep(new Random().nextInt(10)+1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询商品实体对象
     * @param code
     * @return
     */
    public GoodsInfoEntity get(String code){
        return goodsInfoMapper.selectByCode(code);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(GoodsInfoEntity goodsInfoEntity){
        goodsInfoMapper.insert(goodsInfoEntity);
    }

    @Transactional
    public void delete(Integer id) {
        goodsInfoMapper.deleteByPrimaryKey(id);
    }
}
