package chapter7_object_oriented_design;

/**
 * Created by Rene Argento on 29/08/19.
 */
public class Exercise8_Othello {

    public enum PieceColor {
        BLACK, WHITE
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public static class Game {
        private Player[] players;
        private static Game instance;
        private Board board;
        private final int ROWS = 10;
        private final int COLUMNS = 10;

        private Game() {
            board = new Board(ROWS, COLUMNS);
            players = new Player[2];
            players[0] = new Player(PieceColor.WHITE);
            players[1] = new Player(PieceColor.BLACK);
        }

        public static Game getInstance() {
            if (instance == null) {
                instance = new Game();
            }
            return instance;
        }

        public Board getBoard() {
            return board;
        }
    }

    public static class Board {
        private int blackPiecesCount;
        private int whitePiecesCount;
        private Piece[][] board;

        public Board(int rows, int columns) {
            board = new Piece[rows][columns];
        }

        // Initialize center black and white pieces
        public void initialize() {
            int middleRow = getMiddle(board.length);
            int middleColumn = getMiddle(board[0].length);

            board[middleRow][middleColumn] = new Piece(PieceColor.WHITE);
            board[middleRow][middleColumn + 1] = new Piece(PieceColor.BLACK);
            board[middleRow + 1][middleColumn] = new Piece(PieceColor.BLACK);
            board[middleRow + 1][middleColumn + 1] = new Piece(PieceColor.WHITE);
        }

        private int getMiddle(int length) {
            int middle;
            if (length % 2 == 0) {
                middle = (length / 2) - 1;
            } else {
                middle = length / 2;
            }
            return middle;
        }

        public boolean placePieceInBoard(int row, int column, PieceColor pieceColor) {
            if (board[row][column] != null) {
                return false;
            }
            board[row][column] = new Piece(pieceColor);

            if (pieceColor == PieceColor.WHITE) {
                whitePiecesCount++;
            } else {
                blackPiecesCount++;
            }
            return true;
        }

        // Flip pieces starting from (row, column) and proceeding in direction direction
        private int flipSection(int row, int column, PieceColor pieceColor, Direction direction, int numberOfPieces) {
            boolean isIncreasingDirection = direction == Direction.DOWN || direction == Direction.RIGHT;
            boolean isChangingRow = direction == Direction.UP || direction == Direction.DOWN;
            int flippedPieces = 0;

            if (isChangingRow) {
                int endRow;
                if (isIncreasingDirection) {
                    endRow = row + numberOfPieces;
                } else {
                    endRow = row - numberOfPieces;
                }

                for (int currentRow = row; currentRow != endRow;) {
                    Piece piece = board[currentRow][column];
                    if (flipPiece(piece, pieceColor)) {
                        flippedPieces++;
                    }
                    currentRow = isIncreasingDirection ? currentRow + 1 : currentRow - 1;
                }
            } else {
                int endColumn;
                if (isIncreasingDirection) {
                    endColumn = column + numberOfPieces;
                } else {
                    endColumn = column - numberOfPieces;
                }

                for (int currentColumn = column; currentColumn != endColumn;) {
                    Piece piece = board[row][currentColumn];
                    if (flipPiece(piece, pieceColor)) {
                        flippedPieces++;
                    }
                    currentColumn = isIncreasingDirection ? currentColumn + 1 : currentColumn - 1;
                }
            }

            updateScore(pieceColor, flippedPieces);
            return flippedPieces;
        }

        private boolean flipPiece(Piece piece, PieceColor pieceColor) {
            boolean flippedPiece = false;

            if (piece != null && !piece.getColor().equals(pieceColor)) {
                piece.flipColor();
                flippedPiece = true;
            }
            return flippedPiece;
        }

        public int getScoreForColor(PieceColor pieceColor) {
            if (pieceColor.equals(PieceColor.WHITE)) {
                return whitePiecesCount;
            } else {
                return blackPiecesCount;
            }
        }

        public void updateScore(PieceColor newPieceColor, int newPieces) {
            if (newPieceColor.equals(PieceColor.WHITE)) {
                whitePiecesCount += newPieces;
                blackPiecesCount -= newPieces;
            } else {
                blackPiecesCount += newPieces;
                whitePiecesCount -= newPieces;
            }
        }
    }

    public static class Piece {
        private PieceColor color;

        public Piece(PieceColor color) {
            this.color = color;
        }

        public void flipColor() {
            if (color == PieceColor.BLACK) {
                color = PieceColor.WHITE;
            } else {
                color = PieceColor.BLACK;
            }
        }

        public PieceColor getColor() {
            return color;
        }
    }

    public static class Player {
        private PieceColor pieceColor;

        public Player(PieceColor pieceColor) {
            this.pieceColor = pieceColor;
        }

        public int getScore() {
            Board board = Game.getInstance().getBoard();
            int score;
            if (pieceColor.equals(PieceColor.WHITE)) {
                score = board.whitePiecesCount;
            } else {
                score = board.blackPiecesCount;
            }
            return score;
        }

        public boolean playPiece(int row, int column) {
            return Game.getInstance().getBoard().placePieceInBoard(row, column, pieceColor);
        }

        public PieceColor getPieceColor() {
            return pieceColor;
        }
    }

}
