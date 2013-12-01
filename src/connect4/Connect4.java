package connect4;

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
//        if(this.numTurns < 7)return false;
        return downUp(board) || upDown(board) || horizontalMatch(board) || verticalMatch(board);
    }


    private boolean horizontalMatch(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoRows(); i++) {
            for (int j = 0; j < board.getNoCols() - 1; j++) {
                int x = j;
                int y = i;
                count = compareStates(x, y, x + 1, y, board, count);
                if (count >= 3) {
                    System.out.println("horizontal win");
                    return true;
                }
            }
        }

        return false;
    }

    private boolean verticalMatch(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoCols(); i++) {
            for (int j = 0; j < board.getNoRows() - 1; j++) {
                int x = i;
                int y = j;
                count = compareStates(x, y, x, y + 1, board, count);
                if (count >= 3) {
                    System.out.println("vert win at col " + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean upDown(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            for (int j = 0; j < (board.getNoRows() - 1 - i); j++) {
                int x = j;
                int y = j + i;
                count = compareStates(x, y, x + 1, y + 1, board, count);
                if (count >= 3) {
                    System.out.println("1st \\ win");
                    return true;
                }
            }
        }
        count = 0;
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = j;
                count = compareStates(x, y, x + 1, y + 1, board, count);
                if (count >= 3) {
                    System.out.println("2nd \\ win");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean downUp(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            for (int j = 0; j < board.getNoRows() - 1 - i; j++) {
                int x = j;
                int y = (board.getNoRows() - 1) - i - j;
                count = compareStates(x, y, x + 1, y - 1, board, count);
                if (count >= 3) {
                    System.out.println("1st // win");
                    return true;
                }
            }
        }
        count = 0;
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = board.getNoRows() - j - 1;
                count = compareStates(x, y, x + 1, y - 1, board, count);
                if (count >= 3) {
                    System.out.println("2nd // win");
                    return true;
                }
            }
        }
        return false;
    }

    private int compareStates(int x, int y, int xNext, int yNext, Board board, int count) {
        LocationState state = board.getLocationState(new Location(x, y));
        if (state != LocationState.EMPTY) {
            LocationState nextState = board.getLocationState(new Location(xNext, yNext));
            if (state == nextState) return count + 1;
        }
        return 0;
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

//        for (int i = 0; i < board.getNoRows(); i++) {
        for (int i = board.getNoRows() - 1; i >= 0; i--) {
            if (board.getLocationState(new Location(col, i)) == LocationState.EMPTY) {
                board.setLocationState(new Location(col, i), currentPlayer.getPlayerState());
                numTurns++;
                return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }


}
