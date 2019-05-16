package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 30/04/19.
 */
public class Exercise6_Successor {

    public static class TreeNodeParentPointer<Item> {
        private TreeNodeParentPointer<Item> left;
        private TreeNodeParentPointer<Item> right;
        private TreeNodeParentPointer<Item> parent;
        private Item value;

        TreeNodeParentPointer(Item value) {
            this.value = value;
        }
    }

    // O(n) runtime
    // O(1) space
    public static TreeNodeParentPointer<Integer> getSuccessor(TreeNodeParentPointer<Integer> node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return getLeftMostChild(node.right);
        } else {
            TreeNodeParentPointer<Integer> parent = node.parent;

            while (parent != null && parent.right == node) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    private static TreeNodeParentPointer<Integer> getLeftMostChild(TreeNodeParentPointer<Integer> node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    //         5
    //     3      7
    //   2  4   6
    // 1
    public static void main(String[] args) {
        TreeNodeParentPointer<Integer> treeNode5 = new TreeNodeParentPointer<>(5);
        TreeNodeParentPointer<Integer> treeNode3 = new TreeNodeParentPointer<>(3);
        TreeNodeParentPointer<Integer> treeNode7 = new TreeNodeParentPointer<>(7);
        TreeNodeParentPointer<Integer> treeNode2 = new TreeNodeParentPointer<>(2);
        TreeNodeParentPointer<Integer> treeNode4 = new TreeNodeParentPointer<>(4);
        TreeNodeParentPointer<Integer> treeNode1 = new TreeNodeParentPointer<>(1);
        TreeNodeParentPointer<Integer> treeNode6 = new TreeNodeParentPointer<>(6);

        treeNode5.left = treeNode3;
        treeNode5.right = treeNode7;
        treeNode3.left = treeNode2;
        treeNode3.right = treeNode4;
        treeNode2.left = treeNode1;
        treeNode7.left = treeNode6;

        treeNode3.parent = treeNode5;
        treeNode7.parent = treeNode5;
        treeNode2.parent = treeNode3;
        treeNode4.parent = treeNode3;
        treeNode1.parent = treeNode2;
        treeNode6.parent = treeNode7;

        System.out.println("Successor of 5: " + getSuccessor(treeNode5).value + " Expected: 6");
        System.out.println("Successor of 4: " + getSuccessor(treeNode4).value + " Expected: 5");
        System.out.println("Successor of 2: " + getSuccessor(treeNode2).value + " Expected: 3");
        System.out.println("Successor of 1: " + getSuccessor(treeNode1).value + " Expected: 2");
        System.out.println("Successor of 6: " + getSuccessor(treeNode6).value + " Expected: 7");

        System.out.print("Successor of 7: ");
        TreeNodeParentPointer<Integer> lastNodeSuccessor = getSuccessor(treeNode7);
        if (lastNodeSuccessor != null) {
            System.out.print(lastNodeSuccessor.value);
        } else {
            System.out.print("null");
        }
        System.out.println(" Expected: null");
    }

}
