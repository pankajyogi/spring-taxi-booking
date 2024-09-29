package com.github.pankajyogi.spring.taxibooking.internal.cache.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCacheImplTest {

    @Test
    public void testPut_WhenValueIsNull() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey", null);
        assertNull(defaultCache.get("TestKey"));
    }

    @Test
    public void testPut_WhenKeyIsNull() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put(null, 100);
        assertEquals(Integer.valueOf(100), defaultCache.get(null));
    }

    @Test
    public void testPut_WhenKeyAndValueAreNotNull() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey", 100);
        assertEquals(Integer.valueOf(100), defaultCache.get("TestKey"));
    }

    @Test
    public void testPut_OverwritesExistingValue() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey", 100);
        defaultCache.put("TestKey", 200);
        assertEquals(Integer.valueOf(200), defaultCache.get("TestKey"));
    }

    @Test
    public void testValues_WhenCacheIsEmpty() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        assertTrue(defaultCache.values().isEmpty());
    }

    @Test
    public void testValues_WhenCacheHasValues() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey1", 100);
        defaultCache.put("TestKey2", 200);
        assertEquals(2, defaultCache.values().size());
        assertTrue(defaultCache.values().contains(100));
        assertTrue(defaultCache.values().contains(200));
    }

    @Test
    public void testKeys_WhenCacheIsEmpty() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        assertTrue(defaultCache.keys().isEmpty());
    }

    @Test
    public void testKeys_WhenCacheHasValues() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey1", 100);
        defaultCache.put("TestKey2", 200);
        assertEquals(2, defaultCache.keys().size());
        assertTrue(defaultCache.keys().contains("TestKey1"));
        assertTrue(defaultCache.keys().contains("TestKey2"));
    }

    @Test
    public void testClear_WhenCacheIsEmpty() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.clear();
        assertTrue(defaultCache.values().isEmpty());
        assertTrue(defaultCache.keys().isEmpty());
    }

    @Test
    public void testClear_WhenCacheIsNotEmpty() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey1", 100);
        defaultCache.put("TestKey2", 200);
        defaultCache.clear();
        assertTrue(defaultCache.values().isEmpty());
        assertTrue(defaultCache.keys().isEmpty());
    }

    @Test
    public void testRemove_WhenKeyIsNull() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put(null, 100);
        defaultCache.remove(null);
        assertNull(defaultCache.get(null));
    }

    @Test
    public void testRemove_WhenKeyIsNotNullAndExistsInCache() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.put("TestKey", 100);
        defaultCache.remove("TestKey");
        assertNull(defaultCache.get("TestKey"));
    }

    @Test
    public void testRemove_WhenKeyIsNotNullAndNotExistsInCache() {
        DefaultCacheImpl<String, Integer> defaultCache = new DefaultCacheImpl<>();
        defaultCache.remove("TestKey");
        assertNull(defaultCache.get("TestKey"));
    }
}
