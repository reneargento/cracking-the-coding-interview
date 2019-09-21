package chapter8_recursion_dynamic_programming;

/**
 * Created by Rene Argento on 13/09/19.
 */
public class Exercise3_MagicIndex {

    // O(lg(n)) runtime
    // O(1) space
    private static int magicIndexDistinct(int[] array) {
        return magicIndexDistinct(array, 0, array.length - 1);
    }

    private static int magicIndexDistinct(int[] array, int low, int high) {
        if (low > high) {
            return -1;
        }

        int middle = low + (high - low) / 2;

        if (array[middle] == middle) {
            return middle;
        } else if (array[middle] < middle) {
            return magicIndexDistinct(array, middle + 1, high);
        } else {
            return magicIndexDistinct(array, low, middle - 1);
        }
    }

    // O(n) runtime
    // O(1) space
    private static int magicIndexNonDistinct(int[] array) {
        return magicIndexNonDistinct(array, 0, array.length - 1);
    }

    private static int magicIndexNonDistinct(int[] array, int low, int high) {
        if (low > high) {
            return -1;
        }

        int middle = low + (high - low) / 2;

        if (array[middle] == middle) {
            return middle;
        }

        int leftIndex = Math.min(middle - 1, array[middle]);
        int left = magicIndexNonDistinct(array, low, leftIndex);
        if (left != -1) {
            return left;
        }

        int rightIndex = Math.max(middle + 1, array[middle]);
        return magicIndexNonDistinct(array, rightIndex, high);
    }

    public static void main(String[] args) {
        int[] array1 = {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
        int magicIndex1 = magicIndexDistinct(array1);
        System.out.println("Magic index distinct: " + magicIndex1 + " Expected: 2");

        int[] array2 = {-10, -2, 0, 1, 2, 3, 5, 6, 8, 10, 11};
        int magicIndex2 = magicIndexDistinct(array2);
        System.out.println("Magic index distinct: " + magicIndex2 + " Expected: 8");

        int[] array3 = {1, 2, 3, 4, 5, 6};
        int magicIndex3 = magicIndexDistinct(array3);
        System.out.println("Magic index distinct: " + magicIndex3 + " Expected: -1");

        int[] array4 = {0, 2, 5, 5, 5, 5, 7};
        int magicIndex4 = magicIndexNonDistinct(array4);
        System.out.println("\nMagic index non distinct: " + magicIndex4 + " Expected: 0");

        int[] array5 = {5, 5, 5, 5, 5, 5};
        int magicIndex5 = magicIndexNonDistinct(array5);
        System.out.println("Magic index non distinct: " + magicIndex5 + " Expected: 5");

        int[] array6 = {-2, 0, 2, 20, 21, 22, 23};
        int magicIndex6 = magicIndexNonDistinct(array6);
        System.out.println("Magic index non distinct: " + magicIndex6 + " Expected: 2");
    }

}
