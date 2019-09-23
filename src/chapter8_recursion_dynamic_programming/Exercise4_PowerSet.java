package chapter8_recursion_dynamic_programming;

import java.util.*;

/**
 * Created by Rene Argento on 18/09/19.
 */
public class Exercise4_PowerSet {

    // O(n * 2^n) runtime
    // O(n * 2^n) space
    public static Set<Set<Integer>> powerSet(List<Integer> items) {
        Set<Set<Integer>> subsets = new HashSet<>();
        int numberOfSubsets = 1 << items.size(); // There are 2^n subsets

        for (int subsetId = 0; subsetId < numberOfSubsets; subsetId++) {
            Set<Integer> subset = new HashSet<>();
            int bit = 0;

            for (int id = subsetId; id > 0; id >>= 1) {
                if ((id & 1) == 1) {
                    subset.add(items.get(bit));
                }
                bit++;
            }

            subsets.add(subset);
        }

        return subsets;
    }

    // O(n * 2^n) runtime
    // O(n * 2^n) space
    public static Set<Set<Integer>> powerSetIterative(List<Integer> items) {
        Set<Set<Integer>> subsets = new HashSet<>();
        subsets.add(new HashSet<>()); // Add initial, empty set

        for (int item : items) {
            Set<Set<Integer>> newSubsets = new HashSet<>();

            // Copy existing subsets
            for (Set<Integer> subset : subsets) {
                newSubsets.add(new HashSet<>(subset));
            }

            // Add current item to the copy of all existing subsets
            for (Set<Integer> subset : newSubsets) {
                subset.add(item);
            }

            subsets.addAll(newSubsets);
        }

        return subsets;
    }

    public static void main(String[] args) {
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(3);

        Set<Set<Integer>> subsets1 = powerSet(items);
        System.out.println("Subsets 1:");
        printPowerSet(subsets1);

        Set<Set<Integer>> subsets2 = powerSetIterative(items);
        System.out.println("\nSubsets 2:");
        printPowerSet(subsets2);

        System.out.println("\nExpected:");
        System.out.println(); // Empty subset
        System.out.println("1");
        System.out.println("2");
        System.out.println("1 2");
        System.out.println("3");
        System.out.println("1 3");
        System.out.println("2 3");
        System.out.println("1 2 3");
    }

    private static void printPowerSet(Set<Set<Integer>> powerSet) {
        for (Set<Integer> subset : powerSet) {
            StringJoiner elements = new StringJoiner(" ");

            for (int element : subset) {
                elements.add(String.valueOf(element));
            }
            System.out.println(elements);
        }
    }

}
