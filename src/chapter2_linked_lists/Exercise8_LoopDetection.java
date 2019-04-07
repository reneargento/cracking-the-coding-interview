package chapter2_linked_lists;

/**
 * Created by Rene Argento on 05/04/19.
 */
public class Exercise8_LoopDetection {

    // O(n) runtime
    // O(1) space
    public static Node detectLoop(Node head) {
        if (head == null) {
            return null;
        }

        Node slow = head;
        Node fast = head;

        // 1- Turtle and hare move until they meet
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                break;
            }
        }

        if (fast == null || fast.next == null) {
            // There is no loop
            return null;
        }

        // 2- Return turtle to the beginning and move both at same pace until meeting again
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        // List: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 3
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node3; // loop

        Node loopNode = detectLoop(node1);
        if (loopNode != null) {
            System.out.println("Loop node value: " + loopNode.value);
        } else {
            System.out.println("There is no loop");
        }
        System.out.println("Expected: 3");
    }

}
