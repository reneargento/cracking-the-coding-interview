package chapter3_stacks_queues;

import java.util.*;

/**
 * Created by Rene Argento on 13/04/19.
 */
public class Exercise3_StackOfPlates {

    private static class Stack<Item> {
        private static class Node<Item> {
            Item item;
            Node<Item> below;
            Node<Item> above;

            Node (Item item) {
                this.item = item;
            }
        }

        private Node<Item> top;
        private Node<Item> bottom;
        private int size;
        private int capacity;

        Stack(int capacity) {
            this.capacity = capacity;
        }

        // O(1) runtime
        public void push(Item item) {
            if (isFull()) {
                throw new RuntimeException("Stack is full");
            }

            Node<Item> newNode = new Node<>(item);
            newNode.below = top;

            if (top != null) {
                top.above = newNode;
            }
            if (bottom == null) {
                bottom = newNode;
            }
            top = newNode;
            size++;
        }

        // O(1) runtime
        public Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            Item item = top.item;

            top = top.below;
            if (top != null) {
                top.above = null;
            } else {
                bottom = null;
            }

            size--;
            return item;
        }

        // O(1) runtime
        public Item peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            return top.item;
        }

        // O(1) runtime
        public Item popBottom() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }

            Item item = bottom.item;
            bottom = bottom.above;

            if (bottom != null) {
                bottom.below = null;
            }

            size--;
            return item;
        }

        // O(1) runtime
        public boolean isEmpty() {
            return size == 0;
        }

        // O(1) runtime
        public boolean isFull() {
            return size == capacity;
        }
    }

    public static class SetOfStacks<Item> {
        private List<Stack<Item>> stacks;
        private int size;
        private int stacksCapacity;

        SetOfStacks(int stacksCapacity) {
            stacks = new ArrayList<>();
            this.stacksCapacity = stacksCapacity;
        }

        // O(1) runtime
        public void push(Item item) {
            size++;
            if (stacks.size() == 0
                    || getLastSubstack().isFull()) {
                Stack<Item> stack = new Stack<>(stacksCapacity);
                stack.push(item);
                stacks.add(stack);
                return;
            }

            getLastSubstack().push(item);
        }

        // O(1) runtime
        public Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            Item item = getLastSubstack().pop();

            if (getLastSubstack().isEmpty()) {
                removeLastSubstack();
            }

            size--;
            return item;
        }

        // O(1) runtime
        public Item peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            return getLastSubstack().peek();
        }

        // O(1) runtime
        public boolean isEmpty() {
            return size == 0;
        }

        private Stack<Item> getLastSubstack() {
            return stacks.get(stacks.size() - 1);
        }

        private void removeLastSubstack() {
            stacks.remove(stacks.size() - 1);
        }

        // Follow up question
        // O(s) runtime, where s is the number of substacks
        public Item popAt(int index) {
            if (index >= stacks.size()) {
                throw new IllegalArgumentException("Invalid index");
            }

            Item item = stacks.get(index).pop();
            if (index == stacks.size() - 1
                    && getLastSubstack().isEmpty()) {
                removeLastSubstack();
            }

            for (int stack = index + 1; stack < stacks.size(); stack++) {
                Stack<Item> substack = stacks.get(stack);
                Item bottomItem = substack.popBottom();

                if (stack == stacks.size() - 1
                        && substack.isEmpty()) {
                    removeLastSubstack();
                }

                stacks.get(stack - 1).push(bottomItem);
            }

            size--;
            return item;
        }
    }

    public static void main(String[] args) {
        SetOfStacks<Integer> setOfStacks = new SetOfStacks<>(3);
        initialTests(setOfStacks);
        popAtTests(setOfStacks);
    }

    private static void initialTests(SetOfStacks<Integer> setOfStacks) {
        System.out.println("Stacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 0");

        System.out.println("\nIs empty: " + setOfStacks.isEmpty());
        System.out.println("Expected: true");

        setOfStacks.push(3);
        System.out.println("\nPeek: " + setOfStacks.peek());
        System.out.println("Expected: 3");

        System.out.println("\nStacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 1");

        System.out.println("\nIs empty: " + setOfStacks.isEmpty());
        System.out.println("Expected: false");

        setOfStacks.push(4);
        setOfStacks.push(5);

        System.out.println("\nStacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 1");

        setOfStacks.push(6);

        System.out.println("\nStacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 2");

        System.out.println("\nPop: " + setOfStacks.pop());
        System.out.println("Expected: 6");

        System.out.println("\nStacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 1");
    }

    private static void popAtTests(SetOfStacks<Integer> setOfStacks) {
        // Substack 2
        setOfStacks.push(6);
        setOfStacks.push(7);
        setOfStacks.push(8);

        // Substack 3
        setOfStacks.push(9);
        setOfStacks.push(10);
        setOfStacks.push(11);

        System.out.println("\nTotal size: " + setOfStacks.size);
        System.out.println("Expected: 9");

        System.out.println("\nPop at substack 1: " + setOfStacks.popAt(0));
        System.out.println("Expected: 5");

        System.out.println("\nPop at substack 1: " + setOfStacks.popAt(0));
        System.out.println("Expected: 6");

        System.out.println("\nStacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 3");

        System.out.println("\nPop at substack 2: " + setOfStacks.popAt(1));
        System.out.println("Expected: 10");

        System.out.println("\nStacks count: " + setOfStacks.stacks.size());
        System.out.println("Expected: 2");

        System.out.println("\nPop at substack 1: " + setOfStacks.popAt(0));
        System.out.println("Expected: 7");

        System.out.println("\nPop at substack 2: " + setOfStacks.popAt(1));
        System.out.println("Expected: 11");

        System.out.println("\nTotal size: " + setOfStacks.size);
        System.out.println("Expected: 4");
    }

}
