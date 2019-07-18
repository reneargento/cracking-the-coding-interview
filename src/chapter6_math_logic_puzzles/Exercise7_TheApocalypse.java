package chapter6_math_logic_puzzles;

import java.util.Random;

/**
 * Created by Rene Argento on 11/07/19.
 */
public class Exercise7_TheApocalypse {

    public static void main(String[] args) {
        double genderRatio = runSimulation(1000);
        System.out.printf("Gender ratio: %.4f", genderRatio);
    }

    private static double runSimulation(int families) {
        Random random = new Random();
        int girls = 0;
        int boys = 0;

        for (int family = 0; family < families; family++) {
            int[] genders = runOneFamily(random);
            girls += genders[0];
            boys += genders[1];
        }

        return girls / (double) (girls + boys);
    }

    private static int[] runOneFamily(Random random) {
        int girls = 0;
        int boys = 0;

        while (girls == 0) {
            if (random.nextBoolean()) {
                girls++;
            } else {
                boys++;
            }
        }

        return new int[]{girls, boys};
    }

}
