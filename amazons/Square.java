package amazons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static amazons.Utils.*;

/** Represents a position on an Amazons board.  Positions are numbered
 *  from 0 (lower-left corner) to 99 (upper-right corner).  Squares
 *  are immutable and unique: there is precisely one square created for
 *  each distinct position.  Clients create squares using the factory method
 *  sq, not the constructor.  Because there is a unique Square object for each
 *  position, you can freely use the cheap == operator (rather than the
 *  .equals method) to compare Squares, and the program does not waste time
 *  creating the same square over and over again.
 *  @author John Markham
 */
final class Square {

    /** The regular expression for a square designation (e.g.,
     *  a3). For convenience, it is in parentheses to make it a
     *  group.  This subpattern is intended to be incorporated into
     *  other pattern that contain square designations (such as
     *  patterns for moves). */
    static final String SQ = "([a-j](?:[1-9]|10))";

    /** Return my row position, where 0 is the bottom row. */
    int row() {
        return _row;
    }

    /** Return my column position, where 0 is the leftmost column. */
    int col() {
        return _col;
    }

    /** Return my index position (0-99).  0 represents square a1, and 99
     *  is square j10. */
    int index() {
        return _index;
    }

    /** Return true iff THIS - TO is a valid queen move. */
    boolean isQueenMove(Square to) {
        if (this == to
                || to.index() < 0
                || to.index() > Board
                .SIZE * Board.SIZE - 1
                || !exists(to.col(), to.row())) {
            return false;
        }
        if (this.col() == to.col() && this.row() == to.row()) {
            return false;
        } else if (this.col() == to.col()) {
            return true;
        } else if (this.row() == this.row()) {
            return true;
        } else {
            return Math.abs(this.row() - to
                    .row()) == Math.abs(this.col() - to.col());
        }
    }

    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIR = {
        { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
        { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Return the Square that is STEPS>0 squares away from me in direction
     *  DIR, or null if there is no such square.
     *  DIR = 0 for north, 1 for northeast, 2 for east, etc.,
     *  up to 7 for northwest.
     *  If DIR has another value, return null. Thus, unless the
     *  result is null the resulting square is a queen move away from me. */
    Square queenMove(int dir, int steps) {
        if (dir < 0 || dir > 7) {
            return null;
        }
        int[] setter = DIR[dir];
        int columnChecker = col() + steps * DIR[dir][0];
        int rowChecker = row() + steps * DIR[dir][1];
        if (!exists(columnChecker, rowChecker)) {
            return null;
        }
        Square toReturn = sq(columnChecker, rowChecker);
        return toReturn;
    }

    /** Return the direction (an int as defined in the documentation
     *  for queenMove) of the queen move THIS-TO. */
    int direction(Square to) {
        assert isQueenMove(to);
        if (to.row() > row()) {
            if (to.col() == col()) {
                return 0;
            } else if (to.col() > col()) {
                return 1;
            } else if (to.col() < col()) {
                return 7;
            }
        } else if (to.row() < row()) {
            if (to.col() == col()) {
                return 4;
            } else if (to.col() > col()) {
                return 3;
            } else if (to.col() < col()) {
                return 5;
            }
        } else if (to.row() == row()) {
            if (to.col() > col()) {
                return 2;
            } else {
                return 6;
            }
        }
        return 2;
    }

    @Override
    public String toString() {
        return _str;
    }

    /** Return true iff COL ROW is a legal square. */
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.SIZE && col < Board.SIZE;
    }

    /** Return true iff index is a legal square.
     * @param index to check.
     * */
    static boolean exists(int index) {
        return index >= 0 && index <= Board.SIZE * Board.SIZE - 1;
    }

    /** Return the (unique) Square denoting COL ROW. */
    static Square sq(int col, int row) {
        if (!exists(row, col)) {
            throw error("row or column out of bounds");
        }
        return SQUARES[findIndex(col, row)];
    }

    /** Return the (unique) Square denoting the position with index INDEX. */
    static Square sq(int index) {
        return SQUARES[index];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). */
    static Square sq(String col, String row) {
        try {
            int first = Board.SIZE * _integerMappings.get(col);
            int index = first + Integer.parseInt(row);
            return SQUARES[index];
        } catch (NumberFormatException excp) {
            throw new NumberFormatException("Please "
                    + "enter a valid column row configuration.");
        }

    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. */
    static Square sq(String posn) {
        assert posn.matches(SQ);
        String first = posn.substring(0, 1);
        String second = posn.substring(1);
        int indexer = _integerMappings
                .get(first) * 10 + Integer.parseInt(second) - 1;
        return SQUARES[indexer];
    }

    /** Sets up the mappings from strings to integers
     * for setting up _str's. */
    public void setUpMappings() {
        _characterMappings.put(0, "a");
        _characterMappings.put(1, "b");
        _characterMappings.put(2, "c");
        _characterMappings.put(3, "d");
        _characterMappings.put(4, "e");
        _characterMappings.put(5, "f");
        _characterMappings.put(6, "g");
        _characterMappings.put(7, "h");
        _characterMappings.put(8, "i");
        _characterMappings.put(9, "j");
    }

    /** Sets up the mappings from integers to strings
     * for setting up _str's. */
    public void setUpInverse() {
        _integerMappings.put("a", 0);
        _integerMappings.put("b", 1);
        _integerMappings.put("c", 2);
        _integerMappings.put("d", 3);
        _integerMappings.put("e", 4);
        _integerMappings.put("f", 5);
        _integerMappings.put("g", 6);
        _integerMappings.put("h", 7);
        _integerMappings.put("i", 8);
        _integerMappings.put("j", 9);
    }

    /** Return an iterator over all Squares. */
    static Iterator<Square> iterator() {
        return SQUARE_LIST.iterator();
    }

    /** Return the Square with index INDEX. */
    Square(int index) {
        if ((index > Board.SIZE * Board.SIZE - 1 || index < 0)) {
            throw new
                    IndexOutOfBoundsException("incorrect index.");
        }
        if (_characterMappings.get(0) == null) {
            setUpMappings();
        }
        if (_integerMappings.get(0) == null) {
            setUpInverse();
        }
        _index = index;
        _row = findRows(_index);
        _col = findCols(_index);
        _str = findString(_row, _col);
    }

    /** Find the string representation of the cols/rows given.
     * @return the rows of this index.
     * @param rows for me to extract row from.
     * @param cols for me to extract col from.
     * */
    public String findString(int rows, int cols) {
        if (rows > 9 || cols > 9
                || cols % 1 != 0 || rows % 1 != 0) {
            throw new IndexOutOfBoundsException("incorrect cols/rows");
        }
        String first = _characterMappings.get(cols) + " ";
        rows += 1;
        String second = rows + " ";
        String result = first + second;
        result = result.replace(" ", "");
        return result;
    }

    /** Given cols, rows find index.
     * @param cols the column number to convert into an index.
      @param rows the row number to convert into an index.
      @return the index calculated from the rows and columns of this.
     * */
    public static int findIndex(int cols, int rows) {
        return cols * Board.SIZE + rows;
    }

    /** Given INDEX, find the number of rows
     * to this square.
     * @return the rows of this index.
     * @param index for me to extract row from.
     * */
    public int findRows(int index) {
        int boardSize = Board.SIZE;
        return index % boardSize;
    }

    /** Given INDEX, find the number of columns
     * to this square.
     * @return the columns of this index.
     * @param index for me to extract column from.
     * */
    public int findCols(int index) {
        int boardSize = Board.SIZE;
        return index / boardSize;
    }

    /** The cache of all created squares, by index. */
    public static final Square[] SQUARES =
        new Square[Board.SIZE * Board.SIZE];

    /** SQUARES viewed as a List. */
    public static final List<Square> SQUARE_LIST = Arrays.asList(SQUARES);


    /** Getter method for the character mappings of the board.
     *
     * @return _characterMappings.
     */
    public static HashMap<Integer, String> getCharacters() {
        return _characterMappings;
    }

    /** Getter method for the integer mappings of the board.
     *
     * @return _integerMappings.
     */
    public static HashMap<String, Integer> getIntegerMappings() {
        return _integerMappings;
    }

    /** Mappings of cols to letter index (e.g. 0 -> a) */
    private static HashMap<Integer, String>
            _characterMappings = new HashMap<>();

    /** Mappings of letters to col index (e.g. 0 -> a) */
    private static HashMap<String, Integer> _integerMappings = new HashMap<>();

    static {
        for (int i = Board.SIZE * Board.SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }

    /** Getter method for index.
     * @return _index
     * */
    public int getIndex() {
        return _index;
    }

    /** Getter method for index.
     * @return _row
     * */
    public int getRow() {
        return _row;
    }

    /** Getter method for index.
     * @return _col
     * */
    public int getCol() {
        return _col;
    }

    /** Getter method for index.
     * @return _str
     * */
    public String getStr() {
        return _str;
    }

    /** My index position. */
    private final int _index;

    /** My row and column (redundant, since these are determined by _index). */
    private final int _row, _col;

    /** My String denotation. */
    private final String _str;
}
