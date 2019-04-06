package chapter2_linked_lists;

/**
 * Created by Rene Argento on 02/04/19.
 */
public class Exercise6_Palindrome {

    private static class Result {
        private boolean isPalindrome;
        private Node nextNode;

        Result(boolean isPalindrome, Node nextNode) {
            this.isPalindrome = isPalindrome;
            this.nextNode = nextNode;
        }
    }

    // O(n) runtime
    // O(n) space
    public static boolean isPalindrome(Node head) {
        return isPalindrome(head, head).isPalindrome;
    }

    private static Result isPalindrome(Node node, Node head) {
        if (node == null) {
            return new Result(true, null);
        }

        Result result = isPalindrome(node.next, head);

        if (!result.isPalindrome) {
            return new Result(false, null);
        }

        boolean isPalindrome;
        Node nextNode;

        if (node.next == null) {
            isPalindrome = node.value == head.value;
            nextNode = head.next;
        } else {
            isPalindrome = node.value == result.nextNode.value;
            nextNode = result.nextNode.next;
        }

        return new Result(isPalindrome, nextNode);
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(3);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(1);

        System.out.print("Is palindrome: ");
        System.out.print(isPalindrome(head1));
        System.out.println("\nExpected: true");

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(1);

        System.out.print("\nIs palindrome: ");
        System.out.print(isPalindrome(head2));
        System.out.println("\nExpected: false");
    }
}