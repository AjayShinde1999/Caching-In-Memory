package com.caching_in_memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachingInMemoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingInMemoryApplication.class, args);
	}

}
