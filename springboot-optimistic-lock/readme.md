# springboot-optimistic-lock 

本示例，主要是实战 并发场景中秒杀扣减库存的解决的几种方式

### 基于缓存的扣减库存并发实现

- Redisson
  - 用Redisson的原子操作和加锁的方式 减库存，防止并发操作 详见 `GoodsInfoService->方法：updateGoodsAmount3`
- Redis
  - 用Redis的原子操作减库存，防止并发操作 详见 `GoodsInfoService->方法：updateGoodsAmount4`
- memcache
  
> 总结：  
> 此种方式实现并发控制，效率高，需要额外的写一个数据同步任务到数据库中

### 基于数据库乐观锁的扣减库并发实现

- 通过版本号实现

  ```
  update t_goods_info
  set
  	amout = amout-#{buys}, version=version+1
  where
      code = #{code} and version=#{version}
  ```
详见 `GoodsInfoService->方法：updateGoodsAmount1`
- 通过状态控制

```
update t_goods_info
set
	amout = amout-#{buys}
where
    code = #{code} and amout-#{buys} >=0
```
详见 `GoodsInfoService->方法：updateGoodsAmount2`

> 总结：  
> 数据库乐观锁实现的优点就是简单、搞笑，缺点就是并发性能低

# 代码示例

github：https://github.com/tuonioooo/Springboot-master/tree/master/springboot-optimistic-lock

