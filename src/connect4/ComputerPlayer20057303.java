package connect4;

import java.util.ArrayList;
import java.util.Random;

/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Michal
 */

// */ he was giving 1 for one in a row, 5 for 2 and 10 for 3

public class ComputerPlayer20057303 extends IPlayer {
    private final int LIMIT = 2000;
    private final boolean debug = true;
    private static int ROWS = 0;
    private static int COLS = 0;
    private Player me, other;

    public ComputerPlayer20057303(LocationState playerState) {
        super(playerState);
    }

    @Override
    public int getMove(Board board) {
        ROWS = board.getNoRows();
        COLS = board.getNoCols();
        setupPlayers();
//        Board boardCopy = copyBoard(board);
//        Player p1 = new Player(me.getPlayerState());
//        Player p2 = new Player(other.getPlayerState());
//        Connect4 c4Copy = new Connect4(p1, p2, boardCopy);
//        int[] scores = checkForWinner(c4Copy, p1, boardCopy);
//        int move = getMax(scores, boardCopy);
        int move = simGame(board);
        return move;
    }

    private int[] checkForWinner(Connect4 c4, Player me, Board boardCpy) {
        LocationState state  = me.getPlayerState();
        int[] scores = new int[COLS];
        getScore:
        {
            for (int i = 0; i < COLS; i++) {
                int score = 0;
                if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                    me.moveTo = i;
                    c4.takeTurn();

                    //PATTERN MATCHING:
                    // + IS AN OUR STATE (Player me)
                    // - OPPONENT
                    // _ EMPTY
                    // ~ NOT AVAILABLE (Suspended on board / EMPTY field underneath)

                    //win
                    if (BoardChecker.localPatterMatch(state,boardCpy,"++++",i)) {
                        me.win = true;
                        score += 1000000;
                    }
                    //three in a row
                    if (BoardChecker.localPatterMatch(state,boardCpy,"_+++",i)) score+=1000;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"+_++",i)) score+=1000;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"++-+",i)) score+=1000;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"+++-",i)) score+=1000;

                    //block win
                    if (BoardChecker.localPatterMatch(state,boardCpy,"+---",i)) score+=2000;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"-+--",i)) score+=2000;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"--+-",i)) score+=2000;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"---+",i)) score+=2000;

                    //block pre win
                    if (BoardChecker.localPatterMatch(state,boardCpy,"_-+-_",i)) score+=500;
