package chapter10_sorting_searching;

/**
 * Created by Rene Argento on 05/12/19.
 */
public class Exercise3_SearchInRotatedArray {

    // O(lg n) runtime, where n is the number of elements in the array - if all elements are unique
    // O(n) runtime - if elements are not unique
    // O(lg n) space
    public static int searchInRotatedArray(int[] array, int value) {
        return searchInRotatedArray(array, value, 0, array.length - 1);
    }

    private static int searchInRotatedArray(int[] array, int value, int low, int high) {
        if (low > high) {
            return -1;
        }

        int middle = low + (high - low) / 2;
        if (array[middle] == value) {
            return middle;
        }

        // Either left or right side is normally ordered.
        // Find the normally ordered side and use it to decide which side to search.
        if (array[low] < array[middle]) { // Left side is normally ordered.
            if (array[low] <= value && value < array[middle]) {
                return searchInRotatedArray(array, value, low, middle - 1);
            } else {
                return searchInRotatedArray(array, value, middle + 1, high);
            }
        } else if (array[middle] < array[high]) { // Right side is normally ordered.
            if (array[middle] < value && value <= array[high]) {
                return searchInRotatedArray(array, value, middle + 1, high);
            } else {
                return searchInRotatedArray(array, value, low, middle - 1);
            }
        } else if (array[low] == array[middle]) { // Left or right side is composed of only repeated values
            if (array[middle] != array[high]) { // If right value is different, search it
                return searchInRotatedArray(array, value, middle + 1, high);
            } else { // Else, we have to search both sides
                int result = searchInRotatedArray(array, value, low, middle - 1);
                if (result == -1) {
                    return searchInRotatedArray(array, value, middle + 1, high);
                } else {
                    return result;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array1 = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
        int index1 = searchInRotatedArray(array1, 5);
        System.out.println("Index: " + index1 + " Expected: 8");

        int[] array2 = {20, 21, 22, 23, 24, 25, 26, 27, 28, 5};
        int index2 = searchInRotatedArray(array2, 5);
        System.out.println("Index: " + index2 + " Expected: 9");

        int[] array3 = {20, 1, 2, 3, 4, 5, 6};
        int index3 = searchInRotatedArray(array3, 20);
        System.out.println("Index: " + index3 + " Expected: 0");
    }

}
