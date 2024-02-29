package connectfourgame;

import static connectfourgame.Constants.BOARD_ROWS;
import static connectfourgame.Constants.BOARD_COLUMNS;

public class Board {
    private final String[][] board;

    public Board() {
        board = new String[BOARD_COLUMNS][BOARD_ROWS];
        fillBoardWithDots();
    }

    /**
     * Makes a move on the game board.
     *
     * @param column The column where the move is made.
     * @param player The player making the move.
     * @return True if the move is valid and successful, false otherwise.
     */
    public synchronized boolean makeMove(int column, String player) {
        if (column < 1 || column > BOARD_COLUMNS) {
            System.out.println("You must choose column from 1 to seven");
            return false;
        }

        for (int row = BOARD_ROWS - 1; row >= 0; row--) {
            if (board[column - 1][row].equals(".")) {
                board[column - 1][row] = player;
                return true;
            }
        }
        System.out.println("Column " + column + "is full. Choose another column");
        return false;
    }

    public synchronized boolean isFull() {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            if (board[col][0].equals(".")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there is a winner on the game board.
     *
     * @return True if there is a winner, false otherwise.
     */
    public synchronized boolean checkWin() {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            for (int row = 0; row < BOARD_ROWS; row++) {
                String currentCell = board[col][row];
                if (currentCell.equals(".")) {
                    continue;
                }

                if (checkDirection(col, row, 1, 0) || // Horizontal
                        checkDirection(col, row, 0, 1) || // Vertical
                        checkDirection(col, row, 1, 1) || // Diagonal down-right
                        checkDirection(col, row, -1, 1)) { // Diagonal down-left
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for a winner in a specific direction from a starting point.
     *
     * @param startCol  The starting column.
     * @param startRow  The starting row.
     * @param colDelta  The change in column for each step.
     * @param rowDelta  The change in row for each step.
     * @return True if there is a winner in the specified direction, false otherwise.
     */
    private boolean checkDirection(int startCol, int startRow, int colDelta, int rowDelta) {
        String player = board[startCol][startRow];

        for (int step = 1; step < 4; step++) {
            int col = startCol + step * colDelta;
            int row = startRow + step * rowDelta;

            if (col < 0 || col >= BOARD_COLUMNS || row < 0 || row >= BOARD_ROWS) {
                return false;
            }

            if (!player.equals(board[col][row])) {
                return false;
            }
        }

        return true;
    }

    public synchronized void printBoard() {
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLUMNS; col++) {
                System.out.print(board[col][row] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void fillBoardWithDots() {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            for (int row = 0; row < BOARD_ROWS; row++) {
                board[col][row] = ".";
            }
        }
    }
}
