package utility;

@FunctionalInterface
public interface MapFindable<K, V> {
    /**
     * A function to return true if the object is considered found in the map
     * @param key
     * @param value
     * @return
     */
    public abstract boolean find(K key, V value);
}
