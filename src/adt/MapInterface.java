package adt;

import utility.MapFilterable;
import utility.MapFindable;

/**
 * @author xuanbin, Neoh Soon Chee, yong
 */
public interface MapInterface<K, V> {

    /**
     * Associates the value with the key and put it into the map
     * @param key
     * @param value
     */
    public abstract void put(K key, V value);

    /**
     * Gets the value associated with the key
     * @param key
     * @return the value associated with the key
     */
    public abstract V get(K key);

    /**
     * Sets the value corresponding to `key` to `value`
     * @param key
     * @return false if the key not found
     */
    public abstract boolean set(K key, V value);

    /**
     * Removes the key value pair from the map
     * @param key
     * @return `value` the associated value of the key
     */
    public abstract V remove(K key);

    /**
     * Returns all the keys in the Map, converted into primitive arrays of clazz
     * @param clazz
     * @return
     */
    public abstract K[] getKeys(Class<K> clazz);

    /**
     * Returns all the values in the Map, converted into primitive arrays of clazz
     * @param clazz
     * @return
     */
    public abstract V[] getValues(Class<V> clazz);

    /**
     * Tests whether `key` is in the map
     * @param key
     * @return
     */
    public abstract boolean containsKey(K key);

    /**
     * Tests whether `value` is in the map
     * @param value
     * @return
     */
    public abstract boolean containsValue(V value);
    
    /**
     * Check if the map has anything that matches the criteria given by mf
     * @param mf
     * @return
     */
    public abstract boolean containsValue(MapFindable<K, V> mf);

    /**
     * Finds the key of the value (if any)
     * @param value
     * @return
     */
    public abstract K keyOf(V value);

    /**
     * Finds the key of anything that agrees to what is being found
     * @param mf
     * @return
     */
    public abstract K keyOf(MapFindable<K, V> mf);

    /**
     * Returns the size of the map (number of key value pairs)
     * @return
     */
    public abstract int size();

    /**
     * Runs every key value pair of the map, returns a new map with every key value pair that passes the filter
     * @param f functional interface with the function to apply the filter
     * @return a new map that contains the key value pairs that passes the filter
     */
    public abstract MapInterface<K, V> filter(MapFilterable<K, V> f);
}
