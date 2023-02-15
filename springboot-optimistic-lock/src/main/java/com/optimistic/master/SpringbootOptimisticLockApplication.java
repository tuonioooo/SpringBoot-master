package com.optimistic.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.optimistic.master.*"})
public class SpringbootOptimisticLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootOptimisticLockApplication.class, args);
	}
}
