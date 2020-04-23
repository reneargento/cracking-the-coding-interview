package chapter16_moderate;

/**
 * Created by Rene Argento on 07/04/20.
 */
public class Exercise18_PatternMatching {

    // O(n^2) runtime, where n is the value length
    // O(1) space
    public static boolean matches(String pattern, String value) {
        if (pattern == null || value == null) {
            return false;
        }
        if (pattern.length() == 0) {
            return value.length() == 0;
        }

        char main = pattern.charAt(0);
        char alternate = main == 'a' ? 'b' : 'a';
        int size = value.length();

        int countOfMain = count(pattern, main);
        int countOfAlternate = pattern.length() - countOfMain;
        int alternateIndexOnPattern = pattern.indexOf(alternate);
        int maxMainSize = size / countOfMain;

        for (int mainSize = 1; mainSize <= maxMainSize; mainSize++) {
            int mainTotalSize = mainSize * countOfMain;
            int remainingLength = size - mainTotalSize;
            if (countOfAlternate == 0 || remainingLength % countOfAlternate == 0) {
                int alternateIndexOnValue = alternateIndexOnPattern * mainSize;
                int alternateSize = countOfAlternate != 0 ? remainingLength / countOfAlternate : 0;

                if (matches(pattern, value, alternateIndexOnValue, mainSize, alternateSize)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int count(String string, char character) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }

    /* Iterates through pattern and for each character in it, check if it is the main string or the alternate string.
    *  Then checks if the next set of characters in value matches the original set of those characters (either the main
    *  or the alternate). */
    private static boolean matches(String pattern, String value, int alternateIndexOnValue, int mainSize, int alternateSize) {
        int stringIndex = mainSize;

        for (int i = 1; i < pattern.length(); i++) {
            int size = pattern.charAt(i) == pattern.charAt(0) ? mainSize : alternateSize;
            int offset = pattern.charAt(i) == pattern.charAt(0) ? 0 : alternateIndexOnValue;
            if (!isEqual(value, stringIndex, offset, size)) {
                return false;
            }
            stringIndex += size;
        }
        return true;
    }

    private static boolean isEqual(String string, int offset1, int offset2, int size) {
        for (int i = 0; i < size; i++) {
            if (string.charAt(offset1 + i) != string.charAt(offset2 + i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String pattern1 = "aabab";
        String value1 = "catcatgocatgo";
        boolean matches1 = matches(pattern1, value1);
        System.out.println("Matches 1: " + matches1 + " Expected: true");

        String pattern2 = "aab";
        String value2 = "renereneabc";
        boolean matches2 = matches(pattern2, value2);
        System.out.println("Matches 2: " + matches2 + " Expected: true");

        String pattern3 = "aaaa";
        String value3 = "rene";
        boolean matches3 = matches(pattern3, value3);
        System.out.println("Matches 3: " + matches3 + " Expected: false");

        String pattern4 = "aaaa";
        String value4 = "renerenerenerene";
        boolean matches4 = matches(pattern4, value4);
        System.out.println("Matches 4: " + matches4 + " Expected: true");

        String pattern5 = "a";
        String value5 = "renerenerenerene";
        boolean matches5 = matches(pattern5, value5);
        System.out.println("Matches 5: " + matches5 + " Expected: true");

        String pattern6 = "ababa";
        String value6 = "catrenecatrenecat";
        boolean matches6 = matches(pattern6, value6);
        System.out.println("Matches 6: " + matches6 + " Expected: true");

        boolean matches7 = matches(pattern6, value2);
        System.out.println("Matches 7: " + matches7 + " Expected: false");
    }

}
