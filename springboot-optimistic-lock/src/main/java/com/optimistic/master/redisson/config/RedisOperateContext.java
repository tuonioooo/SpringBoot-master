package com.optimistic.master.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p> 业务逻辑中使用的Redis 操作集中使用模块 上下文 </p>
 *
 * @author daizhao  2018/6/4 16:41
 */
@Configuration
public class RedisOperateContext {

    /**
     * 默认后台 主缓存库
     * @param config
     * @return
     */
    @Bean
    @Primary
    public RedissonClient redissonClient(@Autowired Config config) {
        return Redisson.create(config);
    }

}
