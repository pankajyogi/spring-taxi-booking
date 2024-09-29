package com.github.pankajyogi.spring.taxibooking.internal.cache.impl;

import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;
import com.github.pankajyogi.spring.taxibooking.internal.cache.CacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * DefaultCacheManagerImpl is an implementation of the CacheManager interface.
 * It manages multiple cache instances identified by their names.
 * A new cache is created if it does not already exist when requested.
 */
public class DefaultCacheManagerImpl implements CacheManager {

    private final Map<String, Cache<?, ?>> caches;


    public DefaultCacheManagerImpl() {
        caches = new HashMap<>();
    }


    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        if (!caches.containsKey(name)) {
            caches.put(name, new DefaultCacheImpl<>());
        }
        return (Cache<K, V>) caches.get(name);
    }

    @Override
    public void removeCache(String name) {
        caches.remove(name);
    }
}
