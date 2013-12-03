package connect4;

import edu.princeton.cs.introcs.StdRandom;

import java.util.ArrayList;

/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Frank
 */
public class ComputerPlayer20057303 extends IPlayer {
    private int LIMIT = 500;
    private boolean debug = true;
    private int numTurns;
    private Player me, other;
    private Board boardCpy;
    private Connect4 c4;
    private int maxDepth; //0 - random > more = harder ai.

    public ComputerPlayer20057303(LocationState playerState) {
        super(playerState);
    }

    @Override
    public int getMove(Board board) {
//        this.boardCpy = copyBoard(board);
        setupGame();
//        if (this.numTurns < 2) return firstMove(this.boardCpy);

//        int moveTo = checkForWinner(me, other, this.boardCpy);
//        return moveTo;
        int move = simulateGame(board);

        return move;
    }


    private int checkForWinner(Connect4 c4, Player me, Board boardCpy) {
//        debugPrint("MInimax.checkForWinner\n" + c4.getBoard().toString());
        me.win = false;

        for (int i = 0; i < boardCpy.getNoCols(); i++) {
            if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY) me.moveTo = i;
            else break;
            c4.takeTurn();
            boolean isWin = c4.isWin(boardCpy);
//            debugPrint(isWin+"");
            if (isWin) {
//                debugPrint("I can win. make a move at: " + (i + 1) + " to slaughter human\n" + "My Color is " + this.getPlayerState());
                if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                    me.win = true;
                    return i;
                }
            }
            undoMove(boardCpy, i);
        }
        //if no winnerreturn random.
        return findRandomEmpty(boardCpy);
    }

    private int findRandomEmpty(Board boardCpy) {
        ArrayList<Integer> cols = new ArrayList<>();
        for (int i = 0; i < boardCpy.getNoCols(); i++) {
            if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                cols.add(i);
            }
        }
        if (cols.size() > 0) {
            int rand = StdRandom.uniform(0, cols.size());
            int mo = cols.get(rand);
            return mo;
        } else return -1;
    }


    public int simulateGame(Board board) {
//        debugPrint("sim board \n"+ board.toString());
        int p1FirstMove = 0;
        int[] cols = new int[board.getNoCols()];

        for (int i = 0; i < LIMIT; i++) {
            Player p1 = new Player(me.getPlayerState());
            Player p2 = new Player(other.getPlayerState());
            p1.win = p2.win = false;
            boolean fisrtMove = true;
            Board b = copyBoard(board);

            Connect4 c4Copy = new Connect4(p1, p2, b);
            sim:
            {
                while (!c4Copy.isWin(b)) {
                    p1.moveTo = checkForWinner(c4Copy, p1, b);
                    if (p1.moveTo == -1) {
                        break sim;
                    }
                    if (fisrtMove) {
                        p1FirstMove = p1.moveTo;
                        fisrtMove = false;
                    }
                    if (p1.win) {
                        cols[p1FirstMove]++;
                        break sim;
                    }
                    c4Copy.takeTurn();
                    c4Copy.nextPlayer();
                    p2.moveTo = checkForWinner(c4Copy, p2, b);
                    if (p2.moveTo == -1) {
                        break sim;
                    }

                    if (p2.win) {
                        cols[p1FirstMove]--;
                        break sim;
                    }
                    c4Copy.takeTurn();
                    c4Copy.nextPlayer();
                }
            }

//            LIMIT -=100;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < cols.length; i++) {
            if (cols[i] > max && board.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                max = cols[i];
                bestMove = i;
            }
        }

        if (bestMove == -1) {
            System.out.println("show me");
        }
        return bestMove;
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
        boolean win;

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
