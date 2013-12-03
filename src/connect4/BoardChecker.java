package connect4;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 03/12/13
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class BoardChecker {

    public static boolean matchFor(Board board, int count) {
        //count 3 for win.
        return downUp(board, count) || upDown(board, count) || horizontalMatch(board, count) || verticalMatch(board, count);
    }


    private static boolean horizontalMatch(Board board, int count) {
        for (int i = 0; i < board.getNoRows(); i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoCols() - 1; j++) {
                int x = j, y = i;
                inRow = compareStates(x, y, x + 1, y, board, inRow);
                if (inRow >= count) return true;
            }
        }
        return false;
    }//end horizontalMatch()

    private static boolean verticalMatch(Board board, int count) {
        for (int i = 0; i < board.getNoCols(); i++) {
            int inRow = 0;
            for (int j = 0; j < board.getNoRows() - 1; j++) {
                int x = i;
                int y = j;
                inRow = compareStates(x, y, x, y + 1, board, inRow);
                if (inRow >= count) return true;

            }
        }
        return false;
    }//end verticalMatch()

    private static boolean upDown(Board board, int count) {
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
                inRow = compareStates(x, y, x + 1, y + 1, board, inRow);
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
                inRow = compareStates(x, y, x + 1, y + 1, board, inRow);
                if (inRow >= count) return true;

            }
        }
        return false;
    }// end upDown()

    private static boolean downUp(Board board, int count) {
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
                inRow = compareStates(x, y, x + 1, y - 1, board, inRow);
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
                inRow = compareStates(x, y, x + 1, y - 1, board, inRow);
                if (inRow >= count) return true;
            }
        }
        return false;
    }//end downUp()

    private static int compareStates(int x, int y, int xNext, int yNext, Board board, int inRow) {
        LocationState state = board.getLocationState(new Location(x, y));
        if (state != LocationState.EMPTY) {
            LocationState nextState = board.getLocationState(new Location(xNext, yNext));
            if (state == nextState) return inRow + 1;
        }
        return 0;
    }// end compareStates()
}
