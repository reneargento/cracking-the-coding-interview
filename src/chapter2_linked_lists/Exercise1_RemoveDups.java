package chapter2_linked_lists;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 24/03/19.
 */
public class Exercise1_RemoveDups {

    // O(n) runtime
    // O(n) space
    public static void removeDupsLinearSpace(Node node) {
        Set<Integer> valuesSeen = new HashSet<>();
        Node previous = null;

        while (node != null) {
            if (valuesSeen.contains(node.value)) {
                previous.next = node.next;
            } else {
                previous = node;
                valuesSeen.add(node.value);
            }
            node = node.next;
        }
    }

    // O(n^2) runtime
    // O(1) space
    public static void removeDups(Node head) {
        Node currentNode = head;

        while (currentNode != null) {
            Node runnerNode = currentNode;

            while (runnerNode.next != null) {
                if (runnerNode.next.value == currentNode.value) {
                    runnerNode.next = runnerNode.next.next;
                } else {
                    runnerNode = runnerNode.next;
                }
            }
            currentNode = currentNode.next;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(2);
        head.next.next.next.next.next = new Node(1);
        head.next.next.next.next.next.next = new Node(4);
        head.next.next.next.next.next.next.next = new Node(4);

        removeDups(head);
        System.out.print("List: ");
        printList(head);
        System.out.println("\nExpected: 1 2 3 4");
    }

    private static void printList(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
