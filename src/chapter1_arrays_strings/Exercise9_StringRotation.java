package chapter1_arrays_strings;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise9_StringRotation {

    // O(n) runtime
    // O(n) space
    public static boolean isRotation(String string1, String string2) {
        if (string1 == null || string2 == null || string1.length() != string2.length()) {
            return false;
        }

        String concatenatedString = string1 + string1;
        return isSubstring(concatenatedString, string2);
    }

    private static boolean isSubstring(String string1, String string2) {
        return string1.contains(string2) || string2.contains(string1);
    }

    public static void main(String[] args) {
        String string1 = "waterbottle";
        String string2 = "erbottlewat";
        System.out.println("Is rotation: " + isRotation(string1, string2) + " Expected: true");

        String string3 = "waterbotle";
        System.out.println("Is rotation: " + isRotation(string1, string3) + " Expected: false");

        String string4 = "rene";
        String string5 = "ener";
        System.out.println("Is rotation: " + isRotation(string4, string5) + " Expected: true");
    }

}
