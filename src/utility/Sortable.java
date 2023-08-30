package utility;

/**
 * To be used in sort functions
 * Compares two objects, if returned is a positive integer or 0, the positions of the two objects will not be swapped, if returned is negative integer, the positions will be swapped
 */
@FunctionalInterface
public interface Sortable<T> {
    public abstract int order(T a, T b);
}
