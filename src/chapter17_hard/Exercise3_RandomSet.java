package chapter17_hard;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 26/04/20.
 */
public class Exercise3_RandomSet {

    // O(m) runtime, where m is the set size
    // O(m) space
    public static Set<Integer> randomSet(int[] array, int setSize) {
        Random random = new Random();

        for (int i = 0; i < setSize; i++) {
            // Choose a random index between the current index and the end of the array
            int randomIndex = i + random.nextInt(array.length - i);

            // Swap elements
            int temp = array[randomIndex];
            array[randomIndex] = array[i];
            array[i] = temp;
        }

        Set<Integer> randomSet = new HashSet<>();
        for (int i = 0; i < setSize; i++) {
            randomSet.add(array[i]);
        }
        return randomSet;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Set<Integer> randomSet = randomSet(array, 4);

        StringJoiner elements = new StringJoiner(" ");
        for (int element : randomSet) {
            elements.add(String.valueOf(element));
        }
        System.out.println("Random set: " + elements);
    }

}
