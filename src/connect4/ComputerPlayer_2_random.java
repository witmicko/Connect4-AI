package connect4;

/**
 * 
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME 
 * WITH YOUR STUDENT NUMBER.
 * @author Frank
 *
 */
public class ComputerPlayer_2_random extends IPlayer {

	public ComputerPlayer_2_random(LocationState playerState) {
		super(playerState);
		
	}
	
	@Override
	public int getMove(Board board) {
        int x = (int)(Math.random()*board.getNoCols());
		//TODO
		return x;
	}
}
