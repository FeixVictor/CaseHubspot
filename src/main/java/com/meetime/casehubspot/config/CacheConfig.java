package com.meetime.casehubspot.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

    @Bean
    CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("hubspotAccessToken");
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                    .expireAfterWrite(345, TimeUnit.MINUTES) // 5h45min
                    .maximumSize(1)
        );
        return cacheManager;
    }
}