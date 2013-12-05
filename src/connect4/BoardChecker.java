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
        for (int i = 0; i < ROWS - 3; i++) {
            str.setLength(0);
            for (int j = 0; j < (ROWS - i); j++) {
                int x = j;
                int y = j + i;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }

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
        for (int i = 0; i < ROWS - 3; i++) {
            str.setLength(0);
            for (int j = 0; j < ROWS - i; j++) {
                int x = j;
                int y = (ROWS) - 1 - i - j;
                str.append(compareStates(x, y));
            }
            if (str.indexOf(PATTERN) > -1 || str.indexOf(PATTERN_REV) > -1) return true;
        }
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
        boolean bol = (y < ROWS - 1) && (BOARD.getLocationState(new Location(x, y + 1)) == LocationState.EMPTY);
        if (bol) return false;
        return true;
    }

    public static boolean makesPatern(int col, Board board, String pattern) {
        int row = findLandingRow(col, board);
        BOARD = board;
        ROWS = board.getNoRows();
        COLS = board.getNoCols();
        STATE = board.getLocationState(new Location(col, row));
        OTHER_STATE = (STATE == LocationState.RED) ? LocationState.YELLOW : LocationState.RED;
        PATTERN = pattern;
        PATTERN_REV = new StringBuffer(pattern).reverse().toString();


        //horizontally
        str.setLength(0);
        for (int i = 0; i < board.getNoCols(); i++) {
            int k = Math.abs(col - i);
            if (k < 4) str.append(compareStates(i, row));
        }
        System.out.println("hor>  " + str);
//        if(str.indexOf(PATTERN)>-1)return true;

        //verticaly
        str.setLength(0);
        for (int i = 0; i < board.getNoRows(); i++) {
            int k = Math.abs(row - i);
            if (k < 4) str.append(compareStates(col, i));
        }
        System.out.println("vert>  " + str);
//        if(str.indexOf(PATTERN)>-1)return true;

        //diagonal down up /
        str.setLength(0);
        for (int i = col, j = row; i < COLS && j >= 0; i++, j--) {
            int k = Math.abs(col - i);
            if (k < 4) str.append(compareStates(i, j));
        }
//        System.out.println("diag from: "+str);
        StringBuffer str2 = new StringBuffer("");
        for (int i = col - 1, j = row + 1; i >= 0 && j < ROWS; i--, j++) {
            int k = Math.abs(col - i);
            if (k < 4) str2.append(compareStates(i, j));
        }
//        System.out.println("diag to: "+str2);
        StringBuffer s =  new StringBuffer(str2).reverse();
        s.append(str);
        System.out.println("diag up right> " + s);

        //__________________________________________
        //____________________________________
        //diagonal up down
        str.setLength(0);
        for (int i = col, j = row; i < COLS && j < ROWS; i++, j++) {
            int k = Math.abs(col - i);
            if (k < 4) str.append(compareStates(i, j));
        }
        System.out.println("diag from: "+str);
        str2.setLength(0);
        for (int i = col - 1, j = row - 1; i >= 0 && j >= 0; i--, j--) {
            int k = Math.abs(col - i);
            if (k < 4) str2.append(compareStates(i, j));
        }
        System.out.println("diag to: "+str2);
//        s =  new StringBuffer(str2).reverse();
        s =  new StringBuffer(str2).reverse();
        s.append(str);
        System.out.println("diag down right> " + s);


        return false;
    }

    public static int findLandingRow(int col, Board board) {
        int row = 0;
        while (board.getLocationState(new Location(col, row)) == LocationState.EMPTY) {
            row++;
            if (row == board.getNoRows()) break;
        }
        return row;
    }
}
