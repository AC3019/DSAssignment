package adt;

public interface MapInterface<K extends Comparable<K>, V> {
    public abstract void put(K key, V value);
    public abstract V get(K key);
    public abstract K[] getKeys(Class<K> clazz);
    // TODO: add more functions
}
