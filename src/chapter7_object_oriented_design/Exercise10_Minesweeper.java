package chapter7_object_oriented_design;

import java.util.*;

/**
 * Created by Rene Argento on 31/08/19.
 */
public class Exercise10_Minesweeper {

    public enum CellType {
        BOMB, NUMBER, BLANK;
    }

    public class Cell {
        private CellType cellType;
        private boolean hasFlag;
        private boolean isCovered;
        private int row;
        private int column;

        public Cell(CellType cellType, int row, int column) {
            this.cellType = cellType;
            this.row = row;
            this.column = column;
            isCovered = true;
        }

        public CellType getCellType() {
            return cellType;
        }

        public void setCellType(CellType cellType) {
            this.cellType = cellType;
        }

        public boolean hasFlag() {
            return hasFlag;
        }

        public void setFlag(boolean hasFlag) {
            this.hasFlag = hasFlag;
        }

        public boolean isCovered() {
            return isCovered;
        }

        public void setCovered(boolean isCovered) {
            this.isCovered = isCovered;
        }

        public void setRowAndColumn(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        @Override
        public String toString() {
            switch (cellType) {
                case BOMB: return "*";
                case NUMBER: return "#"; // Will be overriden on subclass
                default: return " ";
            }
        }
    }

    public class CellNumber extends Cell {
        private int number;

        public CellNumber(int row, int column) {
            super(CellType.NUMBER, row, column);
            number = 1;
        }

        public int getNumber() {
            return number;
        }

        public void incrementNumber() {
            number++;
        }

        @Override
        public String toString() {
            return String.valueOf(number);
        }
    }

    public class Board {
        private Cell[][] grid;
        private Cell[] bombs;
        private int numberOfCellsToUncover;

        public Board(int gridDimension, int bombs) {
            this.bombs = new Cell[bombs];
            grid = new Cell[gridDimension][gridDimension];
            numberOfCellsToUncover = (gridDimension * gridDimension) - bombs;

            initializeBoard();
            placeBombs();
            setNumberedCells();
        }

        private void initializeBoard() {
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    grid[row][column] = new Cell(CellType.BLANK, row, column);
                }
            }
        }

        private void placeBombs() {
            int gridDimension = grid.length;

            // 1- place bombs on the first cells
            for (int bomb = 0; bomb < bombs.length; bomb++) {
                int row = bomb / gridDimension;
                int column = bomb % gridDimension;

                Cell bombCell = grid[row][column];
                bombCell.setCellType(CellType.BOMB);
                bombs[bomb] = bombCell;
            }

            // 2- shuffle board
            shuffleBoard();
        }

        private void shuffleBoard() {
            Random random = new Random();
            int gridDimension = grid.length;
            int maxIndex = gridDimension * gridDimension;

            for (int index1 = 0; index1 < maxIndex; index1++) {
                int index2 = index1 + random.nextInt(maxIndex - index1);

                // Swap cells
                if (index1 != index2) {
                    int row1 = index1 / gridDimension;
                    int column1 = index1 % gridDimension;
                    Cell cell1 = grid[row1][column1];

                    int row2 = index2 / gridDimension;
                    int column2 = index2 % gridDimension;
                    Cell cell2 = grid[row2][column2];

                    grid[row1][column1] = cell2;
                    cell2.setRowAndColumn(row1, column1);
                    grid[row2][column2] = cell1;
                    cell1.setRowAndColumn(row2, column2);
                }
            }
        }

        private void setNumberedCells() {
            int[][] deltas = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    { 0, -1},          { 0, 1},
                    { 1, -1}, { 1, 0}, { 1, 1}
            };

