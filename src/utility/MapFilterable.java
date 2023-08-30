package utility;

@FunctionalInterface
/**
 * MapFilterable executes the function `apply` to the key value pair to determine whether the passed in item should be included in the final filtered result, and is normally used in specific class's `filter` method
 * @param K type of key
 * @param V type of value
 * @author xuanbin
 */
public interface MapFilterable<K, V> {

    public abstract boolean apply(K key, V value);
    
}
