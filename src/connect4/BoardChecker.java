package connect4;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 03/12/13
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class BoardChecker {

    public static boolean matchFor(LocationState state, Board board, int count, String pattern) {
        //count 3 for win.
        boolean dnUp = downUp(state, board, count,pattern);
        boolean upDn = upDown(state, board, count,pattern);
        boolean hor = horizontalMatch(state, board, count,pattern);
        boolean ver = verticalMatch(state, board, count,pattern);
        return dnUp || upDn || hor || ver;
    }


    private static boolean horizontalMatch(LocationState state, Board board, int count, String pattern) {
        String str = "";
        for (int i = 0; i < board.getNoRows(); i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoCols() - 1; j++) {
                int x = j, y = i;
                str = compareStates(x, y, x + 1, y, board, inRow,state);
                if (inRow >= count) return true;
            }
        }
        return false;
    }//end horizontalMatch()

    private static boolean verticalMatch(LocationState state, Board board, int count, String pattern) {
        for (int i = 0; i < board.getNoCols(); i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoRows() - 1; j++) {
                int x = i;
                int y = j;
                inRow = compareStates(x, y, x, y + 1, board, inRow, state);
                if (inRow >= count) return true;
            }
        }
        return false;
    }//end verticalMatch()

    private static boolean upDown(LocationState state, Board board, int count, String pattern) {
//      *
//      **
//      ***
//      ****
//      *****
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            int inRow = 0;
            for (int j = 0; j < (board.getNoRows() - 1 - i); j++) {
                int x = j;
                int y = j + i;
                inRow = compareStates(x, y, x + 1, y + 1, board, inRow, state);
                if (inRow >= count) return true;

            }
        }
//      *****
//       ****
//        ***
//         **
//          *
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = j;
                inRow = compareStates(x, y, x + 1, y + 1, board, inRow, state);
                if (inRow >= count) return true;

            }
        }
        return false;
    }// end upDown()

    private static boolean downUp(LocationState state, Board board, int count, String pattern) {
//      *****
//      ****
//      ***
//      **
//      *
        for (int i = 0; i < board.getNoRows() - 3; i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoRows() - 1 - i; j++) {
                int x = j;
                int y = (board.getNoRows() - 1) - i - j;
                inRow = compareStates(x, y, x + 1, y - 1, board, inRow, state);
                if (inRow >= count) return true;
            }
        }
//          *
//         **
//        ***
//       ****
//      *****
        for (int i = 1; i < board.getNoCols() - 3; i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoRows() - i; j++) {
                int x = j + i;
                int y = board.getNoRows() - j - 1;
                inRow = compareStates(x, y, x + 1, y - 1, board, inRow, state);
                if (inRow >= count) return true;
            }
        }
        return false;
    }//end downUp()

    private static String compareStates(int x, int y, int xNext, int yNext, Board board, int inRow, LocationState state) {
        LocationState currLoc = board.getLocationState(new Location(x, y));
        if (currLoc != LocationState.EMPTY) {
            LocationState nextState = board.getLocationState(new Location(xNext, yNext));
            if (state == nextState) return inRow + 1;
        }
        return -1;
    }// end compareStates()
}
