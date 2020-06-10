package chapter17_hard;

/**
 * Created by Rene Argento on 02/06/20.
 */
public class Exercise21_VolumeOfHistogram {

    // O(n) runtime, where n is the number of elements in the histogram
    // O(n) space
    public static long computeHistogramVolume(int[] histogram) {
        // Pre-compute max heights on the left
        int[] maxHeightLeft = new int[histogram.length];
        int currentMaxHeightLeft = histogram[0];
        for (int i = 0; i < histogram.length; i++) {
            currentMaxHeightLeft = Math.max(currentMaxHeightLeft, histogram[i]);
            maxHeightLeft[i] = currentMaxHeightLeft;
        }

        long volume = 0;

        // The water above each bar will be the difference between min(maxHeightLeft, maxHeightRight) and the bar's height
        int maxHeightRight = histogram[histogram.length - 1];
        for (int i = histogram.length - 1; i >= 0; i--) {
            maxHeightRight = Math.max(maxHeightRight, histogram[i]);

            int minLeftRightHeight = Math.min(maxHeightLeft[i], maxHeightRight);
            if (minLeftRightHeight > histogram[i]) {
                int water = minLeftRightHeight - histogram[i];
                volume += water;
            }
        }
        return volume;
    }

    public static void main(String[] args) {
        int[] histogram1 = {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0};
        long volume1 = computeHistogramVolume(histogram1);
        System.out.println("Volume: " + volume1 + " Expected: 26");

        int[] histogram2 = {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 2, 0, 1, 5};
        long volume2 = computeHistogramVolume(histogram2);
        System.out.println("Volume: " + volume2 + " Expected: 42");

        int[] histogram3 = {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 8, 0, 2, 0, 5, 2, 0, 3, 0, 0};
        long volume3 = computeHistogramVolume(histogram3);
        System.out.println("Volume: " + volume3 + " Expected: 46");
    }

}
