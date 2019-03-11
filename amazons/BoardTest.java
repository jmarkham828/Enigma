package amazons;

import org.junit.Test;
import org.junit.Before;

import static amazons.Piece.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
/** The suite of all JUnit tests for the amazons*** package.
 *  @author John Markham
 */
public class BoardTest {


    @Test
    public void getBoardTest() {
        Board test = new Board();
        test.makeMove(Move.mv(Square
                .sq(60), Square.sq(66), Square.sq(16)));
        test.makeMove(Move.mv(Square
                .sq(96), Square.sq(78), Square.sq(8)));
        test.makeMove(Move.mv(Square
                .sq(3), Square.sq(47), Square.sq(38)));
        test.makeMove(Move.mv(Square
                .sq(96), Square.sq(78), Square.sq(8)));
    }



    @Test
    public void winnerTest() {
        Board winner = new Board(true);
        winner.put(SPEAR, new Square(9));
        winner.put(SPEAR, new Square(8));
        winner.put(SPEAR, new Square(4));
        winner.put(SPEAR, new Square(13));
        winner.put(SPEAR, new Square(14));
        winner.put(SPEAR, new Square(15));
        winner.put(SPEAR, new Square(16));
        winner.put(SPEAR, new Square(17));
        winner.put(SPEAR, new Square(18));
        winner.put(SPEAR, new Square(19));
        winner.put(SPEAR, new Square(25));
        winner.put(SPEAR, new Square(26));
        winner.put(SPEAR, new Square(27));
        winner.put(SPEAR, new Square(28));
        winner.put(SPEAR, new Square(29));
        winner.put(SPEAR, new Square(34));
        winner.put(SPEAR, new Square(39));
        winner.put(SPEAR, new Square(46));
        winner.put(SPEAR, new Square(47));
        winner.put(SPEAR, new Square(57));
        winner.put(SPEAR, new Square(44));
        winner.put(SPEAR, new Square(67));
        winner.put(SPEAR, new Square(66));
        winner.put(SPEAR, new Square(65));
        winner.put(SPEAR, new Square(72));
        winner.put(SPEAR, new Square(73));
        winner.put(SPEAR, new Square(74));
        winner.put(SPEAR, new Square(75));
        winner.put(SPEAR, new Square(77));
        winner.put(SPEAR, new Square(78));
        winner.put(SPEAR, new Square(89));
        winner.put(SPEAR, new Square(88));
        winner.put(SPEAR, new Square(87));
        winner.put(SPEAR, new Square(86));
        winner.put(SPEAR, new Square(85));
        winner.put(SPEAR, new Square(84));
        winner.put(SPEAR, new Square(83));
        winner.put(SPEAR, new Square(82));
        winner.put(SPEAR, new Square(92));
        winner.put(SPEAR, new Square(95));
        winner.put(SPEAR, new Square(96));
        winner.put(SPEAR, new Square(97));
        winner.put(SPEAR, new Square(99));
        winner.put(WHITE, new Square(3));
        winner.put(WHITE, new Square(56));
        winner.put(WHITE, new Square(94));
        winner.put(WHITE, new Square(79));
        winner.put(BLACK, new Square(6));
        winner.put(BLACK, new Square(7));
        winner.put(BLACK, new Square(93));
        winner.put(BLACK, new Square(98));
        Iterator<Move> mover2 = winner.legalMoves(BLACK);

    }

    @Test
    public void testReachableFrom() {
        Board b = new Board(true);
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b
                .reachableFrom(Square.sq(5, 4), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQUARES
                    .contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQUARES
                .size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQUARES
                .size(), squares.size());
    }


