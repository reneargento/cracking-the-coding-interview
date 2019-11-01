package chapter8_recursion_dynamic_programming;

/**
 * Created by Rene Argento on 01/10/19.
 */
// Reference: https://www.geeksforgeeks.org/understanding-the-coin-change-problem-with-dynamic-programming/
public class Exercise11_Coins {

    // O(c * n) runtime, where c is the number of coins and n is the value
    // O(c + n) space
    public static int countNumberOfWays(int value) {
        int[] ways = new int[value + 1];
        int[] coins = {1, 5, 10, 25};

        // Base case - there is only one way to make value 0, using 0 coins
        ways[0] = 1;

        for (int c = 0; c < coins.length; c++) {
            for (int v = 1; v < ways.length; v++) {
                int previousValue = v - coins[c];

                if (previousValue >= 0) {
                    ways[v] += ways[previousValue];
                }
            }
        }

        return ways[value];
    }

    public static void main(String[] args) {
        int numberOfWays1 = countNumberOfWays(1);
        System.out.println("Number of ways to get 1: " + numberOfWays1 + " Expected: 1");

        int numberOfWays2 = countNumberOfWays(5);
        System.out.println("Number of ways to get 5: " + numberOfWays2 + " Expected: 2");

        int numberOfWays3 = countNumberOfWays(6);
        System.out.println("Number of ways to get 6: " + numberOfWays3 + " Expected: 2");

        int numberOfWays4 = countNumberOfWays(10);
        System.out.println("Number of ways to get 10: " + numberOfWays4 + " Expected: 4");

        int numberOfWays5 = countNumberOfWays(12);
        System.out.println("Number of ways to get 12: " + numberOfWays5 + " Expected: 4");

        int numberOfWays6 = countNumberOfWays(27);
        System.out.println("Number of ways to get 27: " + numberOfWays6 + " Expected: 13");
    }

}
