package connect4;

/**
 * This class represents a board for Connect 4. Please method headers as is.
 *
 * @author
 */
public class Board {
    private LocationState board[][];
    private int noCols, noRows;

    /**
     * This constructor creates and initialises the board.
     *
     * @param col the number of columns in the board
     * @param row the number of rows in the board
     * @see LocationState
     */
    public Board(int col, int row) {

        board = new LocationState[col][row];
        noCols = col;
        noRows = row;
        clear();
    }

    /**
     * This method clears the board by setting each element to
     * LocationState.EMPTY
     *
     * @return Nothing
     */
    public void clear() {

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = LocationState.EMPTY;

    }

    /**
     * This method gets the location state (i.e. colour) at a particular
     * location
     *
     * @param location
     * @return Location state as LocationState
     * @see Location
     * @see LocationState
     */
    public LocationState getLocationState(Location location) {
        return board[location.getX()][location.getY()];
    }

    /**
     * This method sets the location state (i.e. colour) at a particular
     * location
     *
     * @param location
     * @return Nothing
     * @see Location
     * @see LocationState
     */
    public boolean setLocationState(Location location, LocationState state) {
        if (location.getX() < getNoCols() && location.getY() < getNoRows()) {
            board[location.getX()][location.getY()] = state;
            return true;
        }
        return false;
    }

    public String toString() {
        String indexes = " 1  2  3  4  5  6  7\n";
//        String indexes = " 0  1  2  3  4  5  6\n";
        String s = "" + indexes;
        for (int i = 0; i < noRows; i++) {
            for (int j = 0; j < noCols; j++) {
                if (board[j][i] == LocationState.EMPTY) s += "   ";
                if (board[j][i] == LocationState.RED) s += " X ";
                if (board[j][i] == LocationState.YELLOW) s += " O ";
//				s += (board[j][i] + "\t");
            }
            s += i+"\n";
        }

        return s + indexes;
    }

    /**
     * Gets the number of columns on the board.
     *
     * @return number of columns on board as an integer
     */
    public int getNoCols() {
        return noCols;
    }

    /**
     * Gets the number of rows on the board.
     *
     * @return number of rows on board as an integer
     */
    public int getNoRows() {
        return noRows;
    }

}
