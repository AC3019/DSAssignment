package adt;

/**
 * @author hanyue1014
 */
public interface ListInterface<T> {
    public abstract T get(int index) throws IndexOutOfBoundsException;
    public abstract ListInterface<T> insert(T item);
    public abstract ListInterface<T> insert(int index, T item);
    public abstract T remove(int index) throws IndexOutOfBoundsException;
    public abstract boolean isEmpty();
    public abstract boolean isFull();
    public abstract void clear();
}
