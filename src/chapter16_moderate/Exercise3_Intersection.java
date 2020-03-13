package chapter16_moderate;

/**
 * Created by Rene Argento on 12/03/20.
 */
public class Exercise3_Intersection {

    public class IntersectionPoint {

        public class Point {
            public double x, y;

            public Point(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }

        public class Line {
            public Point start, end;
            public double slope, yIntercept;

            public Line(Point start, Point end) {
                this.start = start;
                this.end = end;

                if (start.x == end.x) { // Vertical line
                    slope = Double.POSITIVE_INFINITY;
                    yIntercept = Double.POSITIVE_INFINITY;
                } else {
                    slope = (end.y - start.y) / (end.x - start.x);
                    yIntercept = end.y - slope * end.x;
                }
            }

            public boolean isVertical() {
                return slope == Double.POSITIVE_INFINITY;
            }

            public double getYIntersectionFromXIntersection(double x) {
                if (isVertical()) {
                    return Double.POSITIVE_INFINITY;
                }
                return slope * x + yIntercept;
            }
        }

        // Checks if middle is between start and end
        private boolean isBetween(double start, double middle, double end) {
            if (start > end) {
                return end <= middle && middle <= start;
            } else {
                return start <= middle && middle <= end;
            }
        }

        private boolean isBetween(Point start, Point middle, Point end) {
            return isBetween(start.x, middle.x, end.x) && isBetween(start.y, middle.y, end.y);
        }

        // O(1) runtime
        // O(1) space
        public Point intersection(Point start1, Point end1, Point start2, Point end2) {
            Line line1 = new Line(start1, end1);
            Line line2 = new Line(start2, end2);

            // Parallel lines only intercept if they have the same y-intercept (are the same line) and if the start or
            // the end of one line is within the other line.
            if (line1.slope == line2.slope) { // Parallel lines
                if (line1.yIntercept != line2.yIntercept) return null;

                if (isBetween(start1, start2, end1)) return start2;
                if (isBetween(start1, end2, end1)) return end2;
                if (isBetween(start2, start1, end2)) return start1;
                if (isBetween(start2, end1, end2)) return end1;
                return null;
            }

            // Compute intersection point for infinite lines
            double xIntersection;
            if (line1.isVertical() || line2.isVertical()) {
                // A vertical line is not described by the y = m * x + b equation, it is described by the x = x1 equation
                xIntersection = line1.isVertical() ? line1.start.x : line2.start.x;
            } else { // Set y = m * x + b equations equal and solve for x (where m is the slope and b is the y-intercept)
                xIntersection = (line2.yIntercept - line1.yIntercept) / (line1.slope - line2.slope);
            }
            // Compute y intersection point using a non-vertical line
            double yIntersection = line1.isVertical() ? line2.getYIntersectionFromXIntersection(xIntersection) :
                    line1.getYIntersectionFromXIntersection(xIntersection);
            Point intersection = new Point(xIntersection, yIntersection);

            // Check if intersection point is within both line segments
            if (isBetween(start1, intersection, end1) && isBetween(start2, intersection, end2)) {
                return intersection;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        IntersectionPoint intersectionPoint = new Exercise3_Intersection().new IntersectionPoint();

        IntersectionPoint.Point point1Start = intersectionPoint.new Point(2, 2);
        IntersectionPoint.Point point1End = intersectionPoint.new Point(2, 6);
        IntersectionPoint.Point point2Start = intersectionPoint.new Point(0, 3);
        IntersectionPoint.Point point2End = intersectionPoint.new Point(4, 3);
        IntersectionPoint.Point intersectionPoint1 =
                intersectionPoint.intersection(point1Start, point1End, point2Start, point2End);
        if (intersectionPoint1 != null) {
            System.out.println("Intersection point: " + intersectionPoint1.x + ", " + intersectionPoint1.y);
        } else {
            System.out.println("No intersection");
        }
        System.out.println("Expected: 2.0, 3.0\n");

        IntersectionPoint.Point point3Start = intersectionPoint.new Point(0, 5);
        IntersectionPoint.Point point3End = intersectionPoint.new Point(4, 7);
        IntersectionPoint.Point intersectionPoint2 =
                intersectionPoint.intersection(point1Start, point1End, point3Start, point3End);
        if (intersectionPoint2 != null) {
            System.out.println("Intersection point: " + intersectionPoint2.x + ", " + intersectionPoint2.y);
        } else {
            System.out.println("No intersection");
        }
        System.out.println("Expected: 2.0, 6.0\n");

        IntersectionPoint.Point point4Start = intersectionPoint.new Point(0, 1);
        IntersectionPoint.Point point4End = intersectionPoint.new Point(1, 3);
        IntersectionPoint.Point intersectionPoint3 =
                intersectionPoint.intersection(point1Start, point1End, point4Start, point4End);
        if (intersectionPoint3 != null) {
            System.out.println("Intersection point: " + intersectionPoint3.x + ", " + intersectionPoint3.y);
        } else {
            System.out.println("No intersection");
        }
        System.out.println("Expected: No intersection");
    }
}
