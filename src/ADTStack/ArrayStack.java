package ADTStack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * This class provides an array-based implementation of the stack ADT.
 *
 * @param <T> the type of values stored in the stack
 */
public class ArrayStack<T> implements StackInterface<T> {
    /** This constant defines the default starting capacity for the stack array. */
    private static final int DEFAULT_CAPACITY = 10;

    /** This array stores all stack entries in contiguous memory. */
    private T[] elements;

    /** This index tracks the current top position in the stack array. */
    private int topIndex;

    /**
    * This constructor initializes an empty stack with the default capacity.
    */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        this.topIndex = -1;
    }

    @Override
    public void push(T newEntry) {
        ensureCapacity();
        topIndex++;
        elements[topIndex] = newEntry;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T topValue = elements[topIndex];
        elements[topIndex] = null;
        topIndex--;
        return topValue;
    }

    @Override
    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[topIndex];
    }

    @Override
    public boolean isEmpty() {
        return topIndex < 0;
    }

    @Override
    public void clear() {
        while (topIndex >= 0) {
            elements[topIndex] = null;
            topIndex--;
        }
    }

    /**
     * This method returns the number of entries currently in the stack.
     *
     * @return current number of stored entries
     */
    public int size() {
        return topIndex + 1;
    }

    /**
     * This method expands the internal array if the current array is full.
     */
    private void ensureCapacity() {
        if (topIndex == elements.length - 1) {
            // Double capacity to keep push efficient as the stack grows.
            int newCapacity = elements.length * 2;
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }
}
