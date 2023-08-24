package adt;

import java.util.Arrays;
import java.util.Iterator;

@SuppressWarnings("unchecked") // casting Object[] -> T[] will be complained by compiler as it can't guarantee type safety, surpress it abo noisy
/**
 * There will be no nulls in between elements at all times, therefore inserting items at very far to the end of the array is not possible
 * Optimal for memory (no space wasted to store null in between the elements)
 */
public class ArrayList<T> implements ListInterface<T>, Iterable<T> {

    private T[] arr;
    private int numberOfEntries = 0;
    private int customCap;
    private static final int DEFAULT_CAP = 5;

    /**
     * Rearranges array such that there is no empty space between elements
     * Recalculates the number of elements as well
     */
    private void rearrangeArray() {
        this.numberOfEntries = 0;
        T[] tempArr = (T[]) (new Object[this.arr.length]);
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null)
                tempArr[this.numberOfEntries++] = this.arr[i];
        }
        this.arr = tempArr;
    }

    private void expandArray() {
        T[] tempArr = (T[]) (new Object[this.arr.length + customCap]);
        System.arraycopy(this.arr, 0, tempArr, 0, this.arr.length);
        this.arr = tempArr;
    }

    /**
     * Copies from into ArrayList's internal array, removing every null elements in between
     * @param from original array to copy from
     */
    public ArrayList(T[] from) {
        this.customCap = from.length;
        this.arr = (T[]) (new Object[this.customCap]);
        for (int i = 0; i < from.length; i++) {
            if (from[i] != null)
                this.arr[this.numberOfEntries++] = from[i];
        }
    }

    /**
     * Creates an ArrayList with the underlying array initialised to length `cap`
     * In the future when the ArrayList needs to expand, it will expand by `cap`
     * @param cap
     */
    public ArrayList(int cap) { 
        this.arr = (T[]) (new Object[cap]);
        this.customCap = cap;
    }

    public ArrayList() { this(DEFAULT_CAP); }

    // getters
    public int getNumberOfEntries() { return this.numberOfEntries; }
    public int getCustomCap() { return this.customCap; }

    // setters
    public void setCustomCap(int cap) {
        this.customCap = cap;
    }

    @Override
    /**
     * Empties the entire list
     */
    public void clear() {
        this.numberOfEntries = 0;
        this.arr = (T[]) (new Object[this.customCap]); // create a new array with the same cap they wanted when creating
    }

    @Override
    /**
     * Gets an item from the list
     * @param index The index of the item
     * @throws IndexOutOfBoundsException
     */
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= numberOfEntries) throw new IndexOutOfBoundsException(index);

        return this.arr[index];
    }

    @Override
    public boolean isEmpty() {
        return this.numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return this.numberOfEntries == this.arr.length;
    }

    @Override
    /**
     * Removes the element at `index`, shifting every element on the right to the left
     * @param index The index of the element to remove
     * @throws IndexOutOfBoundsException
     */
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index >= this.numberOfEntries) throw new IndexOutOfBoundsException(index);
        T res = this.arr[index];
        this.arr[index] = null;
        this.rearrangeArray(); // this handles recalculating numberOfEntries
        return res;
    }

    @Override
    /**
     * Inserts an item to the list
     * @param item Inserts the item to the back of the array
     * @return `this` Returns the object itself, after inserting the element to facilitate method chaining
     */
    public ListInterface<T> insert(T item) {
        if (this.isFull()) this.expandArray();

        this.arr[this.numberOfEntries++] = item;

        return this; // to facilitate method chaining
    }

    @Override
    /**
     * For this ArrayList, fixed position insertion only opens to inserting element **in between** the elements, if wanna add item to the back of ArrayList, use `insert(T item)` instead
     * @param index where to insert to
     * @param item the item to insert
     * @throws IndexOutOfBoundsException
     */
    public ListInterface<T> insert(int index, T item) throws IndexOutOfBoundsException {
        if (index > this.numberOfEntries) 
            throw new IndexOutOfBoundsException(
                String.format(
                    "Can only insert in between the elements, expected index %d to %d, got %d", 
                    0, this.numberOfEntries, index
                )
            );

        if (this.isFull()) this.expandArray();

        // add number of entries first to use as the loop later that pushes the elements to the right, save a few things as no need to loop entire array
        this.numberOfEntries++;

        // push all the elements on the right to the right
        T temp = this.arr[index];
        this.arr[index] = item;
        for (int i = index + 1; i < this.numberOfEntries; i++) {
            T temp2 = this.arr[i];
            this.arr[i] = temp;
            temp = temp2;
        }
        
        return this;
    }

    @Override
    public String toString() {
        return "ArrayList [arr=" + Arrays.toString(arr) + ", numberOfEntries=" + numberOfEntries + 
            ", customCap=" + customCap + "]";
    }

    @Override
    /**
     * Returns an iterator that allows traversing the list in one way, facilitates foreach loop
     * @return Iterator
     */
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {

        private int currPos = 0;
        
        @Override
        public boolean hasNext() {
            return currPos >= ArrayList.this.numberOfEntries;
        }
    
        @Override
        public T next() {
            return ArrayList.this.get(this.currPos++);
        }

    }
    
}
