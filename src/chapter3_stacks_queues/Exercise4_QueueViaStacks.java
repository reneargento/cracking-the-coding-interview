package chapter3_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * Created by Rene Argento on 14/04/19.
 */
public class Exercise4_QueueViaStacks {

    public static class MyQueue<Item> {
        private Deque<Item> frontStack;
        private Deque<Item> backStack;

        public MyQueue() {
            frontStack = new ArrayDeque<>();
            backStack = new ArrayDeque<>();
        }

        // O(1) runtime
        public void add(Item item) {
            backStack.push(item);
        }

        // (1) amortized runtime, O(n) runtime
        public Item remove() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }

            if (frontStack.isEmpty()) {
                moveItemsFromBackToFront();
            }
            return frontStack.pop();
        }

        // (1) amortized runtime, O(n) runtime
        public Item peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }

            if (frontStack.isEmpty()) {
                moveItemsFromBackToFront();
            }
            return frontStack.peek();
        }

        private void moveItemsFromBackToFront() {
            System.out.println("Moving items from back to front stack");
            while (!backStack.isEmpty()) {
                frontStack.push(backStack.pop());
            }
        }

        public boolean isEmpty() {
            return frontStack.size() + backStack.size() == 0;
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>();

        System.out.println("Is empty: " + queue.isEmpty());
        System.out.println("Expected: true");

        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println("\nIs empty: " + queue.isEmpty());
        System.out.println("Expected: false\n");

        System.out.println("\nPeek: " + queue.peek());
        System.out.println("Expected: 1");

        System.out.println("\nRemove: " + queue.remove());
        System.out.println("Expected: 1");

        System.out.println("\nRemove: " + queue.remove());
        System.out.println("Expected: 2");

        System.out.println("\nRemove: " + queue.remove());
        System.out.println("Expected: 3");
    }

}
