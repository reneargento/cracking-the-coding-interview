package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 23/05/20.
 */
public class Exercise18_ShortestSupersequence {

    public static class Range {
        private int start;
        private int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
        }
    }

    private static class MinQueue {

        private class DoublyLinkedNode {
            private DoublyLinkedNode previous;
            private DoublyLinkedNode next;
            private int value;
            private int originalIndex;

            public DoublyLinkedNode(int value, int originalIndex) {
                this.value = value;
                this.originalIndex = originalIndex;
            }
        }

        private Map<Integer, DoublyLinkedNode> valuesToNodesMap;
        private DoublyLinkedNode start;
        private DoublyLinkedNode end;

        public MinQueue() {
            valuesToNodesMap = new HashMap<>();
        }

        // O(1) runtime
        public int size() {
            return valuesToNodesMap.size();
        }

        // O(1) runtime
        public void insert(int value, int originalIndex) {
            if (valuesToNodesMap.containsKey(value)) {
                delete(value);
            }
            DoublyLinkedNode node = new DoublyLinkedNode(value, originalIndex);

            if (start == null) {
                start = node;
                end = node;
            } else {
                end.next = node;
                node.previous = end;
                end = node;
            }
            valuesToNodesMap.put(value, node);
        }

        // O(1) runtime
        private void delete(int value) {
            DoublyLinkedNode node = valuesToNodesMap.get(value);

            if (start == node) {
                start = node.next;
            }
            if (end == node) {
                end = node.previous;
            }

            if (node.previous != null) {
                node.previous.next = node.next;
            }
            if (node.next != null) {
                node.next.previous = node.previous;
            }

            valuesToNodesMap.remove(node.value);
        }

        // O(1) runtime
        public Integer peek() {
            if (valuesToNodesMap.isEmpty()) {
                return null;
            }
            return start.originalIndex;
        }
    }

    // O(b) runtime, where b is the number of elements in bigArray
    // O(s) space, where s is the number of elements in smallArray
    public static Range shortestSupersequence(Integer[] smallArray, Integer[] bigArray) {
        if (smallArray == null || bigArray == null || smallArray.length > bigArray.length) {
            return null;
        }
        Set<Integer> valuesSet = new HashSet<>(Arrays.asList(smallArray));

        MinQueue minQueue = new MinQueue();
        int supersequenceStart = -1;
        int supersequenceEnd = -1;
        int supersequenceLength = -1;

        for (int i = 0; i < bigArray.length; i++) {
            int value = bigArray[i];

            if (valuesSet.contains(value)) {
                minQueue.insert(value, i);

                if (minQueue.size() == valuesSet.size()) {
                    int start = minQueue.peek();
                    int length = i - start + 1;

                    if (supersequenceStart == -1 || length < supersequenceLength) {
                        supersequenceStart = start;
                        supersequenceEnd = i;
                        supersequenceLength = length;
                    }
                }
            }
        }

        if (supersequenceStart == -1) {
            return null;
        }
        return new Range(supersequenceStart, supersequenceEnd);
    }

    public static void main(String[] args) {
        Integer[] smallArray1 = {1, 5, 9};
        Integer[] bigArray1 = {7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7};
        Range range1 = shortestSupersequence(smallArray1, bigArray1);
        System.out.println("Super sequence: " + range1 + " Expected: [7, 10]");

        Integer[] smallArray2 = {1, 2, 3, 4};
        Integer[] bigArray2 = {1, 5, 2, 3, 2, 0, 4, 5, 7, 10, 99, 9, 1, 2, 3, 100};
        Range range2 = shortestSupersequence(smallArray2, bigArray2);
        System.out.println("Super sequence: " + range2 + " Expected: [0, 6]");

        Integer[] smallArray3 = {1, 100};
        Range range3 = shortestSupersequence(smallArray3, bigArray2);
        System.out.println("Super sequence: " + range3 + " Expected: [12, 15]");
    }

}
