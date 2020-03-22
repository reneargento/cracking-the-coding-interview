package chapter16_moderate;

import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 20/03/20.
 */
public class Exercise8_EnglishInt {

    private static final String[] SMALLS = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] BIGS = {"", "Thousand,", "Million,", "Billion,"};
    private static final String HUNDRED = "Hundred";
    private static final String NEGATIVE = "Negative";

    // O(c) runtime, where c is the number of 3-digit chunks in the integer
    // O(c) space
    public static String englishInt(int integer) {
        if (integer == 0) {
            return SMALLS[0];
        } else if (integer < 0) {
            return NEGATIVE + " " + englishInt(-1 * integer);
        }

        LinkedList<String> parts = new LinkedList<>();
        int chunkCount = 0;

        while (integer > 0) {
            if (integer % 1000 != 0) {
                int chunk = integer % 1000;
                String convertedChunk = convertChunk(chunk) + " " + BIGS[chunkCount];
                parts.addFirst(convertedChunk);
            }
            integer /= 1000; // Shift chunk
            chunkCount++;
        }

        String englishInt = listToString(parts).trim();
        if (englishInt.charAt(englishInt.length() - 1) == ',') {
            englishInt = englishInt.substring(0, englishInt.length() - 1);
        }
        return englishInt;
    }

    private static String convertChunk(int number) {
        LinkedList<String> parts = new LinkedList<>();

        // Convert hundreds place
        if (number >= 100) {
            String convertedHundred = SMALLS[number / 100] + " " + HUNDRED;
            parts.addLast(convertedHundred);
            number %= 100;
        }

        // Convert tens place
        if (number >= 10 && number <= 19) {
            parts.addLast(SMALLS[number]);
        } else if (number >= 20) {
            parts.addLast(TENS[number / 10]);
            number %= 10;
        }

        // Convert ones place
        if (number >= 1 && number <= 9) {
            parts.addLast(SMALLS[number]);
        }
        return listToString(parts);
    }

    private static String listToString(LinkedList<String> parts) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (String part : parts) {
            stringJoiner.add(part);
        }
        return stringJoiner.toString();
    }

    public static void main(String[] args) {
        String englishInt1 = englishInt(1234);
        System.out.println("1.234: " + englishInt1);
        System.out.println("Expected: One Thousand, Two Hundred Thirty Four\n");

        String englishInt2 = englishInt(10819);
        System.out.println("10.819: " + englishInt2);
        System.out.println("Expected: Ten Thousand, Eight Hundred Nineteen\n");

        String englishInt3 = englishInt(1234342221);
        System.out.println("1.234.342.221: " + englishInt3);
        System.out.println("Expected: One Billion, Two Hundred Thirty Four Million, " +
                "Three Hundred Forty Two Thousand, " +
                "Two Hundred Twenty One\n");

        String englishInt4 = englishInt(-200);
        System.out.println("-200: " + englishInt4);
        System.out.println("Expected: Negative Two Hundred\n");

        String englishInt5 = englishInt(1001);
        System.out.println("1001: " + englishInt5);
        System.out.println("Expected: One Thousand, One\n");

        String englishInt6 = englishInt(100030000);
        System.out.println("100.030.000: " + englishInt6);
        System.out.println("Expected: One Hundred Million, Thirty Thousand\n");

        String englishInt7 = englishInt(0);
        System.out.println("0: " + englishInt7);
        System.out.println("Expected: Zero");
    }

}