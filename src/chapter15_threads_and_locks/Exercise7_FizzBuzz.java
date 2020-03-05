package chapter15_threads_and_locks;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Rene Argento on 03/03/20.
 */
public class Exercise7_FizzBuzz extends Thread {
    private static final Object lock = new Object();
    private static int number = 1;
    private int maxNumber;
    private Predicate<Integer> validate;
    private Function<Integer, String> printer;

    public Exercise7_FizzBuzz(Predicate<Integer> validate, Function<Integer, String> printer, int maxNumber) {
        this.validate = validate;
        this.printer = printer;
        this.maxNumber = maxNumber;
    }

    public void run() {
        while (true) {
            synchronized (lock) {
                if (number > maxNumber) {
                    return;
                }
                if (validate.test(number)) {
                    System.out.println(printer.apply(number));
                    number++;
                }
            }
        }
    }

    public static void main(String[] args) {
        int maxNumber = 100;
        Thread[] threads = {
                new Exercise7_FizzBuzz(i -> i % 3 == 0 && i % 5 == 0, i -> "FizzBuzz", maxNumber),
                new Exercise7_FizzBuzz(i -> i % 3 == 0 && i % 5 != 0, i -> "Fizz", maxNumber),
                new Exercise7_FizzBuzz(i -> i % 3 != 0 && i % 5 == 0, i -> "Buzz", maxNumber),
                new Exercise7_FizzBuzz(i -> i % 3 != 0 && i % 5 != 0, i -> Integer.toString(i), maxNumber)
        };

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
