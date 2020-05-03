package chapter16_moderate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Rene Argento on 16/04/20.
 */
public class Exercise23_Rand7FromRand5 {

    private static Random random = new Random();

    // O(infinite) runtime (nondeterministic number of calls to rand5())
    // O(1) space
    public static int rand7() {
        while (true) {
            int number = 5 * rand5() + rand5();
            if (number < 21) {
                return number % 7;
            }
        }
    }

    private static int rand5() {
        return random.nextInt(5);
    }

    public static void main(String[] args) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            int random = rand7();
            frequencyMap.put(random, frequencyMap.getOrDefault(random, 0) + 1);
        }
        System.out.println("Frequency");
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
