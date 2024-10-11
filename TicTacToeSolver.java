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
        ArrayList<Integer> positions = new ArrayList<>();
        char player = moves.charAt(moves.length() - 1);

        // "2X4O8X6O5X"
        for (int i = moves.length() - 2; i >= 0; i -= 4) {
            // position is decreased to match 0 indexing
            positions.add(Character.getNumericValue(moves.charAt(i)) - 1);
        }

        boolean rowWin = checkRow(positions);
        boolean colWin = checkColumn(positions);
        boolean diagWin = checkDiag(positions);

        //System.out.println("row: " + rowWin + " col: " + colWin + " diag: " + diagWin);
        if (rowWin || colWin || diagWin)
            return player;

        if (moves.length() == 18)
            return 'T';
        return 'U'; // TODO: Return game state ('I' is a placeholder)
    }

    
    private static boolean checkRow(ArrayList<Integer> positions) {
        int row = 2;
        if (positions.getFirst() < 6)
        row = 1;
        if (positions.getFirst() < 3)
        row = 0;
        
        //adjust the row index to the starting index of the row
        row*=3; 
        
        //check if all the row positions are found
        boolean contains = true;
        for (int i = 0; i < 3; i++){
            contains = (contains && positions.contains(row++));
        }
        return contains;
    }
    
    private static boolean checkColumn(ArrayList<Integer> positions) {
        int col = positions.getFirst() % 3;
        
        boolean contains = true;
        // loop to check column
        for (int i = col; i < 9; i += 3) {
            contains = (contains && positions.contains(i));
        }
        return contains;
    }
    
    private static boolean checkDiag(ArrayList<Integer> positions) {
        boolean left = true;
        boolean right = true;

        //check the left diag {0, 4, 8}
        for (int i = 0; i < 9; i+=4){
            left = (left && positions.contains(i));
        }

        //check the right diag {2, 4, 6}
        for (int i = 2; i < 7; i+=2 ){
            right = (right && positions.contains(i));
        }
        return left || right;
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
