package connect4;

import edu.princeton.cs.introcs.Stopwatch;

/**
 * Class to manage the connect 4 game
 */
public class Connect4 {

    private IPlayer human, computer;
    private Board board;
    private IPlayer currentPlayer;
    private int numTurns = 0;

    public Connect4(IPlayer human, IPlayer computer, Board board) {
        this.human = human;
        this.computer = computer;
        this.board = board;
        this.currentPlayer = human;
    }


    /**
     * Toggles current player
     */
    public void nextPlayer() {

        if (currentPlayer == human) {
            currentPlayer = computer;

        } else {
            currentPlayer = human;
        }
    }

    /**
     * Checks if there's a winner
     *
     * @param board to evaluate for winner
     * @return boolean to detect winner
     */
    public boolean isWin(Board board) {
        boolean win = BoardChecker.matchFor(currentPlayer.getPlayerState(), board, "++++");
        return win;
    }


    /**
     * Checks for a draw
     *
     * @return
     */

    public boolean isDraw() {
        //TODO
        return numTurns == board.getNoCols() * board.getNoRows();
    }

    /**
     * Method called to get next move from player
     *
     * @return boolean indicating move take successfully
     */
    public boolean takeTurn() {
        int col = currentPlayer.getMove(board);

//        for (int i = 0; i < board.getNoRows(); i++) {  // <- The Only line of code changed in original skeleton.
        for (int i = board.getNoRows() - 1; i >= 0; i--) {
            if (board.getLocationState(new Location(col, i)) == LocationState.EMPTY) {
                board.setLocationState(new Location(col, i), currentPlayer.getPlayerState());
                numTurns++;
                return true;
            }
        }
        System.out.println("wrong: "+currentPlayer.getPlayerState());
        return false;
    }//end takeTurn()

    public Board getBoard() {
        return board;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//        IPlayer player1 = new ComputerPlayer20057028(LocationState.YELLOW);
        int newAiWins = 0;
        int oldWins = 0;
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < 100; i++) {
//            IPlayer player1 = new HumanPlayer(LocationState.YELLOW);
            IPlayer player1 = new ComputerPlayer_WinTake_Block(LocationState.YELLOW);
            IPlayer player2 = new ComputerPlayer20057303(LocationState.RED);
            Board board = new Board(7, 6);
            Connect4 connect4 = new Connect4(player1, player2, board);
            while (true) {
                while (!connect4.takeTurn() && !connect4.isDraw()){
                    connect4.takeTurn();
                }
                if (player1 instanceof HumanPlayer || player2 instanceof HumanPlayer) {
                    System.out.println(connect4.getBoard().toString());          //////DRAW BOARD
                }
                if(connect4.isWin(board) || connect4.isDraw())break;
                connect4.nextPlayer();
            }
//            System.out.print("." + ((i % 100 == 0) ? "\n" : ""));
//            System.out.print("." );
            System.out.println(connect4.currentPlayer.getPlayerState());
//            System.out.println(connect4.currentPlayer.getPlayerState()+"\nred " + newAiWins + " yell " + oldWins);
            if (connect4.currentPlayer.getPlayerState() == LocationState.RED) newAiWins++;
            if (connect4.currentPlayer.getPlayerState() == LocationState.YELLOW) oldWins++;
        }
        System.out.println("\nnew " + newAiWins + "\nold " + oldWins);
        System.out.println("time: " + s.elapsedTime());
    }
}
