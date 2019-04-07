package chapter2_linked_lists;

/**
 * Created by Rene Argento on 04/04/19.
 */
public class Exercise7_Intersection {

    // O(a + b) runtime, where a is the length of list1 and b is the length of list2
    // O(1) space
    public static Node intersection(Node list1, Node list2) {
        if (list1 == null || list2 == null) {
            return null;
        }

        int length1 = listLength(list1);
        int length2 = listLength(list2);

        int lengthDifference = Math.abs(length1 - length2);

        Node longestList = length1 > length2 ? list1 : list2;
        Node shortestList = length1 > length2 ? list2 : list1;

        for (int i = 0; i < lengthDifference; i++) {
            longestList = longestList.next;
        }

        while (longestList != null && longestList.value != shortestList.value) {
            longestList = longestList.next;
            shortestList = shortestList.next;
        }

        // longestList will be null if there is no intersection
        return longestList;
    }

    private static int listLength(Node head) {
        int length = 0;

        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    public static void main(String[] args) {
        // List 1: 1 -> 2 -> 3 -> 90 -> 91 -> 92
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node90 = new Node(90);
        Node node91 = new Node(91);
        Node node92 = new Node(92);
        node1.next = node2;
        node2.next = node3;
        node3.next = node90;
        node90.next = node91;
        node91.next = node92;

        // List 2: 88 -> 89 -> 90 -> 91 -> 92
        Node node88 = new Node(88);
        Node node89 = new Node(89);
        node88.next = node89;
        node89.next = node90;

        Node intersectionNode = intersection(node1, node88);
        System.out.print("Intersection node value: ");
        if (intersectionNode != null) {
            System.out.println(intersectionNode.value);
        } else {
            System.out.println("No intersection");
        }
        System.out.println("Expected: 90");
    }
}
