package com.github.pankajyogi.spring.taxibooking.internal.cache.impl;

import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCacheManagerImplTest {

    @Test
    void getCache_shouldReturnNewCache() {
        String cacheName = "testCache";
        DefaultCacheManagerImpl defaultCacheManager = new DefaultCacheManagerImpl();

        Cache<?, ?> cache = defaultCacheManager.getCache(cacheName);
        assertNotNull(cache);
        assertTrue(cache instanceof DefaultCacheImpl);
    }

    @Test
    void getCache_shouldReturnExistingCache() {
        String cacheName = "testCache";

        DefaultCacheManagerImpl defaultCacheManager = new DefaultCacheManagerImpl();

        Cache<?, ?> cache1 = defaultCacheManager.getCache(cacheName);

        Cache<?, ?> cache2 = defaultCacheManager.getCache(cacheName);

        assertSame(cache1, cache2);
    }

    @Test
    void removeCache_shouldRemoveExistingCache() {
        String cacheName = "testCache";

        DefaultCacheManagerImpl defaultCacheManager = new DefaultCacheManagerImpl();

        Cache<?,?> cache1 = defaultCacheManager.getCache(cacheName);

        defaultCacheManager.removeCache(cacheName);
        Cache<?,?> cache2 = defaultCacheManager.getCache(cacheName);

        assertNotSame(cache1, cache2);
    }

    @Test
    void removeCache_givenNoCache_shouldDoNothing() {
        String cacheName = "nonExistentCache";

        DefaultCacheManagerImpl defaultCacheManager = new DefaultCacheManagerImpl();

        assertDoesNotThrow(() ->defaultCacheManager.removeCache(cacheName));
    }
}
