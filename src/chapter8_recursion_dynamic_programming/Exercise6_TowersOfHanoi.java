package chapter8_recursion_dynamic_programming;

import java.util.Stack;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 21/09/19.
 */
public class Exercise6_TowersOfHanoi {

    public static void towersOfHanoi(int disks, Stack<Integer> origin, Stack<Integer> buffer, Stack<Integer> destination) {
        if (disks <= 0) {
            return;
        }

        // Move top disks - 1 disks from origin to buffer, using destination as a buffer
        towersOfHanoi(disks - 1, origin, destination, buffer);

        // Move top disk from origin to destination
        int topDisk = origin.pop();
        destination.push(topDisk);

        // Move top disks - 1 disks from buffer to destination, using origin as a buffer
        towersOfHanoi(disks - 1, buffer, origin, destination);
    }

    public static void main(String[] args) {
        Stack<Integer> origin = new Stack<>();
        origin.push(4);
        origin.push(3);
        origin.push(2);
        origin.push(1);
        Stack<Integer> buffer = new Stack<>();
        Stack<Integer> destination = new Stack<>();

        towersOfHanoi(origin.size(), origin, buffer, destination);

        System.out.print("Origin: ");
        printStack(origin);
        System.out.println("Expected:");

        System.out.print("\nBuffer: ");
        printStack(buffer);
        System.out.println("Expected:");

        System.out.print("\nDestination: ");
        printStack(destination);
        System.out.println("Expected: 1 2 3 4");
    }

    private static void printStack(Stack<Integer> stack) {
        StringJoiner disks = new StringJoiner(" ");

        while (!stack.isEmpty()) {
            int disk = stack.pop();
            disks.add(String.valueOf(disk));
        }

        System.out.println(disks);
    }

}
