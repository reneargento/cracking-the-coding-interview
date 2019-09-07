package chapter7_object_oriented_design;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 30/08/19.
 */
public class Exercise9_CircularArray {

    @SuppressWarnings("unchecked")
    public class CircularArray<T> implements Iterable<T> {
        private T[] array;
        private int head;
        private int tail;
        private int size;

        public CircularArray(int size) {
            array = (T[]) new Object[size];
        }

        // O(1) runtime
        public void add(T item) {
            if (size == array.length) {
                throw new RuntimeException("Array is full");
            }
            array[tail] = item;

            tail = (tail + 1) % array.length;
            size++;
        }

        // O(1) runtime
        public void removeLast() {
            if (size == 0) {
                throw new RuntimeException("Array is empty");
            }

            if (tail == 0) {
                tail = array.length - 1;
            } else {
                tail--;
            }

            array[tail] = null;
            size--;
        }

        // O(1) runtime
        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IllegalArgumentException("Invalid index");
            }
            int rotatedIndex = convertHeadIndex(index);
            return array[rotatedIndex];
        }

        // O(1) runtime
        public void set(int index, T item) {
            if (index < 0 || index >= size) {
                throw new IllegalArgumentException("Invalid index");
            }
            int rotatedIndex = convertHeadIndex(index);
            array[rotatedIndex] = item;
        }

        // O(1) runtime
        public void rotate(int shiftRight) {
            if (shiftRight < 0) {
                shiftRight += array.length;
            }

            head = (head + shiftRight) % array.length;
            tail = (tail + shiftRight) % array.length;
        }

        private int convertHeadIndex(int index) {
            if (index < 0) {
                index += array.length;
            }
            return (head + index) % array.length;
        }

        // O(1) runtime
        public int size() {
            return size;
        }

        public Iterator<T> iterator() {
            return new CircularArrayIterator();
        }

        private class CircularArrayIterator implements Iterator<T> {
            private int index;
            private boolean hasNext;

            public CircularArrayIterator() {
                index = head;
                hasNext = size() > 0;
            }

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                T item = array[index];

                index = (index + 1) % array.length;

                if (index == tail) {
                    hasNext = false;
                }

                return item;
            }
        }
    }

    public static void main(String[] args) {
        CircularArray<Integer> circularArray = new Exercise9_CircularArray().new CircularArray<>(5);
        circularArray.add(0);
        circularArray.add(1);
        circularArray.add(2);
        circularArray.add(3);
        circularArray.add(4);

        System.out.println("Element 0: " + circularArray.get(0) + " Expected: " + 0);
        System.out.println("Element 4: " + circularArray.get(4) + " Expected: " + 4);

        circularArray.rotate(2);

        System.out.println("Element 0 after rotation: " + circularArray.get(0) + " Expected: " + 2);
        System.out.println("Element 4 after rotation: " + circularArray.get(4) + " Expected: " + 1);

        circularArray.removeLast();

        StringJoiner elements = new StringJoiner(" ");
        for (int item : circularArray) {
            elements.add(String.valueOf(item));
        }
        System.out.println("\nAll elements after deleting 1: " + elements.toString());
        System.out.println("Expected: 2 3 4 0");
    }

}
