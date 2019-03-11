package amazons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import static amazons.Piece.*;


/** The state of an Amazons Game.
 *  @author John Markham
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL.
     * @param model model
     * */
    Board(Board model) {
        copy(model);
    }

    /** Empty board for testing.
     * @param empty true */
    Board(Boolean empty) {
        if (empty) {
            _turn = WHITE;
            _winner = EMPTY;
            setUpPieceTracker();
        }
        return;
    }

    /** Copies the instance variables of model into me.
     * @param model model
     * */
    void copyInstance(Board model) {
        this._turn = model._turn;
        this._winner = model._winner;
        this._pieces = new HashMap<>(model._pieces);
        this._moves = new Stack<>();
        this._moves.addAll(model._moves);
        this._availSpears = new HashMap<>(model._availSpears);

    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        copyInstance(model);
        this.w1 = model.w1;
        this.w2 = model.w2;
        this.w3 = model.w3;
        this.w4 = model.w4;
        this.b1 = model.b1;
        this.b2 = model.b2;
        this.b3 = model.b3;
        this.b4 = model.b4;
        this.numbera4 = model.numbera4;
        this.numbera7 = model.numbera7;
        this.numberd1 = model.numberd1;
        this.numberd10 = model.numberd10;
        this.numberg1 = model.numberg1;
        this.numberg10 = model.numberg10;
        this.numberj4 = model.numberj4;
        this.numberj7 = model.numberj7;

    }

    /** init piece. */
    private Piece w1;
    /** init piece. */
    private Piece w2;
    /** init piece. */
    private Piece w3;
    /** init piece. */
    private Piece w4;
    /** init piece. */
    private Piece b1;
    /** init piece. */
    private Piece b2;
    /** init piece. */
    private Piece b3;
    /** init piece. */
    private Piece b4;
    /** init piece. */
    private Square numbera4;
    /** init index. */
    private static final int A4INDEX = 3;
    /** init index. */
    private static final int A7INDEX = 6;
    /** init index. */
    private static final int D1INDEX = 30;
    /** init index. */
    private static final int D10INDEX = 39;
    /** init index. */
    private static final int G1INDEX = 60;
    /** init index. */
    private static final int G10INDEX = 69;
    /** init index. */
    private static final int J4INDEX = 93;
    /** init index. */
    private static final int J7INDEX = 96;
    /** init piece. */
    private Square numbera7;
    /** init piece. */
    private Square numberd1;
    /** init piece. */
    private Square numberd10;
    /** init piece. */
    private Square numberg1;
    /** init piece. */
    private Square numberg10;
    /** init piece. */
    private Square numberj4;
    /** init piece. */
    private Square numberj7;


    /** Init method for tracking my pieces. */
    public void setUpPieceTracker() {
        _pieces = new HashMap<>();
        for (int i = 0;
             i < SIZE * SIZE; i++) {
            _pieces.put(i, EMPTY);
        }
        _availSpears = new HashMap<>();
    }

    /** Clears the board to the initial position. */
    void init() {
        int index1 = 0;
        _turn = WHITE;
        _winner = EMPTY;
        setUpPieceTracker();
        w1 = Piece.WHITE;
        w2 = Piece.WHITE;
        w3 = Piece.WHITE;
        w4 = Piece.WHITE;
        b1 = Piece.BLACK;
        b2 = Piece.BLACK;
        b3 = Piece.BLACK;
        b4 = Piece.BLACK;
        numbera4 = new Square(A4INDEX);
        numbera7 = new Square(A7INDEX);
        numberd1 = new Square(D1INDEX);
        numberd10 = new Square(D10INDEX);
        numberg1 = new Square(G1INDEX);
        numberg10 = new Square(G10INDEX);
        numberj4 = new Square(J4INDEX);
        numberj7 = new Square(J7INDEX);
        put(w1, numbera4);
        put(w2, numberd1);
        put(w3, numberg1);
        put(w4, numberj4);
        put(b1, numbera7);
        put(b2, numberd10);
        put(b3, numberg10);
        put(b4, numberj7);
    }



    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Setter method for _turn, using TURN. */
    void setTurn(Piece turn) {
        _turn = turn;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return _moves.size();
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        int blackCount = 0;
        int whiteCount = 0;
        LegalMoveIterator blackLegal = new LegalMoveIterator(BLACK);
        LegalMoveIterator whiteLegal = new LegalMoveIterator(WHITE);
        while (blackLegal.hasNext() && blackCount < 2) {
            blackLegal.next();
            blackCount += 1;
        }
        while (whiteLegal.hasNext() && whiteCount < 2) {
            whiteLegal.next();
            whiteCount += 1;
        }
        if (_turn == WHITE && whiteCount == 0) {
            return BLACK;
        } else if (_turn == BLACK && blackCount == 0) {
            return WHITE;
        }
        return null;
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return get(s.col(), s.row());
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        int lookup = (SIZE * col) + row;
        return _pieces.get(lookup);
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        put(p, s.col(), s.row());
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        put(p, SIZE * col + row);
    }

    /** Set square at index to P.
     * @param index index
     * @param p p
     * */
    final void put(Piece p, int index) {
        _pieces.put(index, p);
        _availSpears.put(index, SPEAR);
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        if (!from.isQueenMove(to)) {
            return false;
        }
        int direction = from.direction(to);
        for (int d = 1; d < 10; d++) {
            Square possibleMove = from.queenMove(direction, d);
            if (possibleMove == null) {
                return false;
            }
            if (_pieces.get(possibleMove.index()) != Piece.EMPTY
                && from.queenMove(direction, d) != asEmpty) {
                return false;
            }
            if (from.queenMove(direction, d) == to) {
                return true;
            }
        }
        return true;
    }

    /** Combination of all possible moves in every direction.
     * @param from from
     * @param asEmpty asEmpty
     * @param onlyLegal onlyLegal
     * @return List of all possible directions of moves.
     **/
    public ArrayList<Square> generateSquare(Square from,
                                            Square asEmpty,
                                            boolean onlyLegal) {
        ArrayList<Square> squareTracker = new ArrayList<>();
        for (int dir = 0; dir < DIRECTION.length; dir++) {
            if (from == null
                    || !Square.exists(from.row(), from.col())) {
                break;
            }
            int origRow = from.row();
            int origCol = from.col();
            int toAddCol = DIRECTION[dir][0];
            int toAddRow = DIRECTION[dir][1];
            int newIndex;
            if (onlyLegal) {
                while ((Square.exists(origCol + toAddCol,
                        origRow + toAddRow)
                        && Square.sq(origCol, origRow)
                        .isQueenMove(Square.sq(origCol + toAddCol,
                                origRow + toAddRow))
                        && _pieces.get((origCol + toAddCol) * 10
                        + origRow + toAddRow) == Piece.EMPTY)
                        || ((Square.exists((origCol + toAddCol) * 10
                        + origRow + toAddRow)
                        && (Square.sq((origCol + toAddCol) * 10
                        + origRow + toAddRow) == asEmpty)))) {
                    origCol = origCol + toAddCol;
                    origRow = origRow + toAddRow;
                    newIndex = 10 * origCol + origRow;
                    Square newSquare = Square.sq(newIndex);
                    squareTracker.add(newSquare);
                }
            }
        }
        return squareTracker;
    }

    /** Generate moves between from and to.
     * @param from from
       @param to to
        @return List of all possible surrounding moves.
     */
    ArrayList<Square> generateMoves(Square from, Square to) {
        ArrayList<Square> neededMoves = new ArrayList<>();
        int initialCol = from.col();
        int initialRow = from.row();
        int toCol = to.col();
        int toRow = to.row();
        int rowDiff = toRow - initialRow;
        int colDiff = toCol - initialCol;
        if (toCol == initialCol) {
            neededMoves = generateVerticalMoves(toCol,
                    initialCol, initialRow, rowDiff);
        } else if (toRow == initialRow) {
            neededMoves = generateHorizontalMoves(toCol,
                    initialCol, initialRow, colDiff);
        } else if (Math.abs(rowDiff) == Math.abs(colDiff)) {
            neededMoves = generateDiagMoves(toCol,
                    toRow, initialCol, initialRow,
                    colDiff, rowDiff);
        }
        return neededMoves;
    }

    /** Vertical move helper.
     * @param initialCol initialCol
     @param initialRow initialRow
     @param rowDiff rowDiff
     @param toCol toCol
     @return List of all possible moves in any vertical direction.
     */
    public ArrayList<Square> generateVerticalMoves(
            int toCol,
            int initialCol,
            int initialRow,
            int rowDiff) {
        ArrayList<Square> neededMoves = new ArrayList<>();
        if (rowDiff > 0) {
            int rowCounter = 0;
            while (rowCounter <= rowDiff) {
                neededMoves
                        .add(new Square(initialCol * SIZE
                                + initialRow + rowCounter));
                rowCounter += 1;
            }
        } else if (rowDiff == 0) {
            return neededMoves;
        } else {
            int rowCounter = 0;
            while (rowCounter >= rowDiff) {
                neededMoves
                        .add(new Square(initialCol * SIZE
                                + initialRow + rowCounter));
                rowCounter -= 1;
            }
        }
        return neededMoves;
    }

    /** Horizontal move helper.
     * @param initialCol initialCol
      @param initialRow initialRow
      @param colDiff colDiff
     @param toCol toCol
     @return List of all possible moves in any horizontal direction.
     */
    public ArrayList<Square> generateHorizontalMoves(
            int toCol,
            int initialCol,
            int initialRow,
            int colDiff) {
        ArrayList<Square> neededMoves = new ArrayList<>();
        if (colDiff > 0) {
            int colCounter = 0;
            while (colCounter <= colDiff) {
                neededMoves
                        .add(new Square((initialCol
                                + colCounter) * SIZE + initialRow));
                colCounter += 1;
            }
        } else if (colDiff == 0) {
            throw new NullPointerException("Please"
                    +
                    "enter a valid move");
        } else {
            int colCounter = 0;
            while (colCounter >= colDiff) {
                neededMoves.add(new Square((initialCol + colCounter)
                        * SIZE + initialRow));
                colCounter -= 1;
            }
        }
        return neededMoves;
    }

    /** Diagonal move helper.
     * @param toRow toRow
     * @param toCol toCol
     * @param initialRow initialRow
     * @param initialCol initialCol
     * @param rowDiff rowDiff
      @param colDiff colDiff
     @return A list of the possible moves in any diagonal direction.
     */
    public ArrayList<Square> generateDiagMoves(
                    int toCol, int toRow,
                    int initialCol, int initialRow,
                    int colDiff, int rowDiff) {
        ArrayList<Square> neededMoves = new ArrayList<>();
        if (toCol > initialCol) {
            if (toRow > initialRow) {
                int counter = 0;
                while (counter <= rowDiff) {
                    neededMoves.add(new
                            Square((initialCol * SIZE
                            + initialRow) + 11 * counter));
                    counter += 1;
                }
            } else if (toRow < initialRow) {
                int counter = 0;
                while (counter <= Math.abs(rowDiff)) {
                    neededMoves.add(new
                            Square((initialCol * SIZE
                            + initialRow) + 9 * counter));
                    counter += 1;
                }
            }
        }
        if (toCol < initialCol) {
            if (toRow > initialRow) {
                int counter = 0;
                while (counter <= rowDiff) {
                    neededMoves.add(new
                            Square((initialCol * SIZE
                            + initialRow) - 9 * counter));
                    counter += 1;
                }
            } else if (toRow < initialRow) {
                int counter = 0;
                while (counter >= rowDiff) {
                    neededMoves.add(new
                            Square((initialCol * SIZE
                            + initialRow) + 11 * counter));
                    counter -= 1;
                }
            }
        }
        return neededMoves;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        return _pieces.get(from.index()) == Piece.BLACK
                || _pieces.get(from.index()) == Piece.WHITE;
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        if (!isLegal(from)) {
            return false;
        } else {
            return isUnblockedMove(from,
                    to, null);
        }
    }


    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        if (isLegal(from, to)) {
            return isUnblockedMove(to, spear, from);
        }
        return false;
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to(), move.spear());
    }

    /** Helps make move by extracting index information.
     * @param from the square we are starting from.
     * @param to the square we are going to.
     * @param spear the location of the spear we are going to throw.
     * */
    void makeMove(Square from, Square to, Square spear) {
        makeMove(Move.mv(from, to, spear));
    }


    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        _moves.push(move);
        _pieces.put(move.to().index(), get(move.from()));
        _pieces.put(move.from().index(), EMPTY);
        _pieces.put(move.spear().index(), SPEAR);
        _turn = _turn.opponent();
    }

    /** Undo one move. Has no effect on the initial board. */
    void undo() {
        if (_moves.size() >= 1) {
            Move m = _moves.pop();
            _pieces.put(m.spear().index(), EMPTY);
            _pieces.put(m.from().index(), get(m.to()));
            _pieces.put(m.to().index(), EMPTY);
            _turn = _turn.opponent();
        }
    }

    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIRECTION = {
            { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
            { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };



    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {


        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }


        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            Square toReturn = _toReturn;
            toNext();
            return toReturn;
        }

        /** Advances to the next from-to(sp). */
        public void toNext() {
            while (hasNext()) {
                if (_from == null) {
                    System.out.println("null from");
                }
                Square possible = _from.queenMove(_dir, _steps + 1);
                if (possible == null) {
                    _dir += 1;
                    _steps = 0;
                } else if (!isUnblockedMove(_from, possible, _asEmpty)) {
                    _dir += 1;
                    _steps = 0;
                } else {
                    _toReturn = possible;
                    _steps += 1;
                    return;
                }
            }
        }

        /** Current from square that we are finding moves around. */
        private Square _from;

        /** Current direction we are looking for moves in. */
        private int _dir;

        /** Current amount of steps we are taking to the next queen move. */
        private int _steps;

        /** Squares that, if encountered, should be ignored if filled. */
        private Square _asEmpty;

        /** The next square we can reach. */
        private Square _toReturn;


    }


    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** Iterator that always returns false. */
    private static final Iterator<Square> NO_SQUARES =
            Collections.emptyIterator();

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _color = side;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _startingSquares.hasNext() || _pieceMoves
                    .hasNext() || _spearThrows.hasNext();
        }

        @Override
        public Move next() {
            Move tmp = Move.mv(_currSquare, _nextSquare, _spearThrows.next());
            toNext();
            return tmp;
        }

        /** Finds the next _color queen.
         * @return true if we can find the next queen, false if not.
         * */
        public boolean findColor() {
            while (_startingSquares.hasNext()) {
                Square possible = _startingSquares.next();
                if (_pieces.get(possible.index()) == _color) {
                    if (!reachableFrom(possible, null).hasNext()) {
                        continue;
                    }
                    _currSquare = possible;
                    return true;
                }
            }
            return false;
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            if (_currSquare == null) {
                findColor();
                if (_currSquare == null) {
                    _spearThrows = NO_SQUARES;
                    _pieceMoves = NO_SQUARES;
                    _startingSquares = NO_SQUARES;
                    return;
                }
                _pieceMoves = reachableFrom(_currSquare, null);
            }
            while (hasNext()) {
                if (!_spearThrows.hasNext()) {
                    if (!_pieceMoves.hasNext()) {
                        if (!_startingSquares.hasNext()) {
                            _spearThrows = NO_SQUARES;
                            _startingSquares = NO_SQUARES;
                            _pieceMoves = NO_SQUARES;
                            return;
                        } else {
                            if (!findColor()) {
                                _spearThrows = NO_SQUARES;
                                _startingSquares = NO_SQUARES;
                                _pieceMoves = NO_SQUARES;
                                return;
                            } else {
                                _pieceMoves = reachableFrom(_currSquare, null);
                                _nextSquare = _pieceMoves.next();
                                _spearThrows =
                                        reachableFrom(_nextSquare, _currSquare);
                                return;
                            }
                        }
                    } else {
                        _nextSquare = _pieceMoves.next();
                        _spearThrows = reachableFrom(_nextSquare, _currSquare);
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        /** Next square position. */
        private Square _nextSquare;
        /** Current queen under consideration. */
        private Square _currSquare;

        /** Black or white color that I'm getting moves for. */
        private Piece _color;

        /** Color squares. */
        private Iterator<Square> _startingSquares;

        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThrows;
    }


    @Override
    public String toString() {
        String result = "";
        for (int i = 9; i >= 0; i -= 1) {
            String rowString = "";
            for (int j = 0; j < 100; j += 10) {
                rowString +=  " " + _pieces.get(i + j);
            }
            result += " " + " " + rowString  + "\n";
        }
        return result;
    }


    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;

    /**Getter method for pieces.
     *
     * @return _pieces
     */
    public HashMap<Integer, Piece> getPieces() {
        return _pieces;
    }

    /** Used to track positions on the board and their pieces. */
    private HashMap<Integer, Piece> _pieces;

    /** The currently placed spears on the board. */
    private HashMap<Integer, Piece>  _availSpears;

    /** Tracks moves on teh board. */
    private Stack<Move> _moves = new Stack<Move>();

    /** Getter method for the stack of moves.
     * @return _moves.
     **/
    public Stack<Move> getMoves() {
        return _moves;
    }
}
