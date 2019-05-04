package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 28/04/19.
 */
public class Exercise5_ValidateBST {

    // O(n) runtime
    // O(n) space
    public static boolean validateBST(TreeNode<Integer> root) {
        return validateBST(root, null, null);
    }

    public static boolean validateBST(TreeNode<Integer> treeNode, Integer minimum, Integer maximum) {
        if (treeNode == null) {
            return true;
        }

        if ((maximum != null && treeNode.value > maximum)
                || (minimum != null && treeNode.value <= minimum)) {
            return false;
        }

        return validateBST(treeNode.left, minimum, treeNode.value)
                && validateBST(treeNode.right, treeNode.value, maximum);
    }

    public static void main(String[] args) {
        TreeNode<Integer> treeNode5 = new TreeNode<>(5);
        TreeNode<Integer> treeNode3 = new TreeNode<>(3);
        TreeNode<Integer> treeNode7 = new TreeNode<>(7);
        TreeNode<Integer> treeNode2 = new TreeNode<>(2);
        TreeNode<Integer> treeNode4 = new TreeNode<>(4);
        TreeNode<Integer> treeNode1 = new TreeNode<>(1);

        treeNode5.left = treeNode3;
        treeNode5.right = treeNode7;
        treeNode3.left = treeNode2;
        treeNode3.right = treeNode4;
        treeNode2.left = treeNode1;
        System.out.println("Validate BST: " + validateBST(treeNode5) + " Expected: true");

        treeNode7.left = new TreeNode<>(0);
        System.out.println("Validate BST: " + validateBST(treeNode5) + " Expected: false");
    }

}
