import java.util.ArrayList;

/**
 * A solver for determining the result of a Tic Tac Toe game based on a String
 * sequence of moves.
 * The board layout:
 * 
 * 1 | 2 | 3
 * -----------
 * 4 | 5 | 6
 * -----------
 * 7 | 8 | 9
 * 
 * Given a string of moves, determines whether 'X' has won,'O' has won, the
 * game ended in a tie, or the game is unfinished. The format of the String
 * is <position of move><player that made move><position of move><player that
 * made move>. e.g. "2X3O8X7O5X" results in:
 * 
 * | X | O
 * -----------
 * | X |
 * -----------
 * O | X |
 * 
 * This class includes a method determineWinner to evaluate the game state
 * and a main method for testing.
 * 
 * @author Mark Timpson
 */
public class TicTacToeSolver {

    /**
     * Determines the winner of the Tic Tac Toe game based on the String moves
     * provided.
     * 
     * @param moves A string representing the moves made in the game in the format:
     *              "<position1><player1><position2><player2>..." where each
     *              position
     *              is a number 1-9 (see board layout) and each player is 'X' or
     *              'O'.
     * @return 'X' if player X won, 'O' if player O won, 'T' for a tie, or 'U' if
     *         the game is unfinished.
     */
    public static char determineWinner(String moves) {
        // Since the last move in a game of TicTacToe determines if there was a winner
        // we only need to check the last move to see if it creates 3 in a row based on
        // that last move's row, column, or diagonal (if applicable)

        // Get the last player's Character; X or O (example String: 2X4O8X6O5X)
        char lastPlayer = moves.charAt(moves.length() - 1);

        // gather all of the moves made by the last player
        ArrayList<Integer> positions = new ArrayList<>();

        // If using a board that is greater than 3x3 then the positions available become
        // longer than 2 digits. This series of loops should take the last player's char (X or O) 
        // and use that to gather the digits before to be parsed and added to the positions list. 
        // int index = moves.length() - 1;
        // char currentPlayer = lastPlayer;
        // while (index > 0){
            
        //     String position = "";
        //     while (currentPlayer == lastPlayer && index > 0){
        //         char p = moves.charAt(--index);
        //         if (Character.isDigit(p))
        //             position = p + position;
        //         else {
        //             currentPlayer = p;
        //             positions.add( Integer.parseInt(position) - 1 ); // subtract one to adjust for a 0 index
        //         }
        //     }
        //     while (currentPlayer != lastPlayer && index > 0){
        //         char p = moves.charAt(--index);
        //         if (Character.isLetter(p))
        //             currentPlayer = p;
        //     }
        // }

        // If playing board is 3x3 then all available positions are going to be single digit. No need for 
        // for more complicated parsing of String characters.
        for (int i = moves.length() - 2; i >= 0; i -= 4) {
            // position is decreased to match 0 indexing
            positions.add(Character.getNumericValue(moves.charAt(i)) - 1);
        }

        // check for a win by row, column, or Diagonal
        boolean rowWin = checkRow(positions);
        boolean colWin = checkColumn(positions);
        boolean diagWin = checkDiag(positions);

        // declare a winner if any of the previous checks returned true
        if (rowWin || colWin || diagWin)
            return lastPlayer;

        // If no winner, return a Tie if the board is full
        if (moves.length() == 18)
            return 'T';

        // Board is not full and no winner then return Unfinished
        return 'U';
    }

    private static boolean checkRow(ArrayList<Integer> positions) {
        /*
         * row 0  _|_|_
         * row 1  _|_|_
         * row 2   | |
         */

        // int math to find row index ( 0-2 / 3 = 0; 3-5 / 3 = 1; 6-8 / 3 = 2 )
        int row = positions.getFirst() / 3;

        // adjust the row index to the starting index of the row (0 or 3 or 6)
        row *= 3;

        // check if all the positions are in the row
        boolean contains = true;
        for (int i = 0; i < 3; i++) {
            contains = (positions.contains(row++));
            if (!contains)
                return contains;
        }
        return contains;

        // If wanting to operate on n x n board instead of constants
        // int row = positions.getFirst() / n;
        // row *= n;
        // for (int i = 0; i < n; i++) {
        //     contains = (positions.contains(row++));
        //     if (!contains)
        //         return contains;
        // }
        // return contains;
    }
        

    private static boolean checkColumn(ArrayList<Integer> positions) {
        /*
         * Columns: 0 1 2
         *          _|_|_
         *          _|_|_
         *           | |
         */

        int col = positions.getFirst() % 3;

        // check if all the postions are in the column
        boolean contains = true;
        for (int i = col; i < 9; i += 3) {
            contains = (positions.contains(i));
            if (! contains)
                return contains;
        }
        return contains;

        // if wanting to operate on n x n board instead of constants
        // int col = positions.getFirst() % n;
        // boolean contains = true;
        // for (int i = col; i < n*n; i += n) {
        //     contains = (positions.contains(i));
        //     if (! contains)
        //         return contains;
        // }
        // return contains;
    }

    private static boolean checkDiag(ArrayList<Integer> positions) {
        boolean left = true;
        boolean right = true;

        // check the left diag {0, 4, 8}
        for (int i = 0; i < 9; i += 4) {
            left = (left && positions.contains(i));
        }

        // check the right diag {2, 4, 6}
        for (int i = 2; i < 7; i += 2) {
            right = (right && positions.contains(i));
        }
        return left || right;

        // if wanting to operate on n x n board instead of constants
        // for (int i = 0; i < n*n; i += (n+1)) {
        //     left = (left && positions.contains(i));
        // }

        // if wanting to operate on n x n board instead of constants
        // for (int i = (n-1); i < n*n-n; i += (n-1)) {
        //     right = (right && positions.contains(i));
        // }
        // return left || right;
    }

    // * * * * * * * * Testing - Do Not Modify * * * * * * * *

    /**
     * Formats output for tests in format"
     * Test <testNumber>
     * -------
     * <moves>
     * Expected output: <expected>
     * determineWinner: <output>
     * 
     * @param testNumber to be printed in title
     * @param moves      String of player moves
     * @param expected   char to be returned by determineWinner
     */
    public static void printTest(int testNumber, String moves, char expected) {
        char output = determineWinner(moves);

        System.out.println("Test " + testNumber);
        System.out.println("-------");
        System.out.println("Moves: " + moves);
        System.out.println("Expected output: " + expected);
        System.out.println("determineWinner: " + output);
        System.out.println();
    }

    /**
     * Runs printTest with 5 different move inputs.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        printTest(1, "2X4O8X6O5X", 'X');
        printTest(2, "1X8O3X2O7X5O", 'O');
        printTest(3, "9X5O7X8O2X4O6X3O1X", 'T');
        printTest(4, "1X9O7X4O3X2O5X", 'X');
        printTest(5, "3X9O7X5O1X", 'U');
    }
}
