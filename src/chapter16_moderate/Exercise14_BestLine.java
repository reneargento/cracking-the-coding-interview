package chapter16_moderate;

import util.HashMapList;

import java.util.List;

/**
 * Created by Rene Argento on 28/03/20.
 */
public class Exercise14_BestLine {

    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static class Line {
        public double slope;
        public double intercept;
        private boolean infiniteSlope;
        public static final double EPSILON = .0001;

        public Line(Point point1, Point point2) {
            if (Math.abs(point1.x - point2.x) >  EPSILON) {
                slope = (point1.y - point2.y) / (point1.x - point2.x);
                // y = m * x + b (where m is the slope and b is the y-intercept)
                // b = y - m * x
                intercept = point1.y - slope * point1.x;
            } else {
                infiniteSlope = true;
                intercept = point1.x; // x-intercept, since slope is infinite
            }
        }

        public static double floorToNearestEpsilon(double doubleValue) {
            int divideResult = (int) (doubleValue / EPSILON);
            return ((double) divideResult) * EPSILON;
        }

        public boolean isEquivalent(double double1, double double2) {
            return Math.abs(double1 - double2) < EPSILON;
        }

        public boolean isEquivalent(Line line) {
            return isEquivalent(slope, line.slope) && isEquivalent(intercept, line.intercept)
                    && (infiniteSlope == line.infiniteSlope);
        }
    }

    // O(n^2) runtime, where n is the number of points
    // O(n) space
    public static Line getBestLine(Point[] points) {
        if (points == null || points.length == 0) {
            return null;
        }
        if (points.length == 1) {
            return new Line(points[0], new Point(0, 0));
        }
        HashMapList<Double, Line> linesBySlopeMap = getLinesBySlopeMap(points);
        return getBestLine(linesBySlopeMap);
    }

    // Add each pair of points as a line to the map.
    private static HashMapList<Double, Line> getLinesBySlopeMap(Point[] points) {
        HashMapList<Double, Line> linesBySlopeMap = new HashMapList<>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Line line = new Line(points[i], points[j]);
                double flooredSlope = Line.floorToNearestEpsilon(line.slope);
                linesBySlopeMap.put(flooredSlope, line);
            }
        }
        return linesBySlopeMap;
    }

    private static Line getBestLine(HashMapList<Double, Line> linesBySlopeMap) {
        Line bestLine = null;
        int bestCount = 0;

        for (double slope : linesBySlopeMap.keySet()) {
            List<Line> lines = linesBySlopeMap.get(slope);

            for (Line line : lines) {
                // Count lines that are equivalent to current line.
                int count = countEquivalentLines(linesBySlopeMap, line);
                if (count > bestCount) {
                    bestCount = count;
                    bestLine = line;
                }
            }
        }
        return bestLine;
    }

    // Check hashmap for lines that are equivalent to a given line.
    // Check one epsilon above and below the actual slope to compensate for floating point errors.
    private static int countEquivalentLines(HashMapList<Double, Line> linesBySlopeMap, Line line) {
        double key = Line.floorToNearestEpsilon(line.slope);
        int count = countEquivalentLines(linesBySlopeMap.get(key), line);
        count += countEquivalentLines(linesBySlopeMap.get(key - Line.EPSILON), line);
        count += countEquivalentLines(linesBySlopeMap.get(key + Line.EPSILON), line);
        return count;
    }

    private static int countEquivalentLines(List<Line> lines, Line line) {
        if (lines == null) {
            return 0;
        }
        int count = 0;
        for (Line parallelLine : lines) {
            if (line.isEquivalent(parallelLine)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Point[] points = createPoints();
        Line line = getBestLine(points);
        System.out.println("Number of times the best line appears: " + validate(line, points));
        System.out.println("Expected: 1225");
    }

    private static Point[] createPoints() {
        int numberOfPoints = 100;
        System.out.println("Points on the graph\n***************");
        Point[] points = new Point[numberOfPoints - 1];
        for (int i = 0; i < numberOfPoints / 2; i++) {
            Point point = new Point(i, 2.3 * ((double)i) + 20.0);
            points[i] = point;
            System.out.println(point);
        }
        for (int i = 0; i < numberOfPoints / 2 - 1; i++) {
            Point point = new Point(i, 3.0 * ((double)i) + 1.0);
            points[numberOfPoints / 2 + i] = point;
            System.out.println(point);
        }
        System.out.println("****************\n");
        return points;
    }

    private static int validate(Line line, Point[] points) {
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Line otherLine = new Line(points[i], points[j]);
                if (line.isEquivalent(otherLine)) {
                    count++;
                }
            }
        }
        return count;
    }

}
