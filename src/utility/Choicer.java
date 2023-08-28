package utility;

@FunctionalInterface
/**
 * Used by Input.getChoice() to easily convert an object to become a Choicer, to let user select among the choices
 * @author xuanbin
 */
public interface Choicer<T> {
    /**
     * Given item, return a distinguiable characteristic of the item, so that people can pick from it
     * @param item
     * @return
     */
    public abstract String toChoiceString(T item);
}
