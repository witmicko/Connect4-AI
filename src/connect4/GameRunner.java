package connect4;


/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 22/11/13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class GameRunner {
    public static void main(String[] args) {
        IPlayer player1 = new ComputerPlayer_1_random(LocationState.RED);
        IPlayer player2 = new ComputerPlayer_2_random(LocationState.YELLOW);
        Board board = new Board(7, 6);
        Connect4 connect4 = new Connect4(player1, player2, board);


        boolean win = false;
        for (int i = 5; i > 0; i--) {
            connect4.takeTurn();
            connect4.nextPlayer();
            System.out.println(connect4.getBoard().toString());
            win = connect4.isWin(connect4.getBoard());
            System.out.println(win);
            System.out.println("^^^^^^^^^");
            if(win)System.exit(2);
        }
    }
}
