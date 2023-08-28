package utility;

@FunctionalInterface
/**
 * Functional interface to define a function to map an item of one class into another class (or the same class)
 * @param T type of inital item
 * @param R type of returned item
 * 
 * @author xuanbin
 */
public interface Mappable<T, R> {
    public R map(T item);
}
