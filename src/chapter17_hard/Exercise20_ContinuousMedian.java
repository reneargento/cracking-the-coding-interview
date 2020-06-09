package chapter17_hard;

import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 30/05/20.
 */
public class Exercise20_ContinuousMedian {

    public static class ContinuousMedianCalculator {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;

        public ContinuousMedianCalculator() {
            maxHeap = new PriorityQueue<>((number1, number2) -> number2 - number1);
            minHeap = new PriorityQueue<>();
        }

        // O(log n) runtime, where n is the number of elements in max(maxHeap.size(), minHeap.size())
        public double addNumberAndGetMedian(int number) {
            addNumber(number);
            return getMedian();
        }

        // O(log n) runtime, where n is the number of elements in max(maxHeap.size(), minHeap.size())
        public void addNumber(int number) {
            if (maxHeap.isEmpty() || number <= maxHeap.peek()) {
                if (maxHeap.size() > minHeap.size()) {
                    int maxHeapTop = maxHeap.poll();
                    minHeap.offer(maxHeapTop);
                }
                maxHeap.offer(number);
            } else {
                if (minHeap.size() > maxHeap.size()) {
                    int minHeapTop = minHeap.poll();
                    maxHeap.offer(minHeapTop);
                }
                minHeap.offer(number);
            }
        }

        // O(1) runtime
        public double getMedian() {
            // If maxHeap is empty, minHeap is also empty
            if (maxHeap.isEmpty()) {
                return 0;
            }

            if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();
            } else if (minHeap.size() > maxHeap.size()) {
                return minHeap.peek();
            } else {
                return ((double) maxHeap.peek() + (double) minHeap.peek()) / 2;
            }
        }
    }

    public static void main(String[] args) {
        ContinuousMedianCalculator continuousMedianCalculator = new ContinuousMedianCalculator();
        double median1 = continuousMedianCalculator.addNumberAndGetMedian(5);
        System.out.println("Median: " + median1 + " Expected: 5.0");

        double median2 = continuousMedianCalculator.addNumberAndGetMedian(1);
        System.out.println("Median: " + median2 + " Expected: 3.0");

        double median3 = continuousMedianCalculator.addNumberAndGetMedian(10);
        System.out.println("Median: " + median3 + " Expected: 5.0");

        double median4 = continuousMedianCalculator.addNumberAndGetMedian(-2);
        System.out.println("Median: " + median4 + " Expected: 3.0");

        continuousMedianCalculator.addNumber(15);
        continuousMedianCalculator.addNumber(20);
        double median5 = continuousMedianCalculator.addNumberAndGetMedian(100);
        System.out.println("Median: " + median5 + " Expected: 10.0");

        continuousMedianCalculator.addNumber(30);
        double median6 = continuousMedianCalculator.addNumberAndGetMedian(40);
        System.out.println("Median: " + median6 + " Expected: 15.0");
    }

}
