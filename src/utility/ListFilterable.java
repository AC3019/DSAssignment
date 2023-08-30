package utility;

@FunctionalInterface
/**
 * Filterable executes the function `apply` to determine whether the passed in item should be included in the final filtered result, and is normally used in specific class's `filter` method
 * @author xuanbin
 */
public interface ListFilterable<T> {
    
    /**
     * apply accepts an item of any type
     * @param item
     * @return true if the item passes the filter condition, false if it should be filtered out
     */
    public abstract boolean apply(T item);

}
