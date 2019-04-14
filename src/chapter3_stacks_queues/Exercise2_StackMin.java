package chapter3_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 13/04/19.
 */
public class Exercise2_StackMin {

    public static class StackMin<Item extends Comparable<Item>> {
        private Deque<Item> stack;
        private Deque<Item> minValuesStack;

        StackMin() {
            stack = new ArrayDeque<>();
            minValuesStack = new ArrayDeque<>();
        }

        // O(1) runtime
        public void push(Item item) {
            stack.push(item);

            if (minValuesStack.isEmpty()
                    || item.compareTo(minValuesStack.peek()) <= 0) {
                minValuesStack.push(item);
            }
        }

        // O(1) runtime
        public Item pop() {
            Item item = stack.pop();

            if (item.equals(minValuesStack.peek())) {
                minValuesStack.pop();
            }

            return item;
        }

        // O(1) runtime
        public Item peek() {
            return stack.peek();
        }

        // O(1) runtime
        public Item min() {
            return minValuesStack.peek();
        }

        // O(1) runtime
        public boolean isEmpty() {
            return stack.isEmpty();
        }
    }

    public static void main(String[] args) {
        StackMin<Integer> stackMin = new StackMin<>();

        // Test isEmpty
        System.out.println("IsEmpty: " + stackMin.isEmpty());
        System.out.println("Expected: true");

        // Test push and min
        stackMin.push(10);

        System.out.println("\nMin: " + stackMin.min());
        System.out.println("Expected: 10");

        stackMin.push(5);

        System.out.println("\nMin: " + stackMin.min());
        System.out.println("Expected: 5");

        stackMin.push(7);
        stackMin.push(3);
        stackMin.push(15);

        System.out.println("\nMin: " + stackMin.min());
        System.out.println("Expected: 3");

        // Test pop
        stackMin.pop();
        System.out.println("\nMin: " + stackMin.min());
        System.out.println("Expected: 3");

        stackMin.pop();
        System.out.println("\nMin: " + stackMin.min());
        System.out.println("Expected: 5");

        System.out.println("\nIsEmpty: " + stackMin.isEmpty());
        System.out.println("Expected: false");

        // Test peek
        System.out.println("\nPeek: " + stackMin.peek());
        System.out.println("Expected: 7");
    }

}
