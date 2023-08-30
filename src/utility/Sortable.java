package utility;

/**
 * To be used in sort functions
 */
@FunctionalInterface
/**
 * Compares two objects, if returned is a positive integer or 0, `a` will be placed in front of `b`, if returned is negative integer, `b` will be placed in front of `a`
 * @param a
 * @param b
 * @return
 */
public interface Sortable<T> {
    public abstract int order(T a, T b);
}
