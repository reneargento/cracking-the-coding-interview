package chapter2_linked_lists;

/**
 * Created by Rene Argento on 29/03/19.
 */
public class Exercise3_DeleteMiddleNode {

    // O(1) runtime
    // O(1) space
    public static void deleteMiddleNode(Node nodeToDelete) {
        if (nodeToDelete == null || nodeToDelete.next == null) {
            return;
        }

        nodeToDelete.value = nodeToDelete.next.value;
        nodeToDelete.next = nodeToDelete.next.next;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        Node middleNode = new Node(3);
        head.next.next = middleNode;
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        System.out.print("Original list: ");
        printList(head);

        deleteMiddleNode(middleNode);

        System.out.print("\nModified list: ");
        printList(head);

        System.out.println("\nExpected: 1 2 4 5");
    }

    private static void printList(Node head) {
        Node current = head;

        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
    }

}
