package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 25/05/19.
 */
public class Exercise10_CheckSubtree {

    // Checks if tree2 is a subtree of tree1
    // O(n * m) runtime, where n is the number of nodes in tree1 and m is the number of nodes in tree2
    // O(n) space
    public static boolean checkSubtree(TreeNode<Integer> tree1, TreeNode<Integer> tree2) {
        if (tree1 == null) {
            return false;
        }

        if (tree2 == null) {
            return true;
        }

        if (tree1.value.equals(tree2.value) && compareTrees(tree1, tree2)) {
            return true;
        }

        return checkSubtree(tree1.left, tree2) || checkSubtree(tree1.right, tree2);
    }

    private static boolean compareTrees(TreeNode<Integer> tree1, TreeNode<Integer> tree2) {
        if (tree1 == null && tree2 == null) {
            return true;
        }

        if (tree1 == null || tree2 == null) {
            return false;
        }

        if (!tree1.value.equals(tree2.value)) {
            return false;
        }

        return compareTrees(tree1.left, tree2.left) && compareTrees(tree1.right, tree2.right);
    }

    // Tree 1
    //          5
    //     3        7
    //   2  4    6    10
    // 1            8   14

    // Tree 2
    //    7
    // 6    10
    //    8   14

    // Tree 3
    //    7
    // 5    10
    //    8   14
    public static void main(String[] args) {
        // Trees 1 and 2
        TreeNode<Integer> treeNode5 = new TreeNode<>(5);
        TreeNode<Integer> treeNode3 = new TreeNode<>(3);
        TreeNode<Integer> treeNode7 = new TreeNode<>(7);
        TreeNode<Integer> treeNode2 = new TreeNode<>(2);
        TreeNode<Integer> treeNode4 = new TreeNode<>(4);
        TreeNode<Integer> treeNode6 = new TreeNode<>(6);
        TreeNode<Integer> treeNode1 = new TreeNode<>(1);
        TreeNode<Integer> treeNode10 = new TreeNode<>(10);
        TreeNode<Integer> treeNode8 = new TreeNode<>(8);
        TreeNode<Integer> treeNode14 = new TreeNode<>(14);

        treeNode5.left = treeNode3;
        treeNode5.right = treeNode7;
        treeNode3.left = treeNode2;
        treeNode3.right = treeNode4;
        treeNode2.left = treeNode1;
        treeNode7.left = treeNode6;
        treeNode7.right = treeNode10;
        treeNode10.left = treeNode8;
        treeNode10.right = treeNode14;

        // Tree 3
        TreeNode<Integer> otherTreeNode7 = new TreeNode<>(7);
        TreeNode<Integer> otherTreeNode5 = new TreeNode<>(5);
        TreeNode<Integer> otherTreeNode10 = new TreeNode<>(10);
        TreeNode<Integer> otherTreeNode8 = new TreeNode<>(8);
        TreeNode<Integer> otherTreeNode14 = new TreeNode<>(14);

        otherTreeNode7.left = otherTreeNode5;
        otherTreeNode7.right = otherTreeNode10;
        otherTreeNode10.left = otherTreeNode8;
        otherTreeNode10.right = otherTreeNode14;

        System.out.println("Check subtree: " + checkSubtree(treeNode5, treeNode7) + " Expected: true");
        System.out.println("Check subtree: " + checkSubtree(treeNode5, otherTreeNode7) + " Expected: false");
    }

}
