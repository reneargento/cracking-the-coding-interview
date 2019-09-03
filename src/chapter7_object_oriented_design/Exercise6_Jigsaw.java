package chapter7_object_oriented_design;

import java.util.*;

/**
 * Created by Rene Argento on 23/08/19.
 */
public class Exercise6_Jigsaw {

    private static final int LEFT_INDEX = 0;
    private static final int TOP_INDEX = 1;
    private static final int RIGHT_INDEX = 2;
    private static final int BOTTOM_INDEX = 3;

    public enum Orientation {
        LEFT, TOP, RIGHT, BOTTOM;

        public Orientation getOpposite() {
            switch (this) {
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
                case TOP: return BOTTOM;
                case BOTTOM: return TOP;
                default: return null;
            }
        }
    }

    public enum Shape {
        INNER, OUTER, FLAT;

        public Shape getOpposite() {
            switch (this) {
                case INNER: return OUTER;
                case OUTER: return INNER;
                default: return null;
            }
        }
    }

    public class Edge {
        private Shape shape;
        private Piece parentPiece;

        public Edge(Shape shape) {
            this.shape = shape;
        }

        public boolean fitsWith(Edge edge) {
            Shape oppositeShape = shape.getOpposite();
            return oppositeShape != null && oppositeShape.equals(edge.getShape());
        }

        public Shape getShape() {
            return shape;
        }

        public Piece getParentPiece() {
            return parentPiece;
        }

        public void setParentPiece(Piece parentPiece) {
            this.parentPiece = parentPiece;
        }
    }

    public class Piece {
        private Map<Orientation, Edge> edges;

        public Piece(Edge[] edgeList) {
            edges = new HashMap<>();
            edges.put(Orientation.LEFT, edgeList[LEFT_INDEX]);
            edges.put(Orientation.TOP, edgeList[TOP_INDEX]);
            edges.put(Orientation.RIGHT, edgeList[RIGHT_INDEX]);
            edges.put(Orientation.BOTTOM, edgeList[BOTTOM_INDEX]);

            for (Edge edge : edgeList) {
                edge.setParentPiece(this);
            }
        }

        public void rotateEdgesBy(int numberRotations) {
            int effectiveRotations = numberRotations % 4;

            for (int i = 0; i < effectiveRotations; i++) {
                Edge previousLeftEdge = edges.get(Orientation.LEFT);
                edges.put(Orientation.LEFT, edges.get(Orientation.BOTTOM));

                Edge previousTopEdge = edges.get(Orientation.TOP);
                edges.put(Orientation.TOP, previousLeftEdge);

                Edge previousRightEdge = edges.get(Orientation.RIGHT);
                edges.put(Orientation.RIGHT, previousTopEdge);

                edges.put(Orientation.BOTTOM, previousRightEdge);
            }
        }

        public void setEdgeAsOrientation(Edge edge, Orientation orientation) {
            while (!edges.get(orientation).equals(edge)) {
                rotateEdgesBy(1);
            }
        }

        public boolean isCorner() {
            return getNumberOfFlatEdges() == 2;
        }

        public boolean isBorder() {
            return getNumberOfFlatEdges() == 1;
        }

        private int getNumberOfFlatEdges() {
            int numberOfFlatEdges = 0;

            for (Orientation orientation : edges.keySet()) {
                Edge edge = edges.get(orientation);

                if (edge.getShape().equals(Shape.FLAT)) {
                    numberOfFlatEdges++;
                }
            }
            return numberOfFlatEdges;
        }

        public Map<Orientation, Edge> getEdges() {
            return edges;
        }

        public Edge getEdgeWithOrientation(Orientation orientation) {
            return edges.get(orientation);
        }
    }

    public class Puzzle {
        private Set<Piece> pieces; // All pieces
        private Piece[][] solution;
        private int size;

        public Puzzle(int size, Set<Piece> pieces) {
            this.size = size;
            this.pieces = pieces;
        }

