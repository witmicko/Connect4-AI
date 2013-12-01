package connect4;

import edu.princeton.cs.introcs.StdIn;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;


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
        while (isInvalidMove(col, board)) col = getCol();
        return col;
    }

    private int getCol() {
        System.out.println("Enter column number: \n");
        try {
            int col = StdIn.readInt();
            return col - 1;
        } catch (InputMismatchException e) {
            StdIn.readLine();
            System.out.println("NaN - try again");
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        return -1;
    }

    private boolean isInvalidMove(int col, Board board) {
        if (col < 0 || col >= board.getNoCols()) return true;
        if (board.getLocationState(new Location(col, 0)) == LocationState.EMPTY) return false;
        System.out.println("Invalid Move, try again");
        return true;
    }

//test
}
