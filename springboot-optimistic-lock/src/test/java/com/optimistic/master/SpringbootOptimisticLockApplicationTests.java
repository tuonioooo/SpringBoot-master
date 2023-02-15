package com.optimistic.master;

import com.optimistic.master.entity.GoodsInfoEntity;
import com.optimistic.master.mapper.GoodsInfoMapper;
import com.optimistic.master.redisson.service.GoodsInfoRedissonService;
import com.optimistic.master.service.GoodsInfoService;
import com.optimistic.master.utils.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class SpringbootOptimisticLockApplicationTests {

	@Autowired
	private GoodsInfoMapper goodsInfoMapper;
	@Autowired
	private GoodsInfoService goodsInfoService;
	@Autowired
	private GoodsInfoRedissonService goodsInfoRedissonService;
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;

	private Integer requestNum = 10;

	private CountDownLatch countDownLatch = new CountDownLatch(requestNum);

	/**
	 * 初始化数据
	 */
	@Before
	public void init(){
		//重置数据库
		List<GoodsInfoEntity> goodsInfoEntities =  goodsInfoMapper.selectAll();
		goodsInfoEntities.stream().forEach(goodsInfoEntity -> goodsInfoService.delete(goodsInfoEntity.getId()));
		goodsInfoService.save(GoodsInfoEntity.builder().id(1).code("10001").amount(10).version(0).build());
		goodsInfoService.save(GoodsInfoEntity.builder().id(2).code("10002").amount(20).version(0).build());
		goodsInfoService.save(GoodsInfoEntity.builder().id(3).code("10003").amount(30).version(0).build());

		//重置缓存
		goodsInfoEntities.stream().forEach(goodsInfoEntity ->
				goodsInfoRedissonService.addItem(goodsInfoEntity.getCode(), goodsInfoEntity.getAmount()));
	}

	@Test
	public void contextLoads() {


	}

	/**
	 * 不加锁的错误演示，会发生库存超卖的情况
	 */
	@Test
	public void test(){
		for(int i=0; i < requestNum; i++){
			new Thread(()->{
				try {
					countDownLatch.await();
					boolean result = goodsInfoService.updateAmount("10001", 1);
					if(result){
						GoodsInfoEntity goodsInfoEntity = goodsInfoService.get("10001");
						log.info("库存扣减成功，剩余库存 = {}", goodsInfoEntity.getAmount());
					}else{
						log.info("库存扣减失败");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			countDownLatch.countDown();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 数据库乐观锁基于版本号实现并发控制——正确演示
	 */
	@Test
	public void test1(){
		for(int i=0; i < requestNum; i++){
			new Thread(()->{
				try {
					countDownLatch.await();
					boolean result = goodsInfoService.updateGoodsAmount1("10002", 20);
					if(result){
						GoodsInfoEntity goodsInfoEntity = goodsInfoService.get("10002");
						log.info("库存扣减成功，剩余库存 = {}", goodsInfoEntity.getAmount());
					}else{
						//为何扣减失败，是数据库在扣减时，版本号已经更新了，之前的版本号不存在，所以会扣减失败
						GoodsInfoEntity goodsInfoEntity = goodsInfoService.get("10002");
						log.info("库存扣减失败，剩余库存 = {}", goodsInfoEntity.getAmount());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			countDownLatch.countDown();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据库乐观锁基于状态实现并发控制——正确演示
	 */
	@Test
	public void test2(){
		for(int i=0; i < requestNum; i++){
			new Thread(()->{
				try {
					countDownLatch.await();
					boolean result = goodsInfoService.updateGoodsAmount2("10003", 30);
					if(result){
						GoodsInfoEntity goodsInfoEntity = goodsInfoService.get("10003");
						log.info("库存扣减成功，剩余库存 = {}", goodsInfoEntity.getAmount());
					}else{
						//为何扣减失败，是数据库在扣减时，版本号已经更新了，之前的版本号不存在，所以会扣减失败
						GoodsInfoEntity goodsInfoEntity = goodsInfoService.get("10003");
						log.info("库存扣减失败，剩余库存 = {}", goodsInfoEntity.getAmount());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			countDownLatch.countDown();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用Redisson实现扣减库存并发控制——正确演示
	 */
	@Test
	public void test3(){
		for(int i=0; i < requestNum; i++){
			new Thread(()->{
				try {
					countDownLatch.await();
					boolean result = goodsInfoService.updateGoodsAmount3("10003", 30);
					if(result){
						Long stock = goodsInfoRedissonService.get("10003");
						log.info("库存扣减成功，剩余库存 = {}", stock);
					}else{
						//为何扣减失败，是数据库在扣减时，库存数量为0，所以会扣减失败
						Long stock = goodsInfoRedissonService.get("10003");
						log.info("库存扣减失败，剩余库存 = {}", stock);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			countDownLatch.countDown();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用Redis实现扣减库存并发控制——正确演示
	 */
	@Test
	public void test4(){
		for(int i=0; i < requestNum; i++){
			new Thread(()->{
				try {
					countDownLatch.await();
					boolean result = goodsInfoService.updateGoodsAmount4("10003", 30);
					if(result){
						Long stock = Long.parseLong(redisTemplateUtil.get("10003").toString());
						log.info("库存扣减成功，剩余库存 = {}", stock);
					}else{
						//为何扣减失败，是数据库在扣减时，库存数量为0，所以会扣减失败
						Long stock = Long.parseLong(redisTemplateUtil.get("10003").toString());
						log.info("库存扣减失败，剩余库存 = {}", stock);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			countDownLatch.countDown();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
