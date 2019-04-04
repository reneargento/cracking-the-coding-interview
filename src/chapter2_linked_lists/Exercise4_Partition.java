package chapter2_linked_lists;

/**
 * Created by Rene Argento on 30/03/19.
 */
public class Exercise4_Partition {

    // O(n) runtime
    // O(1) space
    public static Node partitionList(Node head, int partition) {
        Node newHead = head;
        Node tail = head;

        Node current = head;

        while (current != null) {
            Node nextNode = current.next;
            if (current.value < partition) {
                current.next = newHead;
                newHead = current;
            } else {
                tail.next = current;
                tail = current;
            }
            current = nextNode;
        }

        tail.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        Node head = new Node(7);
        head.next = new Node(1);
        head.next.next = new Node(3);
        head.next.next.next = new Node(9);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(1);
        head.next.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next.next = new Node(2);

        System.out.print("Original list: ");
        printList(head);

        head = partitionList(head, 5);

        System.out.print("\nPartitioned list: ");
        printList(head);

        System.out.println("\nExpected: 2 1 3 1 7 9 5 6");
    }

    private static void printList(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }

}
