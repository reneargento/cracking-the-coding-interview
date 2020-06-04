package chapter17_hard;

/**
 * Created by Rene Argento on 18/05/20.
 */
public class Exercise16_TheMasseuse {

    // O(n) runtime, where n is the number of appointments
    // O(1) space
    public static int maxMinutes(int[] appointments) {
        if (appointments == null) {
            return 0;
        }
        int twoAway = 0;
        int oneAway = 0;

        for (int appointment : appointments) {
            int bestWith = twoAway + appointment;
            int bestWithout = oneAway;

            int current = Math.max(bestWith, bestWithout);
            twoAway = oneAway;
            oneAway = current;
        }
        return oneAway;
    }

    public static void main(String[] args) {
        int[] array1 = { 30, 15, 60, 75, 45, 15, 15, 45 };
        int maxMinutes1 = maxMinutes(array1);
        System.out.println("Max minutes: " + maxMinutes1 + " Expected: 180");

        int[] array2 = { 30, 90, 180, 210 };
        int maxMinutes2 = maxMinutes(array2);
        System.out.println("Max minutes: " + maxMinutes2 + " Expected: 300");

        int[] array3 = { 15, 15, 15, 45, 30 };
        int maxMinutes3 = maxMinutes(array3);
        System.out.println("Max minutes: " + maxMinutes3 + " Expected: 60");

        int[] array4 = { 75, 105, 120, 75, 90, 135 };
        int maxMinutes4 = maxMinutes(array4);
        System.out.println("Max minutes: " + maxMinutes4 + " Expected: 330");
    }

}
