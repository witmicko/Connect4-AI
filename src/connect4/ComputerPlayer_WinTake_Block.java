package connect4;

import edu.princeton.cs.introcs.StdRandom;

import java.util.ArrayList;
import java.util.Random;

/**
 * Example Computer Player.
 * CREATE YOUR OWN VERSION OF THIS, REPLACING THE NUMBER IN THE CLASS NAME
 * WITH YOUR STUDENT NUMBER.
 *
 * @author Frank
 */
public class ComputerPlayer_WinTake_Block extends IPlayer {
    private Player me, other;
    private Board board;
    private Connect4 c4;

    public ComputerPlayer_WinTake_Block(LocationState playerState) {
        super(playerState);
    }

    @Override
    public int getMove(Board board) {
        this.board = copyBoard(board);
        setupGame();

        int moveTo = checkForWinner(this.board);

        if(moveTo<0)moveTo = findRandomEmpty(this.board);
        return moveTo;
    }
    private int findRandomEmpty(Board boardCpy) {
        ArrayList<Integer> cols = new ArrayList<>();
        for (int i = 0; i < boardCpy.getNoCols(); i++) {
            if (boardCpy.getLocationState(new Location(i, 0)) == LocationState.EMPTY) {
                cols.add(i);
            }
        }
        if (cols.size() > 0){
            int rand = StdRandom.uniform(0, cols.size());
            int mo = cols.get(rand);
            return mo;
        }
        else return 0;
    }


    private int checkForWinner(Board board) {
        for (int i = 0; i < board.getNoCols(); i++) {
            if (board.getLocationState(new Location(i, 0)) == LocationState.EMPTY)this.me.moveTo = i;
            else break;
            c4.takeTurn();
            if (c4.isWin(board)) {
                if (board.getLocationState(new Location(i, 0)) == LocationState.EMPTY) return i;
            }
            undoMove(board, i);
        }
        c4.nextPlayer();
        for (int i = 0; i < board.getNoCols(); i++) {
            if (board.getLocationState(new Location(i, 0)) == LocationState.EMPTY)this.other.moveTo = i;
            else break;
            c4.takeTurn();
            boolean isWin = c4.isWin(board);
            if (isWin) {
//                System.out.println("Master can win. Make a move at: " + (i + 1) + " to block");
                if (board.getLocationState(new Location(i, 0)) == LocationState.EMPTY) return i;
            }
            undoMove(board, i);
        }
        return -1;
    }

    public Board copyBoard(Board board) {
        Board copy = new Board(board.getNoCols(), board.getNoRows());
        for (int i = 0; i < board.getNoCols(); i++) {
            for (int j = 0; j < board.getNoRows(); j++) {
                Location l = new Location(i, j);
                copy.setLocationState(l, board.getLocationState(l));
            }
        }
        return copy;
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
        this.c4 = new Connect4(me, other, board);
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
