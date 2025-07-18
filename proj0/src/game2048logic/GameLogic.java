package game2048logic;

import game2048rendering.Side;

import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author Josh Hug
 */
public class GameLogic {
    /**
     * Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c     -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return if there is a merge, returns the 1 + the row number where the merge occurred.
     * if no merge occurs, then return minR.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int row, int column, int minR) {
        // DONE: Fill this in in tasks 2, 3, 4
        if (board[row][column] == 0) {
            return minR;
        }

        int targetRow = row;
        while (targetRow > minR && board[targetRow - 1][column] == 0) {
            --targetRow;
        }

        int temp = board[targetRow][column];
        board[targetRow][column] = board[row][column];
        board[row][column] = temp;

        if (targetRow > minR && board[targetRow - 1][column] == board[targetRow][column]) {
            // Merge the tiles
            board[targetRow - 1][column] *= 2;
            board[targetRow][column] = 0;
            return targetRow;
        }

        return minR;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board the current state of the board
     * @param c     the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // DONE: fill this in in task 5
        int minR = 0;
        for (int r = 0; r < board.length; ++r) {
            minR = moveTileUpAsFarAsPossible(board, r, c, minR);
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // DONE: fill this in in task 6
        for (int c = 0; c < board[0].length; ++c) {
            tiltColumn(board, c);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // DONE: fill this in in task 7
        Runnable prework = () -> {
            switch (side) {
                case EAST -> rotateLeft(board);
                case WEST -> rotateRight(board);
                case SOUTH -> {
                    rotateLeft(board);
                    rotateLeft(board);
                }
            }
        };

        Runnable postwork = () -> {
            switch (side) {
                case EAST -> rotateRight(board);
                case WEST -> rotateLeft(board);
                case SOUTH -> {
                    rotateRight(board);
                    rotateRight(board);
                }
            }
        };

        prework.run();
        tiltUp(board);
        postwork.run();
    }
}
