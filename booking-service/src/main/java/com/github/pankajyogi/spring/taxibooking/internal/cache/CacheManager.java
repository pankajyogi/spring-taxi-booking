package com.github.pankajyogi.spring.taxibooking.internal.cache;


/**
 * CacheManager is an interface responsible for managing cache instances.
 * It provides methods to retrieve and remove caches by their names.
 */
public interface CacheManager {

    /**
     * Retrieves a named cache instance from the CacheManager. If cache does not exist, then create new and return.
     *
     * @param name the name of the cache to retrieve
     * @param <K>  the type of keys maintained by the cache
     * @param <V>  the type of mapped values
     * @return the cache instance corresponding to the specified name
     */
    <K, V> Cache<K, V> getCache(String name);

    /**
     * Removes a cache instance identified by its name from the CacheManager.
     *
     * @param name the name of the cache to be removed
     */
    void removeCache(String name);

}
