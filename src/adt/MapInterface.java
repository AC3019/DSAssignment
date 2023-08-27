package adt;

public interface MapInterface<K extends Comparable<K>, V> {

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
    public abstract K[] getKeys(Class<K> clazz);
    // TODO: add more functions (see kua got or not)
}
