package connect4;

import edu.princeton.cs.introcs.StdRandom;

import java.util.Random;

/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Frank
 */
public class ComputerPlayer_1_random extends IPlayer {

    public ComputerPlayer_1_random(LocationState playerState) {
        super(playerState);

    }
    @Override
    public int getMove(Board board) {
        return (int) (Math.random() * 7);
    }

}