package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 06/05/20.
 */
public class Exercise9_KthMultiple {

    // O(k) runtime, where k is the index of the number searched
    // O(k) space
    public static long kThMultiple(int k) {
        if (k < 0) { return 0; }

        Queue<Long> multiplesOf3 = new LinkedList<>();
        Queue<Long> multiplesOf5 = new LinkedList<>();
        Queue<Long> multiplesOf7 = new LinkedList<>();

        long number = 0;
        multiplesOf3.offer(1L);

        for (int i = 0; i <= k; i++) {
            long multipleOf3 = multiplesOf3.isEmpty() ? Integer.MAX_VALUE : multiplesOf3.peek();
            long multipleOf5 = multiplesOf5.isEmpty() ? Integer.MAX_VALUE : multiplesOf5.peek();
            long multipleOf7 = multiplesOf7.isEmpty() ? Integer.MAX_VALUE : multiplesOf7.peek();

            number = Math.min(multipleOf3, Math.min(multipleOf5, multipleOf7));

            if (number == multipleOf3) {
                multiplesOf3.poll();
                multiplesOf3.offer(number * 3);
                multiplesOf5.offer(number * 5);
            } else if (number == multipleOf5) {
                multiplesOf5.poll();
                multiplesOf5.offer(number * 5);
            } else {
                multiplesOf7.poll();
            }
            multiplesOf7.offer(number * 7);
        }
        return number;
    }

    public static void main(String[] args) {
        long kThMultiple1 = kThMultiple(0);
        System.out.println("0th number: " + kThMultiple1 + " Expected: 1");

        long kThMultiple2 = kThMultiple(4);
        System.out.println("4th number: " + kThMultiple2 + " Expected: 9");

        long kThMultiple3 = kThMultiple(5);
        System.out.println("5th number: " + kThMultiple3 + " Expected: 15");

        long kThMultiple4 = kThMultiple(7);
        System.out.println("7th number: " + kThMultiple4 + " Expected: 25");

        long kThMultiple5 = kThMultiple(12);
        System.out.println("12th number: " + kThMultiple5 + " Expected: 63");
    }

}