//                    if (BoardChecker.localPatterMatch(state,boardCpy,"_-+-_",i)) score+=500;
//                    if (BoardChecker.localPatterMatch(state,boardCpy,"_-+-_",i)) score+=500;
//                    if (BoardChecker.localPatterMatch(state,boardCpy,"_-+-_",i)) score+=500;

                    //can we get 2 in a row?
                    if (BoardChecker.localPatterMatch(state,boardCpy,"++__",i)) score+=250;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"_++_",i)) score+=250;
                    if (BoardChecker.localPatterMatch(state,boardCpy,"__++",i)) score+=250;


                    scores[i] = score;
                    undoMove(boardCpy, i);
                }
            }
        }
        return scores;
    }

    private int findRandomEmpty(Board boardCpy) {
        ArrayList<Integer> cols = new ArrayList<>();
        for (int i = 0; i < COLS; i++) {
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
        int p1FirstMove = -1;
        //array of moves, index represents column, for each our win in sim. +1 else -1.
        // Perhaps changing it around would make ai more defensive.
        int[] myCols = new int[board.getNoCols()];

        for (int i = 0; i < LIMIT; i++) {
            Player p1 = new Player(me.getPlayerState());
            Player p2 = new Player(other.getPlayerState());
            boolean firstMove = true;
            Board boardCopy = copyBoard(board);

            Connect4 c4Copy = new Connect4(p1, p2, boardCopy);
            sim:
            {
                while (true) {
                    //Player 1 (me/ai)
                    int[] scores = checkForWinner(c4Copy, p1, boardCopy);
                    p1.moveTo = getMax(scores, boardCopy);
                    //check for winner uses method to find random EMPTY col, if no winning scenario.
                    // -1 if board is full (due to lack of getter and setter for numTurns in main Connect4 class)
                    if (p1.moveTo == -1) break sim;

                    //save first move. this will be used to calculate how many wins looses are for each starting location.
                    if (firstMove) p1FirstMove = p1.moveTo;
//                    myCols[p1FirstMove] += scores[p1.moveTo];
                    if (firstMove && p1.win && i == 0) return p1.moveTo;

                    //if ai has next winning move, inc value for column. break out
                    if (p1.win) {
                        myCols[p1FirstMove]++;
                        break sim;
                    }

                    //if no win, or board not full take turn, and get next player.
                    c4Copy.takeTurn();
                    c4Copy.nextPlayer();

                    // checking for opponent win.
                    scores = checkForWinner(c4Copy, p2, boardCopy);
                    p2.moveTo = getMax(scores, boardCopy);

                    //as before ^ if board is full, break out.
                    if (p2.moveTo == -1) break sim;

//                    myCols[p1FirstMove] -= scores[p2.moveTo];
                    if (firstMove && p2.win && i == 0) {
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
        return getMax(myCols, board);
    }

    private int simGame(Board board) {
        int[] myCols = new int[COLS];
//        boolean firstMove = true;
        for (int i = 0; i < 500; i++) {
            Board boardCopy = copyBoard(board);
            Player p1 = new Player(me.getPlayerState());
            Player p2 = new Player(other.getPlayerState());
            Connect4 c4Copy = new Connect4(p1, p2, boardCopy);
            int firstMove = 0;//findRandomEmpty(boardCopy);
//            p1.moveTo = firstMove;
//            c4Copy.takeTurn();
//            c4Copy.nextPlayer();
            boolean first = true;

            while (true) {
                int[] scores = checkForWinner(c4Copy, p1, boardCopy);
                p1.moveTo = getMax(scores, boardCopy);
                if (first) {
                    firstMove = p1.moveTo;
                    first = false;
                }
                if (p1.moveTo == -1) break;
                if (p1.win) {
                    myCols[firstMove]++;
                    break;
                }
                c4Copy.takeTurn();
                c4Copy.nextPlayer();

                scores = checkForWinner(c4Copy, p2, boardCopy);
                p2.moveTo = getMax(scores, boardCopy);
                if (p2.moveTo == -1) break;
                if (p2.win) {
                    myCols[firstMove]--;
                    break;
                }
                c4Copy.takeTurn();
                c4Copy.nextPlayer();
            }
        }
//        System.out.println();
        int best = getMax(myCols, board);
        return best;
    }

    private int getMax(int[] candidateMoves, Board board) {
        if (candidateMoves.length == 0) return -1;

        int max = Integer.MIN_VALUE;
        int myBestMove = 0;
        for (int i = 0; i < candidateMoves.length; i++) {
            if (candidateMoves[i] >= max && board.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                max = candidateMoves[i];
                myBestMove = i;
            }
        }
        ArrayList<Integer> maxes = new ArrayList<>();
        for (int i = 0; i < candidateMoves.length; i++) {
            if (candidateMoves[i] == max) maxes.add(i);
        }
        if (maxes.size() > 1) {
            Random r = new Random();
            int rand = r.nextInt(maxes.size());
            myBestMove = maxes.get(rand);
        }
        if (candidateMoves[myBestMove] == 0) myBestMove = findRandomEmpty(board);
        return myBestMove;
    }//end getMax()



    //copy game board
    private Board copyBoard(Board board) {
        Board copy = new Board(COLS, ROWS);
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Location l = new Location(i, j);
                copy.setLocationState(l, board.getLocationState(l));
            }
        }
        return copy;
    }

    //undo move. Takes last disc from specified column
    private void undoMove(Board board, int col) {
        int i = 0;
        while (board.getLocationState(new Location(col, i)) == LocationState.EMPTY && i < ROWS - 1) {
            if (board.getLocationState(new Location(col, i + 1)) != LocationState.EMPTY) {
                board.setLocationState(new Location(col, (i + 1)), LocationState.EMPTY);
                break;
            }
            i++;
        }
    }


    //Set up player states to duplicate main game.
    private void setupPlayers() {
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
