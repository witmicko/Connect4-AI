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
//        IPlayer player1 = new ComputerPlayer_WinTake_Block(LocationState.RED);
        IPlayer player1 = new ComputerPlayer20057303(LocationState.YELLOW);
        IPlayer player2 = new HumanPlayer(LocationState.RED);
        Board board = new Board(7, 6);
        Connect4 connect4 = new Connect4(player1, player2, board);


        boolean win = false;
        while (!win) {
            while(!connect4.takeTurn()){
                connect4.takeTurn() ;
            }
            connect4.nextPlayer();

            System.out.println(connect4.getBoard().toString());
            win = connect4.isWin(board);
        }
        System.out.println("win!!!");
        System.out.println(connect4.getBoard().toString());

    }
}
