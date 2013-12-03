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
//        no setter or public access to numTurns breaks AI win checks
//        if(this.numTurns < 7)return false;
        return downUp(board) || upDown(board) || horizontalMatch(board) || verticalMatch(board);
    }


    private boolean horizontalMatch(Board board) {
//        for (int i = 0; i < board.getNoRows(); i++) {
//            int count = 0;
//            for (int j = 0; j < board.getNoCols() - 1; j++) {
//                int x = j, y = i;
//                count = compareStates(x, y, x + 1, y, board, count);
//                if (count >= 3) return true;
//
//            }
//        }
//        return false;
        for (int i = board.getNoRows() - 1; i >= 0; i--) {
            int count = 0;
            for (int j = 0; j < board.getNoCols() - 1; j++) {
                int x = j, y = i;
                count = compareStates(x, y, x + 1, y, board, count);
                if (count >= 3) return true;

            }
        }
        return false;
    }//end horizontalMatch()

    private boolean verticalMatch(Board board) {
//        for (int i = 0; i < board.getNoCols(); i++) {
//            int count = 0;
//            for (int j = 0; j < board.getNoRows() - 1; j++) {
//                int x = i;
//                int y = j;
//                count = compareStates(x, y, x, y + 1, board, count);
//                if (count >= 3) return true;
//
//            }
//        }
//        return false;
        for (int i = 0; i < board.getNoCols(); i++) {
            int count = 0;
            for (int j = board.getNoRows() - 1; j > 0; j--) {
                int x = i;
                int y = j;
                count = compareStates(x, y, x, y - 1, board, count);
                if (count >= 3) return true;
            }
        }
        return false;
    }//end verticalMatch()

    private boolean upDown(Board board) {
//      *
//      **
//      ***
//      ****
//      *****
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            int count = 0;
            for (int j = 0; j < (board.getNoRows() - 1 - i); j++) {
                int x = j;
                int y = j + i;
                count = compareStates(x, y, x + 1, y + 1, board, count);
                if (count >= 3) return true;

            }
        }
//      *****
//       ****
//        ***
//         **
//          *
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            int count = 0;
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = j;
                count = compareStates(x, y, x + 1, y + 1, board, count);
                if (count >= 3) return true;

            }
        }
        return false;
    }// end upDown()

    private boolean downUp(Board board) {
//      *****
//      ****
//      ***
//      **
//      *
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            int count = 0;
            for (int j = 0; j < board.getNoRows() - 1 - i; j++) {
                int x = j;
                int y = (board.getNoRows() - 1) - i - j;
                count = compareStates(x, y, x + 1, y - 1, board, count);
                if (count >= 3) return true;
            }
        }
//          *
//         **
//        ***
//       ****
//      *****
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            int count = 0;
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = board.getNoRows() - j - 1;
                count = compareStates(x, y, x + 1, y - 1, board, count);
                if (count >= 3) return true;
            }
        }
        return false;
    }//end downUp()

    private int compareStates(int x, int y, int xNext, int yNext, Board board, int count) {
        LocationState state = board.getLocationState(new Location(x, y));
        if (state != LocationState.EMPTY) {
            LocationState nextState = board.getLocationState(new Location(xNext, yNext));
            if (state == nextState) return count + 1;
        }
        return 0;
    }// end compareStates()


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
        System.out.println("wrong " + this.currentPlayer.getPlayerState());
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
        for (int i = 0; i < 1000; i++) {
            IPlayer player2 = new ComputerPlayer_1_random(LocationState.YELLOW);
//            IPlayer player2 = new ComputerPlayer_WinTake_Block(LocationState.YELLOW);
            IPlayer player1 = new ComputerPlayer20057303(LocationState.RED);
            Board board = new Board(7, 6);
            Connect4 connect4 = new Connect4(player1, player2, board);
            while (!connect4.isWin(board) && !connect4.isDraw()) {
                while (!connect4.takeTurn() && !connect4.isDraw()) connect4.takeTurn();
                connect4.nextPlayer();
                if (player1 instanceof HumanPlayer || player2 instanceof HumanPlayer) {
                    System.out.println(connect4.getBoard().toString());          //////DRAW BOARD
                }
            }
            System.out.println("red " + newAiWins + " yell " + oldWins);
            connect4.nextPlayer();
            if (connect4.currentPlayer.getPlayerState() == LocationState.RED) newAiWins++;
            if (connect4.currentPlayer.getPlayerState() == LocationState.YELLOW) oldWins++;
        }
        System.out.println("new " + newAiWins + "\nold " + oldWins);
        System.out.println("time: " + s.elapsedTime());
    }
}
