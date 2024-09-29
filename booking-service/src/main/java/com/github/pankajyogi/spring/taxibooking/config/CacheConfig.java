package com.github.pankajyogi.spring.taxibooking.config;


import com.github.pankajyogi.spring.taxibooking.internal.cache.CacheManager;
import com.github.pankajyogi.spring.taxibooking.internal.cache.impl.DefaultCacheManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // we can change the cacheManager bean implementation
        return new DefaultCacheManagerImpl();
    }

}
