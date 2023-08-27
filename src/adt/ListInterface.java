package adt;

import utility.Filterable;
import utility.Findable;

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
     * Similar like a queue, this method removes the first element of the list
     * @return element that is removed
     */
    public abstract T removeFront();

    /**
     * Similar like a stack, this method removes the last element inserted into the list
     * If this arraylist were to be used like a stack, we recommend not using insert(index, item) else it will mess up the ordering
     * @return element that is removed
     */
    public abstract T removeBack();

    /**
     * Removes the element at `index`, shifting every element on the right to the left
     * @param index The index of the element to remove
     * @throws IndexOutOfBoundsException
     * @return element that is removed
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

    /**
     * Search for the index of the certain objectm using the `.equals()` method on the object
     * @param obj
     * @return -1 if not found, otherwise the index of the object in the list
     */
    public abstract int indexOf(T obj);

    /**
     * Applies a function to every item in the list, if the function returns true, the object is considered found and the index of the object is returned
     * @param f
     * @return -1 if not found, otherwise the index of the object in the list
     */
    public abstract int indexOf(Findable<T> f);
}
