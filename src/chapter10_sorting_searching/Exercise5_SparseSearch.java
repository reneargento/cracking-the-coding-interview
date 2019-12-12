package chapter10_sorting_searching;

/**
 * Created by Rene Argento on 08/12/19.
 */
public class Exercise5_SparseSearch {

    // O(n) runtime, where n is the number of strings in the array
    // O(n) space
    public static int sparseSearch(String[] array, String value) {
        if (array == null || value == null || value.equals("")) {
            return -1;
        }
        return sparseSearch(array, value, 0, array.length - 1);
    }

    private static int sparseSearch(String[] array, String value, int low, int high) {
        if (low > high) {
            return -1;
        }

        int middle = low + (high - low) / 2;

        if (array[middle].isEmpty()) {
            int left = middle - 1;
            int right = middle + 1;

            while (true) {
                if (left < low && right > high) {
                    return -1;
                } else if (left >= low && !array[left].isEmpty()) {
                    middle = left;
                    break;
                } else if (right <= high && !array[right].isEmpty()) {
                    middle = right;
                    break;
                }
                left--;
                right++;
            }
        }

        int compare = array[middle].compareTo(value);
        if (compare > 0) {
            return sparseSearch(array, value, low, middle - 1);
        } else if (compare < 0) {
            return sparseSearch(array, value, middle + 1, high);
        } else {
            return middle;
        }
    }

    public static void main(String[] args) {
        String[] array = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};

        int index1 = sparseSearch(array, "ball");
        System.out.println("Index: " + index1 + " Expected: 4");

        int index2 = sparseSearch(array, "at");
        System.out.println("Index: " + index2 + " Expected: 0");

        int index3 = sparseSearch(array, "dad");
        System.out.println("Index: " + index3 + " Expected: 10");

        int index4 = sparseSearch(array, "rene");
        System.out.println("Index: " + index4 + " Expected: -1");
    }

}
