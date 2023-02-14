package com.redisson.master;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedissonTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void client(){
        RBucket<String> bucket = redissonClient.getBucket("testkey");
        bucket.set("测试111");
        System.out.println("bucket = " + bucket.get());
    }

}
