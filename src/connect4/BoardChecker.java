package connect4;

import edu.princeton.cs.introcs.Stopwatch;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 03/12/13
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class BoardChecker {
    private static StringBuffer str = new StringBuffer();
    private static int COLS = 0;
    private static int ROWS = 0;
    private static String PATTERN = "";
    private static String PATTERN_REV = "";

    private static LocationState STATE = LocationState.EMPTY;
    private static LocationState OTHER_STATE = LocationState.EMPTY;
    private static Board BOARD = new Board(0, 0);


    public static boolean matchFor(LocationState state, Board board, String pattern) {
        COLS = board.getNoCols();
        ROWS = board.getNoRows();
        PATTERN = pattern;
        PATTERN_REV = new StringBuffer(pattern).reverse().toString();
        STATE = state;
        OTHER_STATE = (STATE == LocationState.RED) ? LocationState.YELLOW : LocationState.RED;
        BOARD = board;
        return horizontalMatch() || verticalMatch() || upDown() || downUp();
    }


    private static boolean horizontalMatch() {
        for (int i = ROWS - 1; i >= 0; i--) {
            str.setLength(0);
            for (int j = 0; j < COLS; j++) {
                int x = j, y = i;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
        return false;
    }//end horizontalMatch()

    private static boolean verticalMatch() {
        for (int i = 0; i < COLS; i++) {
            str.setLength(0);
            for (int j = ROWS - 1; j >= 0; j--) {
                int x = i;
                int y = j;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
        return false;
    }//end verticalMatch()

    private static boolean upDown() {
//      *
//      **
//      ***
//      ****
//      *****
        for (int i = 0; i < ROWS - 3; i++) {
            str.setLength(0);
            for (int j = 0; j < (ROWS - i); j++) {
                int x = j;
                int y = j + i;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
//      *****
//       ****
//        ***
//         **
//          *
        for (int i = 1; i < COLS - 3; i++) {
            str.setLength(0);
            for (int j = 0; j < ROWS - i + 1; j++) {
                int x = j + i;
                int y = j;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
        return false;
    }// end upDown()

    private static boolean downUp() {
//      *****
//      ****
//      ***
//      **
//      *
        for (int i = 0; i < ROWS - 3; i++) {
            str.setLength(0);
            for (int j = 0; j < ROWS - i; j++) {
                int x = j;
                int y = (ROWS) - 1 - i - j;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
//          *
//         **
//        ***
//       ****
//      *****
        for (int i = 1; i < COLS - 3; i++) {
            str.setLength(0);
            for (int j = 0; j < ROWS - i + 1; j++) {
                int x = j + i;
                int y = ROWS - j - 1;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
        return false;
    }//end downUp()

    private static char compareStates(int x, int y) {
        Location location = new Location(x, y);
        if (BOARD.getLocationState(location) == STATE) return '+';
        if (BOARD.getLocationState(location) == OTHER_STATE) return '-';
        boolean isaa = isAvailable(x, y);
        if (isaa) return '_';
        return '~';
    }// end compareStates()

    private static boolean isAvailable(int x, int y) {
//        if (y == ROWS - 1 && BOARD.getLocationState(new Location(x, y)) == LocationState.EMPTY) return true;
        boolean bol = (y < ROWS-1) && !(BOARD.getLocationState(new Location(x, y + 1)) == LocationState.EMPTY);
        if (bol) return true;
        return false;
    }
}
