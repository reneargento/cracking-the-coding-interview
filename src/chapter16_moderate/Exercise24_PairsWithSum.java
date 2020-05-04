package chapter16_moderate;

import java.util.*;

/**
 * Created by Rene Argento on 21/04/20.
 */
public class Exercise24_PairsWithSum {

    public static class Pair {
        int number1;
        int number2;

        public Pair(int number1, int number2) {
            this.number1 = number1;
            this.number2 = number2;
        }

        @Override
        public String toString() {
            return "(" + number1 + ", " + number2 + ")";
        }
    }

    // O(n) runtime, where n is the number of elements on the array
    // O(n) space
    public static List<Pair> getAllPairsWithSum(int[] array, int sum) {
        List<Pair> pairs = new ArrayList<>();
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int number : array) {
            int complement = sum - number;
            int frequencyOfComplement = frequencyMap.getOrDefault(complement, 0);

            for (int i = 0; i < frequencyOfComplement; i++) {
                pairs.add(new Pair(number, complement));
            }
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }
        return pairs;
    }

    public static void main(String[] args) {
        int[] array = {0, 0, 2, 5, -3, 1, 1, 1};

        List<Pair> pairs1 = getAllPairsWithSum(array, 7);
        printPairs(pairs1);
        System.out.println("Expected: (5, 2)\n");

        List<Pair> pairs2 = getAllPairsWithSum(array, 0);
        printPairs(pairs2);
        System.out.println("Expected: (0, 0)\n");

        List<Pair> pairs3 = getAllPairsWithSum(array, 2);
        printPairs(pairs3);
        System.out.println("Expected: (2, 0) (2, 0) (-3, 5) (1, 1) (1, 1) (1, 1)");
    }

    private static void printPairs(List<Pair> pairs) {
        StringJoiner pairsDescription = new StringJoiner(" ");
        for (Pair pair : pairs) {
            pairsDescription.add(String.valueOf(pair));
        }
        System.out.println("Pairs: " + pairsDescription);
    }

}