        // To solve the puzzle:
        // Step 1 - Group the pieces into corner pieces, border pieces and inside pieces.
        // Step 2 - Pick an arbitrary corner piece and put it in the top left corner.
        // Step 3 - Walk through the puzzle in order, filling in piece by piece.
        // At each location, search through the correct group of pieces to find the matching piece.
        // Step 4 - Insert the piece into the puzzle, rotating it if needed.
        // Repeat steps 3 and 4 until the puzzle is completed or until it is found to be impossible to complete.
        // O(n^2) runtime
        // O(n^2) space
        public boolean solve() {
            // Group pieces
            Set<Piece> cornerPieces = new HashSet<>();
            Set<Piece> borderPieces = new HashSet<>();
            Set<Piece> insidePieces = new HashSet<>();
            groupPieces(cornerPieces, borderPieces, insidePieces);

            solution = new Piece[size][size];
            // Walk through the puzzle, finding the piece that fits the previous one
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    Set<Piece> piecesToSearch = getPiecesToSearch(cornerPieces, borderPieces, insidePieces, row, column);
                    if (!findAndInsertMatchingPiece(piecesToSearch, row, column)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void groupPieces(Set<Piece> cornerPieces, Set<Piece> borderPieces, Set<Piece> insidePieces) {
            for (Piece piece : pieces) {
                if (piece.isCorner()) {
                    cornerPieces.add(piece);
                } else if (piece.isBorder()) {
                    borderPieces.add(piece);
                } else {
                    insidePieces.add(piece);
                }
            }
        }

        private Set<Piece> getPiecesToSearch(Set<Piece> cornerPieces, Set<Piece> borderPieces, Set<Piece> insidePieces,
                                             int row, int column) {
            if ((row == 0 && column == 0)
                    || (row == 0 && column == size - 1)
                    || (row == size - 1 && column == 0)
                    || (row == size - 1 && column == size - 1)) {
                return cornerPieces;
            }

            if (row == 0 || row == size - 1 || column == 0 || column == size - 1) {
                return borderPieces;
            }

            return insidePieces;
        }

        private boolean findAndInsertMatchingPiece(Set<Piece> piecesToSearch, int row, int column) {
            if (row == 0 && column == 0) { // On top left corner, just put in a piece
                Piece piece = piecesToSearch.iterator().next();
                piecesToSearch.remove(piece);
                orientTopLeftCorner(piece);
                solution[0][0] = piece;
            } else {
                // Get piece to match
                Piece pieceToMatch = column == 0 ? solution[row - 1][0] : solution[row][column - 1];
                Orientation orientationToMatch = column == 0 ? Orientation.BOTTOM : Orientation.RIGHT;
                Edge edgeToMatch = pieceToMatch.getEdgeWithOrientation(orientationToMatch);

                // Get matching edge
                Edge edge = getMatchingEdge(edgeToMatch, piecesToSearch);
                if (edge == null) {
                    return false; // It is impossible to solve the puzzle
                }

                Orientation orientation = orientationToMatch.getOpposite();
                putPieceIntoSolution(piecesToSearch, edge, row, column, orientation);
            }
            return true;
        }

        private Edge getMatchingEdge(Edge edgeToMatch, Set<Piece> pieces) {
            for (Piece piece : pieces) {
                for (Orientation orientation : piece.getEdges().keySet()) {
                    Edge edge = piece.getEdges().get(orientation);
                    if (edge.fitsWith(edgeToMatch)) {
                        return edge;
                    }
                }
            }
            return null;
        }

        private void putPieceIntoSolution(Set<Piece> pieces, Edge edge, int row, int column, Orientation orientation) {
            Piece piece = edge.getParentPiece();
            piece.setEdgeAsOrientation(edge, orientation);
            pieces.remove(piece);
            solution[row][column] = piece;
        }

        private void orientTopLeftCorner(Piece piece) {
            while (!piece.getEdgeWithOrientation(Orientation.LEFT).getShape().equals(Shape.FLAT)
                    || !piece.getEdgeWithOrientation(Orientation.TOP).getShape().equals(Shape.FLAT)) {
                piece.rotateEdgesBy(1);
            }
        }
    }

}