    private void buildBoard(Board b, Piece[][] target) {
        int white = 0;
        int black = 0;
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - row - 1][col];
                if (piece == WHITE) {
                    white += 1;
                }
                if (piece == BLACK) {
                    black += 1;
                }
                b.put(piece, Square.sq(col, row));
            }
        }
    }


    static final Piece E = EMPTY;

    static final Piece W = WHITE;

    static final Piece B = BLACK;

    static final Piece S = SPEAR;

    static final Piece[][] REACHABLEFROMTESTBOARD =
        {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, W, W },
            { E, E, E, E, E, E, E, S, E, S },
            { E, E, E, S, S, S, S, E, E, S },
            { E, E, E, S, E, E, E, E, B, E },
            { E, E, E, S, E, W, E, E, B, E },
            { E, E, E, S, S, S, B, W, B, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
        };

    @Test
    public void aiTest() {
        Board b = new Board();
        buildBoard(b, AITEST);
        int numMoves = 0;
        HashMap<Integer, Move> moves = new HashMap<>();
        Iterator<Move> legalMoves = b
                .legalMoves(BLACK);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.put(numMoves, m);
        }
    }


    static final Piece[][] AITEST =
        {
            {E, E, E, E, S, E, E, E, S, E},
            {E, E, B, E, E, S, S, W, B, E},
            {E, E, E, S, E, S, S, S, S, E},
            {E, S, S, S, S, S, E, S, S, E},
            {S, E, B, E, E, S, S, E, E, S},
            {E, S, S, E, E, E, S, E, S, E},
            {E, W, S, E, S, S, W, E, S, E},
            {S, E, S, E, S, E, S, S, E, E},
            {E, S, E, S, S, E, B, E, S, W},
            {E, E, E, S, E, E, S, E, E, E},
        };


    static final Set<Square> REACHABLEFROMTESTSQUARES =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 5),
                    Square.sq(4, 5),
                    Square.sq(4, 4),
                    Square.sq(6, 4),
                    Square.sq(7, 4),
                    Square.sq(6, 5),
                    Square.sq(7, 6),
                    Square.sq(8, 7)));

    private Board tracker;
    private Square a4;


    @Before
    public void setUp() {
        tracker = new Board();
        Piece w1 = WHITE;
        Piece w2 = WHITE;
        Piece w3 = WHITE;
        Piece w4 = WHITE;
        Piece b1 = BLACK;
        Piece b2 = BLACK;
        Piece b3 = BLACK;
        Piece b4 = BLACK;
        a4 = new Square(3);
        Square a7 = new Square(6);
        Square d1 = new Square(30);
        Square d10 = new Square(39);
        Square g1 = new Square(60);
        Square g10 = new Square(69);
        Square j4 = new Square(93);
        Square j7 = new Square(96);
        tracker.put(w1, a4);
        tracker.put(w2, d1);
        tracker.put(w3, g1);
        tracker.put(w4, j4);
        tracker.put(b1, a7);
        tracker.put(b2, d10);
        tracker.put(b3, g10);
        tracker.put(b4, j7);
    }



    @Test
    public void toStringTest() {
        Board printer = new Board();
        Square w1 = new Square(3);
        Square w2 = new Square(36);
        Square w3 = new Square(93);
        Square w4 = new Square(60);
        Square b1 = new Square(69);
        Square b2 = new Square(96);
        Square b3 = new Square(28);
        Square b4 = new Square(6);
        Square spear1 = new Square(66);
        Square spear2 = new Square(73);
        printer.put(WHITE, w1);
        printer.put(WHITE, w2);
        printer.put(WHITE, w3);
        printer.put(WHITE, w4);
        printer.put(BLACK, b1);
        printer.put(BLACK, b2);
        printer.put(BLACK, b3);
        printer.put(BLACK, b4);
        printer.put(SPEAR, spear1);
        printer.put(SPEAR, spear2);


    }

    @Test
    public void testToString() {
        Board b = new Board();
        assertEquals(INIT_BOARD_STATE, b.toString());
        makeSmile(b);
        assertEquals(SMILE, b.toString());
    }

    private void makeSmile(Board b) {
        b.put(EMPTY, Square.sq(0, 3));
        b.put(EMPTY, Square.sq(0, 6));
        b.put(EMPTY, Square.sq(9, 3));
        b.put(EMPTY, Square.sq(9, 6));
        b.put(EMPTY, Square.sq(3, 0));
        b.put(EMPTY, Square.sq(3, 9));
        b.put(EMPTY, Square.sq(6, 0));
        b.put(EMPTY, Square.sq(6, 9));
        for (int col = 1; col < 4; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(2, 7));
        for (int col = 6; col < 9; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(7, 7));
        for (int lip = 3; lip < 7; lip += 1) {
            b.put(WHITE, Square.sq(lip, 2));
        }
        b.put(WHITE, Square.sq(2, 3));
        b.put(WHITE, Square.sq(7, 3));
    }

    static final String INIT_BOARD_STATE =
            "   - - - B - - B - - -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   B - - - - - - - - B\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   W - - - - - - - - W\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - - W - - W - - -\n";

    static final String SMILE =
            "   - - - - - - - - - -\n"
                    +
                    "   - S S S - - S S S -\n"
                    +
                    "   - S - S - - S - S -\n"
                    +
                    "   - S S S - - S S S -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - W - - - - W - -\n"
                    +
                    "   - - - W W W W - - -\n"
                    +
                    "   - - - - - - - - - -\n"
                    +
                    "   - - - - - - - - - -\n";

    static final Square TEST = Square.sq(0, 0);


    @Test
    public void generateSquareTest() {

        Square test1 = new Square(0);
        Square test2 = new Square(99);
        Square test3 = new Square(90);
        Square test4 = new Square(9);
        Square test5 = new Square(40);
        Square test6 = new Square(59);
        Square test7 = new Square(44);

        ArrayList<Square> map1 = tracker
                .generateSquare(test1, a4, true);
        ArrayList<Square> map2 = tracker
                .generateSquare(test2, a4, true);
        ArrayList<Square> map3 = tracker
                .generateSquare(test3, a4, true);
        ArrayList<Square> map4 = tracker
                .generateSquare(test4, a4, true);
        ArrayList<Square> map5 = tracker
                .generateSquare(test5, a4, true);
        ArrayList<Square> map6 = tracker
                .generateSquare(test6, a4, true);
        ArrayList<Square> map7 = tracker
                .generateSquare(test7, a4, true);
    }

    @Test
    public void reachableFromIteratorTest() {

        Square test = new Square(0);
        ArrayList<Square> map1 = tracker.generateSquare(test, a4, true);
        Square test9 = new Square(3);


        Square test1 = new Square(0);
        Square test2 = new Square(99);
        Square test3 = new Square(90);
        Square test4 = new Square(9);
        Square test5 = new Square(40);
        Square test6 = new Square(59);
        Square test7 = new Square(44);


        Iterator<Square> mover1 = tracker
                .reachableFrom(test1, test1);
        while (mover1.hasNext()) {
            mover1.next();
        }
        Iterator<Square> mover2 = tracker
                .reachableFrom(test9, test9);
        while (mover2.hasNext()) {
            mover2.next();
        }
        Iterator<Square> mover3 = tracker
                .reachableFrom(test2, test2);
        while (mover3.hasNext()) {
            mover3.next();
        }
        Iterator<Square> mover4 = tracker
                .reachableFrom(test3, test3);
        while (mover4.hasNext()) {
            mover4.next();
        }
        Iterator<Square> mover5 = tracker
                .reachableFrom(test4, test4);
        while (mover5.hasNext()) {
            mover5.next();
        }
        Iterator<Square> mover6 = tracker.reachableFrom(test5, test5);
    }
    @Test
    public void trackerTest() {
        assertTrue(tracker.getPieces().get(3) != EMPTY);
        assertTrue(tracker.getPieces().get(6) != EMPTY);
        assertTrue(tracker.getPieces().get(30) != EMPTY);
        assertTrue(tracker.getPieces().get(39) != EMPTY);
        assertTrue(tracker.getPieces().get(60) != EMPTY);
        assertTrue(tracker.getPieces().get(69) != EMPTY);
        assertTrue(tracker.getPieces().get(93) != EMPTY);
        assertTrue(tracker.getPieces().get(96) != EMPTY);

        assertTrue(tracker.getPieces().get(3) == WHITE);
        assertTrue(tracker.getPieces().get(6) == BLACK);
        assertTrue(tracker.getPieces().get(30) == WHITE);
        assertTrue(tracker.getPieces().get(39) == BLACK);
        assertTrue(tracker.getPieces().get(60) == WHITE);
        assertTrue(tracker.getPieces().get(69) == BLACK);
        assertTrue(tracker.getPieces().get(93) == WHITE);
        assertTrue(tracker.getPieces().get(96) == BLACK);
    }

    @Test
    public void isUnblockedMoveTest() {

        Move true1 = Move.mv(Square.sq(39), Square.sq(59), Square.sq(49));
        Move true2 = Move.mv(Square.sq(30), Square.sq(12), Square.sq(30));
        Move true3 = Move.mv(Square.sq(39), Square.sq(59), Square.sq(99));

        Move true4 = Move.mv(Square.sq(93), Square.sq(98), Square.sq(96));
        Move true5 = Move.mv(Square.sq(60), Square.sq(69), Square.sq(69));

        Move false1 = Move.mv(Square.sq(30), Square.sq(43), Square.sq(30));
        Move false2 = Move.mv(Square.sq(30), Square.sq(13), Square.sq(30));

        assertTrue(tracker.isUnblockedMove(true1
                .from(), true1.to(), true1.spear()));
        assertTrue(tracker.isUnblockedMove(true2
                .from(), true2.to(), true2.spear()));
        assertTrue(tracker.isUnblockedMove(true3
                .from(), true3.to(), true3.spear()));
        assertTrue(tracker.isUnblockedMove(true4
                .from(), true4.to(), true4.spear()));
        assertTrue(tracker.isUnblockedMove(true5
                .from(), true5.to(), true5.spear()));
        assertFalse(tracker.isUnblockedMove(false1
                .from(), false1.to(), false1.spear()));
        assertFalse(tracker.isUnblockedMove(false2
                .from(), false2.to(), false2.spear()));

    }

    static final Set<Move> LEGALMOVESLIST2 =
            new HashSet<>(Arrays.asList(
                    Move.mv(Square.sq(0, 0), Square
                            .sq(0, 1), Square.sq(0, 0)),
                    Move.mv(Square.sq(0, 0), Square
                            .sq(0, 1), Square.sq(1, 1)),
                    Move.mv(Square.sq(0, 0), Square
                            .sq(1, 1), Square.sq(0, 0)),
                    Move.mv(Square.sq(0, 0), Square
                            .sq(1, 1), Square.sq(0, 1)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 5), Square.sq(5, 4)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 5), Square.sq(5, 6)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 5), Square.sq(5, 7)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 6), Square.sq(5, 4)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 6), Square.sq(5, 5)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 6), Square.sq(5, 7)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 7), Square.sq(5, 4)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 7), Square.sq(5, 5)),
                    Move.mv(Square.sq(5, 4), Square
                            .sq(5, 7), Square.sq(5, 6)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(8, 9), Square.sq(9, 9)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(8, 9), Square.sq(8, 8)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(8, 9), Square.sq(9, 8)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(8, 8), Square.sq(9, 9)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(8, 8), Square.sq(8, 9)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(8, 8), Square.sq(9, 8)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(9, 8), Square.sq(9, 9)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(9, 8), Square.sq(8, 8)),
                    Move.mv(Square.sq(9, 9), Square
                            .sq(9, 8), Square.sq(8, 9))));


    static final Piece[][] LEGALMOVESTLIST2 = {
        {E, E, E, E, E, E, E, S, E, W},
        {E, E, E, E, S, S, S, S, E, E},
        {E, E, E, E, S, E, S, S, S, S},
        {E, E, E, S, S, E, S, E, E, S},
        {E, E, E, S, S, E, S, E, B, E},
        {E, E, E, S, S, W, S, S, B, E},
        {E, E, E, S, S, S, B, S, B, E},
        {S, S, S, E, E, E, S, S, S, E},
        {E, E, S, E, E, E, E, E, E, E},
        {W, S, S, E, E, E, E, E, E, E},
    };



    @Test
    public void isLegalTest() {
        assertTrue(tracker.isLegal(Square.sq(30), Square
                .sq(38), Square.sq(30)));
    }

    @Test
    public void generateHorizVerTest() {
        Square dirtest11 = new Square(60);
        Square dirtest12 = new Square(69);

        Square dirtest21 = new Square(9);
        Square dirtest22 = new Square(0);

        Square dirtest31 = new Square(23);
        Square dirtest32 = new Square(93);

        Square dirtest41 = new Square(79);
        Square dirtest42 = new Square(9);

        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).size() == 10);
        assertTrue(tracker
                .generateMoves(dirtest21, dirtest22).size() == 10);
        assertTrue(tracker
                .generateMoves(dirtest31, dirtest32).size() == 8);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).size() == 8);


        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(0).index() == 60);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(1).index() == 61);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(2).index() == 62);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(3).index() == 63);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(4).index() == 64);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(5).index() == 65);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(6).index() == 66);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(7).index() == 67);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(8).index() == 68);
        assertTrue(tracker
                .generateMoves(dirtest11, dirtest12).get(9).index() == 69);

        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(0).index() == 79);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(1).index() == 69);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(2).index() == 59);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(3).index() == 49);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(4).index() == 39);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(5).index() == 29);
        assertTrue(tracker
                .generateMoves(dirtest41, dirtest42).get(6).index() == 19);
    }


    @Test
    public void generateDiagTest() {
        Square dirtest51 = new Square(37);
        Square dirtest52 = new Square(48);

        Square dirtest61 = new Square(54);
        Square dirtest62 = new Square(21);

        Square dirtest71 = new Square(22);
        Square dirtest72 = new Square(31);

        Square dirtest81 = new Square(81);
        Square dirtest82 = new Square(45);

        Square dirtest91 = new Square(90);
        Square dirtest92 = new Square(79);

        assertTrue(tracker
                .generateMoves(dirtest51, dirtest52).size() == 2);
        assertTrue(tracker
                .generateMoves(dirtest61, dirtest62).size() == 4);
        assertTrue(tracker
                .generateMoves(dirtest71, dirtest72).size() == 2);
        assertTrue(tracker
                .generateMoves(dirtest81, dirtest82).size() == 5);
        assertTrue(tracker
                .generateMoves(dirtest91, dirtest92).size() == 0);


        assertTrue(tracker
                .generateMoves(dirtest51, dirtest52).get(0).index() == 37);
        assertTrue(tracker
                .generateMoves(dirtest51, dirtest52).get(1).index() == 48);

        assertTrue(tracker
                .generateMoves(dirtest61, dirtest62).get(0).index() == 54);
        assertTrue(tracker
                .generateMoves(dirtest61, dirtest62).get(1).index() == 43);
        assertTrue(tracker
                .generateMoves(dirtest61, dirtest62).get(2).index() == 32);
        assertTrue(tracker
                .generateMoves(dirtest61, dirtest62).get(3).index() == 21);

        assertTrue(tracker
                .generateMoves(dirtest71, dirtest72).get(0).index() == 22);
        assertTrue(tracker
                .generateMoves(dirtest71, dirtest72).get(1).index() == 31);

        assertTrue(tracker
                .generateMoves(dirtest81, dirtest82).get(0).index() == 81);
        assertTrue(tracker
                .generateMoves(dirtest81, dirtest82).get(1).index() == 72);
        assertTrue(tracker
                .generateMoves(dirtest81, dirtest82).get(2).index() == 63);
        assertTrue(tracker
                .generateMoves(dirtest81, dirtest82).get(3).index() == 54);
        assertTrue(tracker
                .generateMoves(dirtest81, dirtest82).get(4).index() == 45);
    }

    @Test
    public void undoMoveTest() {
        Square dirtest81 = new Square(81);
        Square dirtest82 = new Square(12);

        ArrayList<Square> paths = tracker
                .generateMoves(dirtest81, dirtest82);
        for (int path = 0; path < paths.size() - 1; path++) {
            Square currSquare = paths.get(path);
            Square nextSquare = paths.get(path + 1);
            Square spear = new Square(path);
            tracker.makeMove(currSquare, nextSquare, spear);
        }
    }

}


