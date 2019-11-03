package chapter8_recursion_dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 17/10/19.
 */
public class Exercise13_StackOfBoxes {

    private static class Box {
        private long width;
        private long height;
        private long depth;

        Box(long width, long height, long depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }

        public boolean canBeAbove(Box otherBox) {
            return height < otherBox.height
                    && width < otherBox.width
                    && depth < otherBox.depth;
        }
    }

    // O(n^2) runtime, where n is the number of boxes
    // O(n) space
    public static long getMaxHeight(List<Box> boxes) {
        boxes.sort((box1, box2) -> Long.compare(box2.height, box1.height));

        long maxHeight = 0;
        long[] heightsWithBottomBox = new long[boxes.size()];

        // Check each box as the base box
        for (int i = 0; i < boxes.size(); i++) {
            long height = getMaxHeight(boxes, i, heightsWithBottomBox);
            maxHeight = Math.max(height, maxHeight);
        }
        return maxHeight;
    }

    private static long getMaxHeight(List<Box> boxes, int bottomIndex, long[] heightsWithBottomBox) {
        if (heightsWithBottomBox[bottomIndex] > 0) {
            return heightsWithBottomBox[bottomIndex];
        }

        Box bottomBox = boxes.get(bottomIndex);
        long maxHeight = 0;

        for (int i = bottomIndex + 1; i < boxes.size(); i++) {
            if (boxes.get(i).canBeAbove(bottomBox)) {
                long height = getMaxHeight(boxes, i, heightsWithBottomBox);
                maxHeight = Math.max(height, maxHeight);
            }
        }

        maxHeight += bottomBox.height;
        heightsWithBottomBox[bottomIndex] = maxHeight;
        return maxHeight;
    }

    // O(n * lg(n)) runtime, where n is the number of boxes
    // O(n) space
    public static long getMaxHeight2(List<Box> boxes) {
        boxes.sort((box1, box2) -> Long.compare(box2.height, box1.height));
        long[] heightsWithBottomBox = new long[boxes.size()];
        return getMaxHeight2(boxes, null, 0, heightsWithBottomBox);
    }

    private static long getMaxHeight2(List<Box> boxes, Box bottomBox, int offset, long[] heightsWithBottomBox) {
        // Base case
        if (offset >= boxes.size()) {
            return 0;
        }

        // Height with bottom box
        Box newBottomBox = boxes.get(offset);
        long heightWithBottomBox = 0;
        if (bottomBox == null || newBottomBox.canBeAbove(bottomBox)) {
            if (heightsWithBottomBox[offset] == 0) {
                heightsWithBottomBox[offset] = getMaxHeight2(boxes, newBottomBox, offset + 1, heightsWithBottomBox);
                heightsWithBottomBox[offset] += newBottomBox.height;
            }
            heightWithBottomBox = heightsWithBottomBox[offset];
        }

        // Height without bottom box
        long heightWithoutBottomBox = getMaxHeight2(boxes, bottomBox, offset + 1, heightsWithBottomBox);

        return Math.max(heightWithBottomBox, heightWithoutBottomBox);
    }

    public static void main(String[] args) {
        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box(4, 3, 5));
        boxes.add(new Box(4, 4, 2));
        boxes.add(new Box(2, 2, 2));
        boxes.add(new Box(1, 4, 3));
        boxes.add(new Box(2, 1, 3));

        long maxHeight1 = getMaxHeight(boxes);
        System.out.println("Max height method 1: " + maxHeight1 + " Expected: 5");

        long maxHeight2 = getMaxHeight2(boxes);
        System.out.println("Max height method 2: " + maxHeight2 + " Expected: 5");
    }

}

