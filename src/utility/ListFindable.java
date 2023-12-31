package utility;

/**
 * @author xuanbin
 */
@FunctionalInterface
public interface ListFindable<T> {
    /**
     * A function to return true if the object is considered found
     * @param item the item passed to this functional interface to determine whether this is what to find
     * @return true if the item matches what was to be found
     */
    public abstract boolean find(T item);
}