            for (Cell bomb : bombs) {
                for (int[] delta : deltas) {
                    int row = bomb.getRow() + delta[0];
                    int column = bomb.getColumn() + delta[1];

                    if (isValidCellIndex(row, column)) {
                        CellType cellType = grid[row][column].getCellType();
                        if (cellType.equals(CellType.BLANK)) {
                            grid[row][column] = new CellNumber(row, column);
                        } else if (cellType.equals(CellType.NUMBER)) {
                            CellNumber cellNumber = (CellNumber) grid[row][column];
                            cellNumber.incrementNumber();
                        }
                    }
                }
            }
        }

        private boolean isValidCellIndex(int row, int column) {
            return row >= 0 && row < grid.length
                    && column >= 0 && column < grid[0].length;
        }

        public GameState uncoverCell(int row, int column) {
            GameState gameState = GameState.RUNNING;
            Cell cell = grid[row][column];

            if (!cell.isCovered()) {
                return gameState;
            }

            cell.setCovered(false);
            int uncovered = 0;

            if (cell.getCellType().equals(CellType.BOMB)) {
                gameState = GameState.LOST;
            } else if (cell.getCellType().equals(CellType.BLANK)) {
                uncovered = floodFill(cell);
            } else {
                uncovered = 1;
            }

            numberOfCellsToUncover -= uncovered;
            if (numberOfCellsToUncover == 0) {
                gameState = GameState.WON;
            }

            return gameState;
        }

        private int floodFill(Cell cell) {
            int uncovered = 0;

            int[][] deltas = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    { 0, -1},          { 0, 1},
                    { 1, -1}, { 1, 0}, { 1, 1}
            };

            Queue<Cell> queue = new LinkedList<>();
            queue.offer(cell);

            while (!queue.isEmpty()) {
                Cell currentCell = queue.poll();
                uncovered++;

                if (currentCell != null && currentCell.getCellType().equals(CellType.BLANK)) {
                    for (int[] delta : deltas) {
                        int neighborRow = currentCell.getRow() + delta[0];
                        int neighborColumn = currentCell.getColumn() + delta[1];

                        if (isValidCellIndex(neighborRow, neighborColumn)) {
                            Cell neighborCell = grid[neighborRow][neighborColumn];

                            if (neighborCell.isCovered()) {
                                neighborCell.setCovered(false);
                                queue.offer(neighborCell);
                            }
                        }
                    }
                }
            }

            return uncovered;
        }

        public void flagCell(int row, int column) {
            grid[row][column].setFlag(true);
        }

        public void removeFlagFromCell(int row, int column) {
            grid[row][column].setFlag(false);
        }

        private void printGrid(boolean showCoveredCells) {
            printLine();

            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[0].length; column++) {
                    if (column == 0) {
                        System.out.print("| ");
                    }

                    Cell cell = grid[row][column];
                    if (cell.hasFlag()) {
                        System.out.print("F ");
                    } else if (cell.isCovered() && !showCoveredCells) {
                        System.out.print("? ");
                    } else {
                        System.out.print(grid[row][column] + " ");
                    }

                    if (column == grid[0].length - 1) {
                        System.out.print("|");
                    }
                }
                System.out.println();
            }

            printLine();
            System.out.println();
        }

        private void printLine() {
            System.out.print("  ");
            for (int row = 0; row < grid.length; row++) {
                System.out.print("- ");
            }
            System.out.println();
        }

        public Cell[][] getGrid() {
            return grid;
        }

        public int getNumberOfCellsToUncover() {
            return numberOfCellsToUncover;
        }
    }

    public enum Command {
        UNCOVER, FLAG, UNFLAG
    }

    public class UserPlay {
        private int row;
        private int column;
        private Command command;

        public UserPlay(int row, int column, Command command) {
            this.row = row;
            this.column = column;
            this.command = command;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public Command getCommand() {
            return command;
        }
    }

    public enum GameState {
        LOST, RUNNING, WON
    }

    public class Game {
        private Board board;
        private GameState state;

        public Game(int gridDimension, int bombs) {
            validateInput(gridDimension, bombs);

            board = new Board(gridDimension, bombs);
            board.printGrid(false);
        }

        public void play() {
            Scanner scanner = new Scanner(System.in);
            state = GameState.RUNNING;

            while (state.equals(GameState.RUNNING)) {
                printInstructions();
                UserPlay userPlay = getInput(scanner);

                int row = userPlay.getRow();
                int column = userPlay.getColumn();

                switch (userPlay.getCommand()) {
                    case UNCOVER:
                        state = board.uncoverCell(row, column);
                        break;
                    case FLAG:
                        board.flagCell(row, column);
                        break;
                    case UNFLAG:
                        board.removeFlagFromCell(row, column);
                }

                if (state.equals(GameState.LOST) || state.equals(GameState.WON)) {
                    board.printGrid(true);
                    break;
                } else {
                    board.printGrid(false);
                }
            }

            if (state.equals(GameState.WON)) {
                System.out.println("Congratulations, you won!");
            } else {
                System.out.println("BOOM");
                System.out.println("Game over");
            }
        }

        private void printInstructions() {
            System.out.println("Input command:");
            System.out.println("U row column to uncover cell");
            System.out.println("F row column to flag cell");
            System.out.println("R row column to remove flag from cell");
        }

        private UserPlay getInput(Scanner scanner) {
            boolean isValidInput = false;
            UserPlay userPlay = null;

            while (!isValidInput) {
                try {
                    String command = scanner.next();
                    int row = scanner.nextInt();
                    int column = scanner.nextInt();

                    if (!board.isValidCellIndex(row, column)) {
                        System.out.println("Invalid cell, try again");
                    } else if (!command.equals("U") && !command.equals("F") && !command.equals("R")) {
                        System.out.println("Invalid command. Valid commands: U F R");
                    } else if (board.getGrid()[row][column].hasFlag()
                            && !command.equals("R")) {
                        System.out.println("Cell is flagged. Remove flag first or uncover other cell");
                    } else {
                        Command commandValue = null;
                        switch (command) {
                            case "U": commandValue = Command.UNCOVER;
                                break;
                            case "F": commandValue = Command.FLAG;
                                break;
                            case "R": commandValue = Command.UNFLAG;
                                break;
                        }
                        userPlay = new UserPlay(row, column, commandValue);
                        isValidInput = true;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid command");
                    printInstructions();
                }
            }

            return userPlay;
        }

        private void validateInput(int gridDimension, int bombs) {
            if (gridDimension <= 0) {
                throw new IllegalArgumentException("Grid dimension must be positive");
            }
            if (bombs > (gridDimension * gridDimension)) {
                throw new IllegalArgumentException("There is not enough cells to place the bombs");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Exercise10_Minesweeper().new Game(4, 2);
        game.play();
    }

}