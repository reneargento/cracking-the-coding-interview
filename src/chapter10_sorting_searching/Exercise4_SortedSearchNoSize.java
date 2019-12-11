package chapter10_sorting_searching;

/**
 * Created by Rene Argento on 06/12/19.
 */
public class Exercise4_SortedSearchNoSize {

    private static class Listy {
        private int[] values;

        Listy(int[] values) {
            this.values = values;
        }

        public int elementAt(int index) {
            if (index < 0 || index >= values.length) {
                return -1;
            }
            return values[index];
        }
    }

    // O(lg n) runtime, where n is the number of elements in the array
    // O(1) space
    public static int searchNoSize(Listy listy, int value) {
        int index = 1;
        while (listy.elementAt(index) != -1 && listy.elementAt(index) < value) {
            index *= 2;
        }
        return binarySearch(listy, value,index / 2, index);
    }

    private static int binarySearch(Listy listy, int value, int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int element = listy.elementAt(middle);

            if (element == -1 || value < element) {
                high = middle - 1;
            } else if (value > element) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 10, 20, 30, 50, 100, 200, 300, 1000};
        Listy listy = new Listy(array);

        int searchResult1 = searchNoSize(listy, 10);
        System.out.println("Index: " + searchResult1 + " Expected: " + 5);

        int searchResult2 = searchNoSize(listy, 1);
        System.out.println("Index: " + searchResult2 + " Expected: " + 0);

        int searchResult3 = searchNoSize(listy, 1000);
        System.out.println("Index: " + searchResult3 + " Expected: " + 12);

        int searchResult4 = searchNoSize(listy, 9999);
        System.out.println("Index: " + searchResult4 + " Expected: " + -1);
    }

}
