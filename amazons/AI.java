package amazons;

import java.util.Iterator;


import static amazons.Piece.*;

/** A Player that automatically generates moves.
 *  @author John Markham
 */
class AI extends Player {

    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI with no piece or controller (intended to produce
     *  a template). */
    AI() {
        this(null, null);
    }

    /** A new AI playing PIECE under control of CONTROLLER. */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        if (!board().getMoves().contains(move)) {
            _controller.reportMove(move);
        }
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }
        if (sense == 1) {
            int bestSoFar = Integer.MIN_VALUE;
            Iterator<Move> legalMoves = board.legalMoves(WHITE);
            Move moveHolder = null;
            while (legalMoves.hasNext()) {
                moveHolder = legalMoves.next();
                board.makeMove(moveHolder);
                int response = findMove(board,
                        depth - 1, false,
                        -1, alpha, beta);
                board.undo();
                if (response >= bestSoFar) {
                    if (saveMove) {
                        _lastFoundMove = moveHolder;
                    }
                    bestSoFar = response;
                    alpha = Math.max(alpha, bestSoFar);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestSoFar;
        } else {
            int bestSoFar = Integer.MAX_VALUE;
            Iterator<Move> legalMoves = board.legalMoves(BLACK);
            Move moveHolder = null;
            while (legalMoves.hasNext()) {
                moveHolder = legalMoves.next();
                board.makeMove(moveHolder);
                int response = findMove(board,
                        depth - 1, false,
                        1, alpha, beta);
                board.undo();
                if (response <= bestSoFar) {
                    if (saveMove) {
                        _lastFoundMove = moveHolder;
                    }
                    bestSoFar = Math.min(bestSoFar, response);
                    beta = Math.min(beta, bestSoFar);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestSoFar;
        }
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        int score = staticScore(board);
        Piece turn = board().turn();
        if (N < 10) {
            return 1;
        } else if (N <= FOURTY && score < 0 && turn == WHITE) {
            return 2;
        } else if (N <= FOURTY && score > 0 && turn == WHITE) {
            return 2;
        } else if (N <= FOURTY && score < 0 && turn == BLACK) {
            return 3;
        } else if (N <= FOURTY && score > 0 && turn == BLACK) {
            return 3;
        } else if (N <= EIGHTY && score > 0 && turn == BLACK) {
            return 2;
        } else if (N <= EIGHTY && score < 0 && turn == BLACK) {
            return 2;
        } else if (N <= EIGHTY && score < 0 && turn == WHITE) {
            return 3;
        } else if (N <= EIGHTY && score > 0 && turn == WHITE) {
            return 2;
        } else {
            return 5;
        }
    }

    /** The number 40. */
    private static final int FOURTY = 40;
    /** The number 80. */
    private static final int EIGHTY = 80;


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == BLACK) {
            return -WINNING_VALUE;
        } else if (winner == WHITE) {
            return WINNING_VALUE;
        }
        Iterator<Move> oppLegal = board.legalMoves(BLACK);
        Iterator<Move> myLegal = board.legalMoves(WHITE);
        int oppMoves = 0; int myMoves = 0;
        while (oppLegal.hasNext()) {
            oppLegal.next();
            oppMoves += 1;
        }
        while (myLegal.hasNext()) {
            myLegal.next();
            myMoves += 1;
        }
        return myMoves - oppMoves;
    }

}
