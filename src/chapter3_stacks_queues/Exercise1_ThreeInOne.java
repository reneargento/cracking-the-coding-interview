package chapter3_stacks_queues;

import java.util.NoSuchElementException;

/**
 * Created by Rene Argento on 09/04/19.
 */
public class Exercise1_ThreeInOne {

    private static class StackInfo {
        int size;
        int startIndex;
        int capacity;

        StackInfo(int size, int startIndex, int capacity) {
            this.size = size;
            this.startIndex = startIndex;
            this.capacity = capacity;
        }
    }

    @SuppressWarnings("unchecked")
    public static class ThreeStacks<Item> {
        private Item[] array;
        private StackInfo[] stacksInfo;

        ThreeStacks(int numberOfStacks) {
            array = (Item[]) new Object[numberOfStacks];
            stacksInfo = new StackInfo[numberOfStacks];

            for (int stackId = 0; stackId < numberOfStacks; stackId++) {
                stacksInfo[stackId] = new StackInfo(0, stackId, 1);
            }
        }

        public int size(int stackId) {
            return stacksInfo[stackId].size;
        }

        public void push(int stackId, Item item) {
            stacksInfo[stackId].size++;
            int stackStartIndex = getStackArrayStartIndex(stackId);
            array[stackStartIndex] = item;

            if (stacksInfo[stackId].size == stacksInfo[stackId].capacity) {
                stacksInfo[stackId].capacity *= 2;
                resize();
            }
        }

        public Item pop(int stackId) {
            if (isEmpty(stackId)) {
                throw new NoSuchElementException("Stack is empty");
            }
            StackInfo stackInfo = stacksInfo[stackId];

            int stackStartIndex = getStackArrayStartIndex(stackId);
            Item item = array[stackStartIndex];
            array[stackStartIndex] = null;
            stackInfo.size--;

            if (stackInfo.size <= stackInfo.capacity / 4) {
                stacksInfo[stackId].capacity /= 2;
                resize();
            }

            return item;
        }

        public Item peek(int stackId) {
            if (isEmpty(stackId)) {
                throw new NoSuchElementException("Stack is empty");
            }

            int stackStartIndex = getStackArrayStartIndex(stackId);
            return array[stackStartIndex];
        }

        public boolean isEmpty(int stackId) {
            return stacksInfo[stackId].size == 0;
        }

        public void printStacks() {
            for (int stackId = 0; stackId < stacksInfo.length; stackId++) {
                System.out.print("Stack " + (stackId + 1) + ": ");

                if (isEmpty(stackId)) {
                    System.out.println();
                    continue;
                }

                int stackStartIndex = getStackArrayStartIndex(stackId);

                for (int i = stackStartIndex; i >= stacksInfo[stackId].startIndex; i--) {
                    System.out.print(array[i] + " ");
                }
                System.out.println();
            }
        }

        private int getStackArrayStartIndex(int stackId) {
            return stacksInfo[stackId].startIndex + stacksInfo[stackId].size - 1;
        }

        private void resize() {
            int totalCapacity = 0;

            for (int stackId = 0; stackId < stacksInfo.length; stackId++) {
                totalCapacity += stacksInfo[stackId].capacity;
            }

            Item[] newStacks = (Item[]) new Object[totalCapacity];
            int stackNewStartIndex = 0;

            for (int stackId = 0; stackId < stacksInfo.length; stackId++) {
                int stackStartIndex = stacksInfo[stackId].startIndex;
                int newArrayIndex = stackNewStartIndex;
                stacksInfo[stackId].startIndex = stackNewStartIndex;

                for (int i = stackStartIndex; i < stackStartIndex + stacksInfo[stackId].size; i++) {
                    newStacks[newArrayIndex++] = array[i];
                }

                stackNewStartIndex += stacksInfo[stackId].capacity;
            }

            array = newStacks;
        }
    }

    public static void main(String[] args) {
        ThreeStacks<Integer> threeStacks = new ThreeStacks<>(3);

        // Test isEmpty
        System.out.println("IsEmpty - stack 1: " + threeStacks.isEmpty(0));
        System.out.println("Expected: true");

        // Test push
        threeStacks.push(0, 1);
        threeStacks.push(0, 2);
        threeStacks.push(0, 3);
        threeStacks.push(1, 10);
        threeStacks.push(1, 11);
        threeStacks.push(1, 12);
        threeStacks.push(2, 90);
        threeStacks.push(2, 91);

        System.out.println("\nStacks");
        threeStacks.printStacks();
        System.out.println("Expected:");
        System.out.println("Stack 1: 3 2 1");
        System.out.println("Stack 2: 12 11 10");
        System.out.println("Stack 3: 91 90");

        System.out.println("\nIsEmpty - stack 1: " + threeStacks.isEmpty(0));
        System.out.println("Expected: false");

        // Test size
        System.out.println("\nSize - stack 2: " + threeStacks.size(1));
        System.out.println("Expected: 3");

        // Test peek
        System.out.println("\nPeek - stack 1: " + threeStacks.peek(0));
        System.out.println("Expected: 3");

        System.out.println("Peek - stack 2: " + threeStacks.peek(1));
        System.out.println("Expected: 12");

        System.out.println("Peek - stack 3: " + threeStacks.peek(2));
        System.out.println("Expected: 91");

        // Test pop
        threeStacks.pop(1);
        threeStacks.pop(1);
        threeStacks.pop(1);
        threeStacks.pop(2);

        System.out.println("\nStacks");
        threeStacks.printStacks();
        System.out.println("Expected:");
        System.out.println("Stack 1: 3 2 1");
        System.out.println("Stack 2: ");
        System.out.println("Stack 3: 90");
    }

}
