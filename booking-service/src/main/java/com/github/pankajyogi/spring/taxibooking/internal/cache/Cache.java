package com.github.pankajyogi.spring.taxibooking.internal.cache;

import java.util.List;
import java.util.Set;

/**
 * The Cache interface provides the contract for a generic cache mechanism.
 * It supports basic operations such as retrieving, inserting, and listing cache elements,
 * as well as clearing the entire cache.
 *
 * @param <K> the type of keys used to identify cached values
 * @param <V> the type of values that are being cached
 */
public interface Cache<K, V> {

    /**
     * Retrieves the value associated with the specified key from the cache.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key does not exist in the cache
     */
    V get(K key);

    /**
     * Inserts or updates the value associated with the given key in the cache.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    void put(K key, V value);

    /**
     * Retrieves a list of all values currently stored in the cache.
     *
     * @return a List of values currently held in the cache.
     */
    List<V> values();

    /**
     * Retrieves a set of all keys currently stored in the cache.
     *
     * @return a Set of keys currently held in the cache
     */
    Set<K> keys();

    /**
     * Clears all entries from the cache.
     * After this operation, the cache will be empty.
     */
    void clear();


    /**
     * Removes the value associated with the specified key from the cache.
     *
     * @param key the key whose associated value is to be removed
     */
    void remove(K key);
}
