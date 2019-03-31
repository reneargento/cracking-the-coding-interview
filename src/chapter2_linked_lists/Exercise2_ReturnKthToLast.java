package chapter2_linked_lists;

/**
 * Created by Rene Argento on 25/03/19.
 */
public class Exercise2_ReturnKthToLast {

    // O(n) runtime, 1 pass
    // O(1) space
    public static Node kthToLast2(Node head, int k) {
        Node current = head;
        Node runner = head;

        for (int i = 1; i <= k; i++) {
            if (runner != null) {
                runner = runner.next;
            } else {
                System.out.println("Invalid index");
                return null;
            }
        }

        while (runner != null) {
            current = current.next;
            runner = runner.next;
        }

        return current;
    }

    // O(n) runtime, 2 passes
    // O(1) space
    public static Node kthToLast1(Node head, int k) {
        int length = 0;

        Node current = head;

        while (current != null) {
            length++;
            current = current.next;
        }

        if (k > length) {
            System.out.println("Invalid index");
            return null;
        }

        Node newCurrent = head;

        int targetIndex = length - k;
        for (int i = 0; i < targetIndex; i++) {
            newCurrent = newCurrent.next;
        }

        return newCurrent;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next = new Node(7);
        head.next.next.next.next.next.next.next = new Node(8);

        System.out.println("8th to last node: " + kthToLast1(head, 8).value);
        System.out.println("8th to last node: " + kthToLast2(head, 8).value);
        System.out.println("Expected: 1");

        System.out.println("\n4th to last node: " + kthToLast1(head, 4).value);
        System.out.println("4th to last node: " + kthToLast2(head, 4).value);
        System.out.println("Expected: 5");

        System.out.println("\n1st to last node: " + kthToLast1(head, 1).value);
        System.out.println("1st to last node: " + kthToLast2(head, 1).value);
        System.out.println("Expected: 8");
    }

}
