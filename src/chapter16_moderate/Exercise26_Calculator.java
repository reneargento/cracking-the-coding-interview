package chapter16_moderate;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 23/04/20.
 */
public class Exercise26_Calculator {

    private enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    // O(n) runtime, where n is the number of characters on the equation
    // O(1) space (each stack will have at most size 2)
    public static double compute(String equation) {
        Deque<Double> operands = new ArrayDeque<>();
        Deque<Operator> operators = new ArrayDeque<>();

        // Compute priority operations right away.
        // Compute non-priority operations once we have 2 of them consecutive.
        for (int i = 0; i < equation.length(); i++) {
            try {
                int number = parseNextNumber(equation, i);
                operands.push((double) number);

                // Compute expression if the previous operator was high priority
                Operator topOperator = operators.isEmpty() ? null : operators.peek();
                if (topOperator != null && isPriorityOperator(topOperator)) {
                    popStacksAndCompute(operands, operators);
                }

                // Move index to next operator
                i += String.valueOf(number).length();
                if (i >= equation.length()) {
                    break;
                }

                Operator operator = parseNextOperator(equation, i);

                // Compute expression if there are 2 consecutive non-priority operators
                if (!isPriorityOperator(operator)
                        && !operators.isEmpty()
                        && !isPriorityOperator(operators.peek())) {
                    popStacksAndCompute(operands, operators);
                }
                operators.push(operator);
            } catch (NumberFormatException exception) {
                return Double.MIN_VALUE;
            }
        }

        // If the last operator was non-priority it will still have to be processed
        if (!operators.isEmpty()) {
            popStacksAndCompute(operands, operators);
        }
        return operands.pop();
    }

    private static int parseNextNumber(String equation, int offset) {
        StringBuilder numberString = new StringBuilder();
        while (offset < equation.length() && Character.isDigit(equation.charAt(offset))) {
            numberString.append(equation.charAt(offset));
            offset++;
        }
        return Integer.parseInt(numberString.toString());
    }

    private static Operator parseNextOperator(String equation, int offset) {
        char operator = equation.charAt(offset);
        switch (operator) {
            case '+': return Operator.ADD;
            case '-': return Operator.SUBTRACT;
            case '*': return Operator.MULTIPLY;
            default: return Operator.DIVIDE;
        }
    }

    private static boolean isPriorityOperator(Operator operator) {
        return operator == Operator.MULTIPLY || operator == Operator.DIVIDE;
    }

    private static void popStacksAndCompute(Deque<Double> operands, Deque<Operator> operators) {
        double number2 = operands.pop();
        double number1 = operands.pop();
        Operator operator = operators.pop();
        double result = compute(number1, operator, number2);
        operands.push(result);
    }

    private static double compute(double number1, Operator operator, double number2) {
        switch (operator) {
            case ADD: return number1 + number2;
            case SUBTRACT: return number1 - number2;
            case MULTIPLY: return number1 * number2;
            default: {
                if (number2 == 0) {
                    throw new ArithmeticException("Cannot divide by 0");
                }
                return number1 / number2;
            }
        }
    }

    public static void main(String[] args) {
        String equation1 = "2*3+5/6*3+15";
        double result1 = compute(equation1);
        System.out.println("Result: " + result1 + " Expected: 23.5");

        String equation2 = "5-1+3";
        double result2 = compute(equation2);
        System.out.println("Result: " + result2 + " Expected: 7.0");

        String equation3 = "2-6-7*8/2+5";
        double result3 = compute(equation3);
        System.out.println("Result: " + result3 + " Expected: -27.0");

        String equation4 = "5324-31*2+1-4/2";
        double result4 = compute(equation4);
        System.out.println("Result: " + result4 + " Expected: 5261.0");
    }

}
