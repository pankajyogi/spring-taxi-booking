package com.github.pankajyogi.spring.taxibooking.internal.cache.impl;

import com.github.pankajyogi.spring.taxibooking.internal.cache.Cache;

import java.util.*;

/**
 * DefaultCacheImpl is an implementation of the Cache interface.
 * It provides basic caching mechanisms using a HashMap to store key-value pairs.
 *
 * @param <K> the type of keys used to identify cached values
 * @param <V> the type of values that are being cached
 */
public class DefaultCacheImpl<K, V> implements Cache<K, V> {

    private final Map<K, V> map;

    public DefaultCacheImpl() {
        map = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public List<V> values() {
        List<V> values = Collections.emptyList();
        if (map.values() != null) {
            values = new ArrayList<>(map.values());
        }
        return values;
    }

    @Override
    public Set<K> keys() {
        return map.keySet();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }
}
