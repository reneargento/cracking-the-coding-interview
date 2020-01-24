package chapter13_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by Rene Argento on 23/01/20.
 */
public class Exercise8_LambdaRandom {

    // O(n) runtime, where n is the number of elements in the list
    // O(n) space
    public static List<Integer> getRandomSubset(List<Integer> list) {
        Random random = new Random();
        return list.stream()
                .filter(element -> random.nextBoolean())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            values.add(i);
        }

        List<Integer> randomSubset = getRandomSubset(values);

        StringJoiner valuesString = new StringJoiner( " ");
        for (int value : randomSubset) {
            valuesString.add(String.valueOf(value));
        }
        System.out.println("Random subset: " + randomSubset);
    }

}
