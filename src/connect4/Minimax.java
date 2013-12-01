package connect4;

/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Frank
 */
public class Minimax extends IPlayer {
    private boolean debug = true;
    private int numTurns;
    private Player me, other;
    private Board boardCpy;
    private Connect4 c4;
    private int maxDepth; //0 - random > more = harder ai.

    public Minimax(LocationState playerState) {
        super(playerState);
    }

    @Override
    public int getMove(Board board) {
        this.boardCpy = copyBoard(board);
        setupGame();
        if(this.numTurns < 2)return firstMove(this.boardCpy);

        int moveTo = checkForWinner(this.boardCpy);
        int x = (moveTo < 0) ? (int) (Math.random() * 7) : moveTo;
        debugPrint("move to: "+moveTo + "\n x: "+x);
        return x;
    }


    private int checkForWinner(Board boardCpy) {
        for (int i = 0; i < boardCpy.getNoCols(); i++) {
            this.me.moveTo = i;
            c4.takeTurn();
            boolean isWin = c4.isWin(boardCpy);
//            debugPrint(isWin+"");
            if (isWin) {
                debugPrint("I can win. make a move at: " + (i + 1) + " to slaughter human\n" + "My Color is " + this.getPlayerState());
                if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY)return i;
            }undoMove(boardCpy, i);
        }

        c4.nextPlayer();
        for (int i = 0; i < boardCpy.getNoCols(); i++) {
            this.other.moveTo = i;
            c4.takeTurn();
            boolean isWin = c4.isWin(boardCpy);
            if (isWin) {
                debugPrint("Master can win. Make a move at: " + (i + 1) + " to block" + "My Color is " + this.getPlayerState());
                if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY) return i;
            }undoMove(boardCpy, i);
        }
        return -1;
    }




    //copy boardCpy and count number of turns
    public Board copyBoard(Board board) {
        Board copy = new Board(board.getNoCols(), board.getNoRows());
        int countTurns = 0;
        for (int i = 0; i < board.getNoCols(); i++) {
            for (int j = 0; j < board.getNoRows(); j++) {
                Location l = new Location(i, j);
                copy.setLocationState(l, board.getLocationState(l));
                if (board.getLocationState(l) != LocationState.EMPTY) countTurns++;
            }
        }
        setNumTurns(countTurns);
        return copy;
    }

    private int firstMove(Board board) {
        int midCol = board.getNoCols() / 2;
        //get state of bottom middle column
        LocationState midColState = board.getLocationState(new Location(midCol, board.getNoRows() - 1));

        //if middle col empty - take it.
        if (midColState == LocationState.EMPTY) return midCol;

        //if not, one to the left
        if (midColState == other.getPlayerState()) return midCol - 1;
        return -1;
    }

    private void undoMove(Board board, int col) {
        int i = 0;
        while (board.getLocationState(new Location(col, i)) == LocationState.EMPTY && i < board.getNoRows() - 1) {
            if (board.getLocationState(new Location(col, i + 1)) != LocationState.EMPTY) {
                board.setLocationState(new Location(col, (i + 1)), LocationState.EMPTY);
                break;
            }
            i++;
        }
    }


    public void setupGame() {
        this.me = new Player(this.getPlayerState());
        LocationState otherState = (me.getPlayerState() == LocationState.RED) ? LocationState.YELLOW : LocationState.RED;
        this.other = new Player(otherState);
        this.c4 = new Connect4(me, other, boardCpy);
    }


    private void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    private void debugPrint(String prompt) {
        if (this.debug) System.out.println(prompt);
    }

    private class Player extends IPlayer {
        int moveTo;

        public Player(LocationState playerState) {
            super(playerState);
            moveTo = 0;
        }

        @Override
        public int getMove(Board board) {
            return this.moveTo;
        }
    }


}
