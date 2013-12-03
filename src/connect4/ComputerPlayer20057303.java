package connect4;

import edu.princeton.cs.introcs.StdRandom;

import java.util.ArrayList;
import java.util.Random;

/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Michal
 */
public class ComputerPlayer20057303 extends IPlayer {
    private final int LIMIT = 1000;
    private final boolean debug = true;
    private Player me, other;

    public ComputerPlayer20057303(LocationState playerState) {
        super(playerState);
    }

    @Override
    public int getMove(Board board) {
        setupGame();
        return simulateGame(board);
    }


    private int checkForWinner(Connect4 c4, Player me, Board boardCpy) {
        me.win = false;
        for (int i = 0; i < boardCpy.getNoCols(); i++) {
            LocationState thisState = boardCpy.getLocationState(new Location(i, 0));
            if (thisState == LocationState.EMPTY) me.moveTo = i;
            else break;
            c4.takeTurn();
            if (c4.isWin(boardCpy)) {
                if (thisState == LocationState.EMPTY) {
                    me.win = true;
                    return i;
                }
            }
            undoMove(boardCpy, i);
        }
        //if no winner return random empty location.
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
            Random r = new Random();
            int rand = r.nextInt(cols.size());
            return cols.get(rand);
        } else return -1;
    }


    private int simulateGame(Board board) {
        int p1FirstMove = 0;
        //array of moves, index represents column, for each our win in sim. +1 else -1.
        // Perhaps changing it around would make ai more defensive.
        int[] myCols = new int[board.getNoCols()];

        for (int i = 0; i < LIMIT; i++) {
            Player p1 = new Player(me.getPlayerState());
            Player p2 = new Player(other.getPlayerState());
            boolean firstMove = true;
            Board boardCopy = copyBoard(board);

            Connect4 c4Copy = new Connect4(p1, p2, boardCopy);
//            Connect4 c4Copy = new Connect4(p2,p1, boardCopy);
            sim:
            {
                while (!c4Copy.isWin(boardCopy)) {
                    //Player 1 (me/ai)
                    p1.moveTo = checkForWinner(c4Copy, p1, boardCopy);

                    //check for winner uses method to find random EMPTY col, if no winning scenario.
                    // -1 if board is full (due to lack of getter and setter for numTurns in main Connect4 class)
                    if (p1.moveTo == -1) break sim;

                    //save first move. this will be used to calculate how many wins looses are for each starting location.
                    if (firstMove) p1FirstMove = p1.moveTo;
//                    if (firstMove && p1.win) return p1.moveTo;

                    //if ai has next winning move, inc value for column. break out
                    if (p1.win) {
                        myCols[p1FirstMove]++;
                        break sim;
                    }
                    //if no win, or board not full take turn, and get next player.
                    c4Copy.takeTurn();
                    c4Copy.nextPlayer();

                    // checking for opponent win.
                    p2.moveTo = checkForWinner(c4Copy, p2, boardCopy);

                    //as before ^ if board is full, break out.
                    if (p2.moveTo == -1) {
                        break sim;
                    }
                    if (firstMove && p2.win) {
//                        System.out.println("block at " + (p2.moveTo + 1));
                        return p2.moveTo;
                    }

                    //if opponent has a winning move. Decrease val for col. and break out.
                    if (p2.win) {
                        myCols[p1FirstMove]--;
                        break sim;
                    }
                    //if we got here, repeat.
                    c4Copy.takeTurn();
                    c4Copy.nextPlayer();
                    firstMove = false;
                }
            }
        }

        //Find max value for next move. Greater the value, more wins it spanned out using this next move.
        int max = Integer.MIN_VALUE;
        int myBestMove = 0;
        for (int i = 0; i < myCols.length; i++) {
            if (myCols[i] > max && board.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                max = myCols[i];
                myBestMove = i;
            }
        }
        //if myCols[bestMove] < 0
        return myBestMove;
    }

    //copy game board
    private Board copyBoard(Board board) {
        Board copy = new Board(board.getNoCols(), board.getNoRows());
        for (int i = 0; i < board.getNoCols(); i++) {
            for (int j = 0; j < board.getNoRows(); j++) {
                Location l = new Location(i, j);
                copy.setLocationState(l, board.getLocationState(l));
            }
        }
        return copy;
    }

    //undo move. Takes last disc from specified column
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


    //Set up player states to duplicate main game.
    private void setupGame() {
        this.me = new Player(this.getPlayerState());
        LocationState otherState = (me.getPlayerState() == LocationState.RED) ? LocationState.YELLOW : LocationState.RED;
        this.other = new Player(otherState);
    }


    private void debugPrint(String prompt) {
        if (this.debug) System.out.println(prompt);
    }

    //Helping class to store dummy AI in our AI.
    private class Player extends IPlayer {
        int moveTo; // used to change val of next move.
        boolean win; // check if player has a next winning move.

        public Player(LocationState playerState) {
            super(playerState);
        }

        @Override
        public int getMove(Board board) {
            return this.moveTo;
        }
    }
}
