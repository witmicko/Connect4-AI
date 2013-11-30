package connect4;

import edu.princeton.cs.introcs.StdIn;

import java.util.InputMismatchException;


/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Frank
 */
public class HumanPlayer extends IPlayer {

    public HumanPlayer(LocationState playerState) {
        super(playerState);
    }

    @Override
    public int getMove(Board board) {
        int col = -1;
        while (!isValidMove(col, board)) col = getCol("Enter column number: \n", board);
        return col;
    }

    private int getCol(String prompt, Board board) {
        System.out.println(prompt);
        try {
            int col = StdIn.readInt();
            return col - 1;
        } catch (InputMismatchException e) {
            StdIn.readLine();
            System.out.println("NaN - try again");
        }
        return -1;
    }

    private boolean isValidMove(int col, Board board) {
        if (col < 0 || col >= board.getNoCols()) return false;
        if(board.getLocationState(new Location(col,0))==LocationState.EMPTY)return true;
        System.out.println("Invalid Move, try again");
        return false;
    }

//test
}
