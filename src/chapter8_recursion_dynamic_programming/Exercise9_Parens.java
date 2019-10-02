package chapter8_recursion_dynamic_programming;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 27/09/19.
 */
public class Exercise9_Parens {

    public static void printAllParentheses(int pairs) {
        char[] string = new char[pairs * 2];
        List<String> parentheses = new ArrayList<>();
        printAllParentheses(pairs, pairs, string, 0, parentheses);
        printParentheses(parentheses);
    }

    // O(n^2 * 2^n) runtime
    // O(2^n) space
    private static void printAllParentheses(int leftRemaining, int rightRemaining, char[] string, int index,
                                            List<String> parentheses) {
        if (leftRemaining < 0 || rightRemaining < leftRemaining) {
            return; // Invalid state
        }

        // Base case
        if (leftRemaining == 0 && rightRemaining == 0) {
            parentheses.add(String.valueOf(string));
            return;
        }

        string[index] = '(';
        printAllParentheses(leftRemaining - 1, rightRemaining, string, index + 1, parentheses);

        string[index] = ')';
        printAllParentheses(leftRemaining, rightRemaining - 1, string, index + 1, parentheses);
    }

    private static void printParentheses(List<String> parentheses) {
        StringJoiner stringJoiner = new StringJoiner(" , ");
        for (String current : parentheses) {
            stringJoiner.add(current);
        }
        System.out.println("Parentheses: " + stringJoiner);
    }

    public static void main(String[] args) {
        printAllParentheses(3);
        System.out.println("Expected: ((())) , (()()) , (())() , ()(()) , ()()()");
    }

}