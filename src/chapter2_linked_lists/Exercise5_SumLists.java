package chapter2_linked_lists;

/**
 * Created by Rene Argento on 31/03/19.
 */
public class Exercise5_SumLists {

    // O(n) runtime
    // O(n) space
    // Recursive version
    public static Node sumListsInverseOrder(Node head1, Node head2, int carryOver) {
        if (head1 == null && head2 == null && carryOver == 0) {
            return null;
        }

        int value = carryOver;
        if (head1 != null) {
            value += head1.value;
        }
        if (head2 != null) {
            value += head2.value;
        }

        Node sumNode = new Node(value % 10);
        if (head1 != null || head2 != null) {
            sumNode.next = sumListsInverseOrder(head1 != null ? head1.next : null,
                    head2 != null ? head2.next : null,
                    value >= 10 ? 1 : 0);
        }

        return sumNode;
    }

    // O(n) runtime
    // O(n) space
    // Iterative version
    public static Node sumListsInverseOrder(Node head1, Node head2) {
        Node current1 = head1;
        Node current2 = head2;
        Node result = null;
        Node currentResult = null;

        boolean carryOver = false;

        while (current1 != null && current2 != null) {
            int value1 = current1.value;
            int value2 = current2.value;
            int sum = value1 + value2;

            if (carryOver) {
                sum++;
            }

            if (sum > 9) {
                carryOver = true;
                sum = sum - 10;
            } else {
                carryOver = false;
            }

            Node sumNode = new Node(sum);

            if (result == null) {
                result = sumNode;
                currentResult = sumNode;
            } else {
                currentResult.next = sumNode;
                currentResult = sumNode;
            }
            current1 = current1.next;
            current2 = current2.next;
        }

        Node remainingNodes = null;
        if (current1 != null) {
            remainingNodes = current1;
        } else if (current2 != null) {
            remainingNodes = current2;
        }

        while (remainingNodes != null) {
            Node sumNode = new Node(remainingNodes.value);

            if (carryOver) {
                sumNode.value = sumNode.value + 1;
                carryOver = false;
            }

            if (result == null) {
                result = sumNode;
                currentResult = sumNode;
            } else {
                currentResult.next = sumNode;
                currentResult = sumNode;
            }
            remainingNodes = remainingNodes.next;
        }

        return result;
    }

    // FOLLOW UP section

    private static class PartialSum {
        Node sum;
        int carryOver;
    }

    // O(n) runtime
    // O(n) space
    public static Node sumListsForwardOrderRecursive(Node head1, Node head2) {
        int length1 = length(head1);
        int length2 = length(head2);

        if (length1 < length2) {
            head1 = padList(head1, length2 - length1);
        } else if (length2 < length1) {
            head2 = padList(head2, length1 - length2);
        }

        PartialSum partialResult = addLists(head1, head2);
        Node result = partialResult.sum;

        if (partialResult.carryOver != 0) {
            result = insertBefore(result, partialResult.carryOver);
        }

        return result;
    }

    private static PartialSum addLists(Node head1, Node head2) {
        if (head1 == null && head2 == null) {
            return new PartialSum();
        }

        PartialSum partialSum = addLists(head1.next, head2.next);

        int value = head1.value + head2.value + partialSum.carryOver;

        partialSum.sum = insertBefore(partialSum.sum, value % 10);
        partialSum.carryOver = value / 10;
        return partialSum;
    }

    private static int length(Node head) {
        int length = 0;

        while (head != null) {
            length++;
            head = head.next;
        }

        return length;
    }

    private static Node padList(Node head, int padding) {
        for (int i = 0; i < padding; i++) {
            head = insertBefore(head, 0);
        }
        return head;
    }

    private static Node insertBefore(Node head, int value) {
        Node newHead = new Node(value);
        newHead.next = head;
        return newHead;
    }

    // O(n) runtime
    // O(n) space
    public static Node sumListsForwardOrder(Node head1, Node head2) {
        head1 = inverseList(head1);
        head2 = inverseList(head2);
        Node result = sumListsInverseOrder(head1, head2);
        return inverseList(result);
    }

    private static Node inverseList(Node head) {
        Node current = head;
        Node newHead = head;
        boolean isFirst = true;

        while (current != null) {
            Node next = current.next;

            if (isFirst) {
                isFirst = false;
                current.next = null;
            } else {
                current.next = newHead;
            }

            newHead = current;
            current = next;
        }

        return newHead;
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);

        Node head2 = new Node(6);
        head2.next = new Node(7);
        head2.next.next = new Node(8);

        // 54321 + 876 = 55197
        Node result1 = sumListsInverseOrder(head1, head2);
        System.out.print("Result 1: ");
        printList(result1);
        System.out.println("\nExpected: 79155");

        Node result2 = sumListsInverseOrder(head1, head2, 0);
        System.out.print("\nResult 2: ");
        printList(result2);
        System.out.println("\nExpected: 79155");

        Node head3 = new Node(5);
        head3.next = new Node(4);
        head3.next.next = new Node(3);
        head3.next.next.next = new Node(2);
        head3.next.next.next.next = new Node(1);

        Node head4 = new Node(8);
        head4.next = new Node(7);
        head4.next.next = new Node(6);

        Node result3 = sumListsForwardOrder(head3, head4);
        System.out.print("\nResult 3: ");
        printList(result3);
        System.out.println("\nExpected: 55197");

        Node head5 = new Node(5);
        head5.next = new Node(4);
        head5.next.next = new Node(3);
        head5.next.next.next = new Node(2);
        head5.next.next.next.next = new Node(1);

        Node head6 = new Node(8);
        head6.next = new Node(7);
        head6.next.next = new Node(6);

        Node result4 = sumListsForwardOrderRecursive(head5, head6);
        System.out.print("\nResult 4: ");
        printList(result4);
        System.out.println("\nExpected: 55197");
    }

    private static void printList(Node head) {
        while (head != null) {
            System.out.print(head.value);
            head = head.next;
        }
    }

}
