package adt;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

import utility.ListFilterable;
import utility.Findable;
import utility.ListMappable;

/**
 * There will be no nulls in between elements at all times, therefore inserting items at very far to the end of the array is not possible
 * Optimal for memory (no space wasted to store null in between the elements)
 * @author xuanbin
 */
@SuppressWarnings("unchecked") // casting Object[] -> T[] will be complained by compiler as it can't guarantee type safety, surpress it abo noisy
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
    
    /**
     * Determines whether the underlying array is full
     * @return true if the underlying array is full, false otherwise
     */
    public boolean isFull() {
        return this.numberOfEntries == this.arr.length;
    }

    @Override
    public void clear() {
        this.numberOfEntries = 0;
        this.arr = (T[]) (new Object[this.customCap]); // create a new array with the same cap they wanted when creating
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= numberOfEntries) throw new IndexOutOfBoundsException(index);

        return this.arr[index];
    }

    @Override
    public boolean isEmpty() {
        return this.numberOfEntries == 0;
    }

    @Override
    public T removeFront() {
        return this.remove(0);
    }

    @Override
    public T removeBack() {
        return this.remove(this.getNumberOfEntries() - 1);
    }


    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index >= this.numberOfEntries) throw new IndexOutOfBoundsException(index);
        T res = this.arr[index];
        this.arr[index] = null;
        this.rearrangeArray(); // this handles recalculating numberOfEntries
        return res;
    }

    @Override
    public ListInterface<T> insert(T item) {
        if (this.isFull()) this.expandArray();

        this.arr[this.numberOfEntries++] = item;

        return this; // to facilitate method chaining
    }

    @Override
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
    public <U> U[] toArray(Class<U> clazz) {
        // getNumberOfEntries because we don't want the additional nulls behind the array to be included
        U[] res = (U[]) Array.newInstance(clazz, this.getNumberOfEntries());
        System.arraycopy(this.arr, 0, res, 0, this.getNumberOfEntries());
        return res;
    }

    @Override
    public ArrayList<T> filter(ListFilterable<T> f) {
        ArrayList<T> resList = new ArrayList<>();

        // loop over this instead of the underlying array so nulls wont be included
        for (T item: this) {
            if (f.apply(item))
                resList.insert(item);
        }

        return resList;
    }

    @Override
    public <U> ArrayList<U> map(ListMappable<T, U> m) {
        ArrayList<U> res = new ArrayList<>();

        for (T item: this) {
            res.insert(m.map(item));
        }

        return res;
    }

    @Override
    public int indexOf(Findable<T> f) {
        for (int i = 0; i < this.getNumberOfEntries(); i++) {
            if (f.find(this.get(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int indexOf(T obj) {
        return this.indexOf((item) -> item.equals(obj));
    }

    @Override
    public boolean contains(Findable<T> f) {
        return this.indexOf(f) != -1;
    }

    @Override
    public boolean contains(T obj) {
        return this.indexOf(obj) != -1;
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
            return currPos < ArrayList.this.numberOfEntries;
        }
    
        @Override
        public T next() {
            return ArrayList.this.get(this.currPos++);
        }

    }

    @Override
    public String toString() {
        return "ArrayList [arr=" + Arrays.toString(arr) + ", numberOfEntries=" + numberOfEntries + 
            ", customCap=" + customCap + "]";
    }

}
