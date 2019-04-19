package chapter3_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 14/04/19.
 */
public class Exercise5_SortStack {

    public static class StackSorter<Item extends Comparable<Item>> {

        // O(n^2) runtime, where n is the number of items in the stack
        // O(n) space
        public void sortStack(Deque<Item> stack) {
            Deque<Item> auxStack = new ArrayDeque<>();

            while (!stack.isEmpty()) {
                Item nextItem = stack.pop();

                while (!auxStack.isEmpty()
                        && nextItem.compareTo(auxStack.peek()) < 0) {
                    stack.push(auxStack.pop());
                }
                auxStack.push(nextItem);
            }

            while (!auxStack.isEmpty()) {
                stack.push(auxStack.pop());
            }
        }
    }

    public static void main(String[] args) {
        StackSorter<Integer> stackSorter = new StackSorter<>();

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(2);
        stack.push(99);
        stack.push(6);
        stack.push(1);
        stack.push(11);
        stack.push(10);
        stack.push(4);
        stack.push(2);
        stack.push(6);
        stack.push(5);

        stackSorter.sortStack(stack);

        StringJoiner items = new StringJoiner(" ");
        for (Integer item : stack) {
            items.add(String.valueOf(item));
        }
        System.out.println("Sorted stack: " + items);
        System.out.println("Expected: 1 2 2 4 5 6 6 10 11 99");
    }

}