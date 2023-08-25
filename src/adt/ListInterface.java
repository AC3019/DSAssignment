package adt;

import utility.Filterable;

/**
 * @author hanyue1014
 */
public interface ListInterface<T> {

    /**
     * Gets an item from the list
     * @param index The index of the item
     * @throws IndexOutOfBoundsException
     */
    public abstract T get(int index) throws IndexOutOfBoundsException;

    /**
     * Inserts an item to the list
     * @param item Inserts the item to the back of the list
     * @return `this` Returns the object itself, after inserting the element to facilitate method chaining
     */
    public abstract ListInterface<T> insert(T item);

    /**
     * For this List, fixed position insertion only opens to inserting element **in between** the elements, if wanna add item to the back of List, use `insert(T item)` instead
     * @param index where to insert to
     * @param item the item to insert
     * @throws IndexOutOfBoundsException
     */
    public abstract ListInterface<T> insert(int index, T item) throws IndexOutOfBoundsException;

    /**
     * Removes the element at `index`, shifting every element on the right to the left
     * @param index The index of the element to remove
     * @throws IndexOutOfBoundsException
     */
    public abstract T remove(int index) throws IndexOutOfBoundsException;

    /**
     * Determines whether the List is empty
     * @return true if the list is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Empties the entire list
     */
    public abstract void clear();

    /**
     * Converts the list back to a java array
     * @param Class<U> the class to cast to
     * @return U[] the array containing every item in the list, except null (with the class)
     */
    public abstract <U> U[] toArray(Class<U> clazz);

    /**
     * Applies filter to every item in the list, if the item passes through the filter, it will be included in the final result
     * @param f
     * @return T[] primitive java array containing items that passes the filter
     */
    public abstract ListInterface<T> filter(Filterable<T> f);
}
