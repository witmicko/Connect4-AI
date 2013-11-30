package connect4;

/**
 * Class to manage the connect 5 game
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
        boolean downUp, upDown, hor, vert;
        downUp = upDown = hor = vert = false;
        upDown = upDown(board);
        hor = horizontalMatch(board);
        vert = verticalMatch(board);
        downUp = downUp(board);
        return downUp || upDown || hor || vert;
    }


    private boolean horizontalMatch(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoRows(); i++) {
            for (int j = 0; j < board.getNoCols() - 1; j++) {
                int x = j;
                int y = i;
                LocationState state = board.getLocationState(new Location(x, y));
                if (state != LocationState.EMPTY) {
                    int xNxt = x + 1;
                    int yNxt = y + 0;
                    LocationState nextState = board.getLocationState(new Location(xNxt, yNxt));
                    if (state == nextState) {
                        count++;
                        if (count >= 3) return true;
                    } else count = 0;
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
                LocationState state = board.getLocationState(new Location(x, y));
                if (state != LocationState.EMPTY) {
                    int xNxt = x + 0;
                    int yNxt = y + 1;
                    LocationState nextState = board.getLocationState(new Location(xNxt, yNxt));
                    if (state == nextState) {
                        count++;
                        if (count >= 3) return true;
                    } else count = 0;
                }
            }
        }
        return false;
    }

    private boolean upDown(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoRows() - 2; i++) {
            for (int j = 0; j < (board.getNoRows() - 1 - i); j++) {
                int x = j;
                int y = j + i;
                LocationState state = board.getLocationState(new Location(x, y));
                if (state != LocationState.EMPTY) {
                    int xNxt = x + 1;
                    int yNxt = y + 1;
                    LocationState nextState = board.getLocationState(new Location(xNxt, yNxt));
                    if (state == nextState) {
                        count++;
                        if (count >= 3) return true;
                    } else count = 0;
                }
            }
        }

        count = 0;
        for (int i = 1; i < board.getNoCols() - 2; i++) {
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = j;
                LocationState state = board.getLocationState(new Location(x, y));
                if (state != LocationState.EMPTY) {
                    int xNxt = x + 1;
                    int yNxt = y + 1;
                    LocationState nextState = board.getLocationState(new Location(xNxt, yNxt));
                    if (state == nextState) {
                        count++;
                        if (count >= 3) return true;
                    } else count = 0;
                }
            }
        }
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    private boolean downUp(Board board) {
        int count = 0;
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            for (int j = 0; j < board.getNoRows() - 1 - i; j++) {
                int x = j;
                int y = board.getNoRows() - 1 - i - j;
                LocationState state = board.getLocationState(new Location(x, y));
                if (state != LocationState.EMPTY) {
                    int xNxt = x + 1;
                    int yNxt = y - 1;
                    LocationState nextState = board.getLocationState(new Location(xNxt, yNxt));
                    if (state == nextState) {
                        count++;
                        if (count >= 3) return true;
                    } else count = 0;
                }
            }
        }


        //start herer
        count = 0;
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            for (int j = 0; j < board.getNoRows() - i + 1; j++) {
                int x = j + i;
                int y = board.getNoRows() - j - 1;
                LocationState state = board.getLocationState(new Location(x, y));
                if (state != LocationState.EMPTY) {
                    int xNxt = x + 1;
                    int yNxt = y - 1;
                    LocationState nextState = board.getLocationState(new Location(xNxt, yNxt));
                    if (state == nextState) {
                        count++;
                        if (count >= 3) return true;
                    } else count = 0;
                }
            }
        }
        return false;
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
                board.setLocationState(new Location(col, i),
                        currentPlayer.getPlayerState());
                numTurns++;
                return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }


}
