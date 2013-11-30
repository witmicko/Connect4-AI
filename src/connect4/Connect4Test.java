package connect4;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 27/11/13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class Connect4Test {
    IPlayer p1, p2;
    Board board;
    Connect4 c4;

    @Before
    public void setup() {
        p1 = new ComputerPlayer_1_random(LocationState.YELLOW);
        p2 = new ComputerPlayer_1_random(LocationState.RED);
        board = new Board(7, 6);
        c4 = new Connect4(p1, p2, board);
    }

    @After
    public void clean() {
        board.clear();
    }


    //    @Test
    public void testIsVerticalWin() throws Exception {
        board.setLocationState(new Location(0, 1), LocationState.RED);
        board.setLocationState(new Location(0, 2), LocationState.RED);
        board.setLocationState(new Location(0, 3), LocationState.RED);
        board.setLocationState(new Location(0, 4), LocationState.RED);
        assertTrue(c4.isWin(board));
        board.clear();

        board.setLocationState(new Location(4, 1), LocationState.RED);
        board.setLocationState(new Location(4, 2), LocationState.RED);
        board.setLocationState(new Location(4, 3), LocationState.YELLOW);
        board.setLocationState(new Location(4, 4), LocationState.RED);
        System.out.println(c4.isWin(board));
        assertFalse(c4.isWin(board));
        board.clear();
    }

    //    @Test
    public void testHorizontalWin() throws Exception {
        board.setLocationState(new Location(1, 0), LocationState.RED);
        board.setLocationState(new Location(2, 0), LocationState.RED);
        board.setLocationState(new Location(3, 0), LocationState.RED);
        board.setLocationState(new Location(4, 0), LocationState.RED);
        boolean win = c4.isWin(board);
        assertTrue(win);
        board.clear();

//        this should fail
        board.setLocationState(new Location(1, 0), LocationState.RED);
        board.setLocationState(new Location(2, 0), LocationState.RED);
        board.setLocationState(new Location(3, 0), LocationState.YELLOW);
        board.setLocationState(new Location(4, 0), LocationState.RED);
        win = c4.isWin(board);
        assertFalse(win);
        board.clear();
    }

    //    @Test
    public void testUpDownWin() throws Exception {
        board.setLocationState(new Location(0, 0), LocationState.RED);
        board.setLocationState(new Location(1, 1), LocationState.RED);
        board.setLocationState(new Location(2, 2), LocationState.RED);
        board.setLocationState(new Location(3, 3), LocationState.RED);
        boolean win = c4.isWin(board);
        assertTrue(win);
        board.clear();

        board.setLocationState(new Location(0, 2), LocationState.RED);
        board.setLocationState(new Location(1, 3), LocationState.RED);
        board.setLocationState(new Location(2, 4), LocationState.RED);
        board.setLocationState(new Location(3, 5), LocationState.RED);
        win = c4.isWin(board);
        assertTrue(win);
        board.clear();

        board.setLocationState(new Location(1, 1), LocationState.RED);
        board.setLocationState(new Location(2, 2), LocationState.RED);
        board.setLocationState(new Location(3, 3), LocationState.RED);
        board.setLocationState(new Location(4, 4), LocationState.RED);
        win = c4.isWin(board);
        assertTrue(win);
        board.clear();

        board.setLocationState(new Location(2, 2), LocationState.RED);
        board.setLocationState(new Location(3, 3), LocationState.RED);
        board.setLocationState(new Location(4, 4), LocationState.RED);
        board.setLocationState(new Location(5, 5), LocationState.RED);
        win = c4.isWin(board);
        assertTrue(win);
        board.clear();

        board.setLocationState(new Location(1, 0), LocationState.RED);
        board.setLocationState(new Location(2, 1), LocationState.RED);
        board.setLocationState(new Location(3, 2), LocationState.RED);
        board.setLocationState(new Location(4, 3), LocationState.RED);
        win = c4.isWin(board);
        assertTrue(win);
        board.clear();

        board.setLocationState(new Location(board.getNoCols() - 4, 0), LocationState.RED);
        board.setLocationState(new Location(board.getNoCols() - 3, 1), LocationState.RED);
        board.setLocationState(new Location(board.getNoCols() - 2, 2), LocationState.RED);
        board.setLocationState(new Location(board.getNoCols() - 1, 3), LocationState.RED);
        win = c4.isWin(board);
        assertTrue(win);
        board.clear();

        board.setLocationState(new Location(board.getNoCols() - 4, 1), LocationState.RED);
        board.setLocationState(new Location(board.getNoCols() - 3, 2), LocationState.RED);
        board.setLocationState(new Location(board.getNoCols() - 2, 3), LocationState.YELLOW);
        board.setLocationState(new Location(board.getNoCols() - 1, 4), LocationState.RED);
        win = c4.isWin(board);
        assertFalse(win);
        board.clear();
    }

    @Test
    public void testDownUpWin() {
//        board.setLocationState(new Location(0, 5), LocationState.RED);
//        board.setLocationState(new Location(1, 4), LocationState.RED);
//        board.setLocationState(new Location(2, 3), LocationState.RED);
//        board.setLocationState(new Location(3, 2), LocationState.RED);
//        boolean win = c4.isWin(board);
//        assertTrue(win);
//        board.clear();
//
//        board.setLocationState(new Location(0, 4), LocationState.RED);
//        board.setLocationState(new Location(1, 3), LocationState.RED);
//        board.setLocationState(new Location(2, 2), LocationState.RED);
//        board.setLocationState(new Location(3, 1), LocationState.RED);
//        win = c4.isWin(board);
//        assertTrue(win);
//        board.clear();
//
//
//        board.setLocationState(new Location(0, 3), LocationState.RED);
//        board.setLocationState(new Location(1, 2), LocationState.RED);
//        board.setLocationState(new Location(2, 1), LocationState.RED);
//        board.setLocationState(new Location(3, 0), LocationState.RED);
//        win = c4.isWin(board);
//        assertTrue(win);
//        board.clear();
//
//        board.setLocationState(new Location(0, 3), LocationState.RED);
//        board.setLocationState(new Location(1, 2), LocationState.RED);
//        board.setLocationState(new Location(2, 1), LocationState.YELLOW);
//        board.setLocationState(new Location(3, 0), LocationState.RED);
//        win = c4.isWin(board);
//        assertTrue(win);
//        board.clear();


        board.setLocationState(new Location(2, 6), LocationState.RED);
        board.setLocationState(new Location(3, 5), LocationState.RED);
        board.setLocationState(new Location(4, 4), LocationState.YELLOW);
        board.setLocationState(new Location(5, 3), LocationState.RED);
        boolean win = c4.isWin(board);
        assertTrue(win);


    }

    public void testTakeTurn() throws Exception {

    }
}
