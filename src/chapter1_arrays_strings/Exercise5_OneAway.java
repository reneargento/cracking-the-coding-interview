package chapter1_arrays_strings;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise5_OneAway {

    // O(MIN(s1, s2)) runtime, where s1 is the length of the first string and s2 is the length of the second string
    // O(1) space
    public static boolean isOneAway(String string1, String string2) {
        int lengthDifference = Math.abs(string1.length() - string2.length());
        if (lengthDifference > 1) {
            return false;
        }

        String shorterString;
        String longerString;

        if (string1.length() < string2.length()) {
            shorterString = string1;
            longerString = string2;
        } else {
            shorterString = string2;
            longerString = string1;
        }

        int index1 = 0;
        int index2 = 0;

        boolean foundDifference = false;

        while (index1 < shorterString.length() && index2 < longerString.length()) {
            if (shorterString.charAt(index1) != longerString.charAt(index2)) {
                if (foundDifference) {
                    return false;
                }

                foundDifference = true;

                if (shorterString.length() == longerString.length()) {
                    // Replace edit
                    index1++;
                } // Else -> insert or delete edit -> do not move index 1
            } else {
                index1++;
            }

            index2++;
        }

        return true;
    }

    // O(s1 * s2) runtime, where s1 is the length of the first string and s2 is the length of the second string
    // O(s1 * s2) space
    public static boolean isOneAway2(String string1, String string2) {
        int lengthDifference = Math.abs(string1.length() - string2.length());
        if (lengthDifference > 1) {
            return false;
        }

        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        for (int i = 1; i <= string1.length(); i++) {
            for (int j = 1; j <= string2.length(); j++) {
                int smallestEditDistance = Math.min(dp[i - 1][j - 1], dp[i][j - 1]);
                smallestEditDistance = Math.min(smallestEditDistance, dp[i - 1][j]);

                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    dp[i][j] = smallestEditDistance;
                } else {
                    dp[i][j] = smallestEditDistance + 1;
                }
            }
        }

        return dp[string1.length()][string2.length()] <= 1;
    }

    public static void main(String[] args) {
        String string1 = "pale";
        String string2 = "ple";
        String string3 = "pales";
        String string4 = "bale";
        String string5 = "bake";

        System.out.println("Is one away: " + isOneAway(string1, string2) + " Expected: " + true);
        System.out.println("Is one away: " + isOneAway(string3, string1) + " Expected: " + true);
        System.out.println("Is one away: " + isOneAway(string1, string4) + " Expected: " + true);
        System.out.println("Is one away: " + isOneAway(string1, string5) + " Expected: " + false);
    }

}

