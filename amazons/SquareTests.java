package amazons;

import org.junit.Test;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the amazons*** package.
 *  @author John Markham
 */
public class SquareTests {

    @Test
     public void findRowsTest() {
        Square tester = new Square(44);
        int testRow1 = 0;
        int testRow2 = 99;
        int testRow3 = 44;
        int testRow4 = 51;
        int testRow5 = 21;
        int testRow6 = 88;
        int testRow7 = 63;
        int testRow8 = 16;
        int testRow9 = 17;
        int testRow10 = 18;
        assertEquals(tester.findRows(testRow1), 0);
        assertEquals(tester.findRows(testRow2), 9);
        assertEquals(tester.findRows(testRow3), 4);
        assertEquals(tester.findRows(testRow4), 1);
        assertEquals(tester.findRows(testRow5), 1);
        assertEquals(tester.findRows(testRow6), 8);
        assertEquals(tester.findRows(testRow7), 3);
        assertEquals(tester.findRows(testRow8), 6);
        assertEquals(tester.findRows(testRow9), 7);
        assertEquals(tester.findRows(testRow10), 8);
        tester.queenMove(1, 3);
    }

    @Test
     public void findColsTest() {
        Square tester = new Square(8);
        int testRow1 = 0;
        int testRow2 = 99;
        int testRow3 = 44;
        int testRow4 = 51;
        int testRow5 = 21;
        int testRow6 = 88;
        int testRow7 = 63;
        int testRow8 = 16;
        int testRow9 = 17;
        int testRow10 = 18;
        int testRow11 = 9;
        int testRow12 = 1;
        int testRow13 = 6;
        assertEquals(tester.findCols(testRow1), 0);
        assertEquals(tester.findCols(testRow2), 9);
        assertEquals(tester.findCols(testRow3), 4);
        assertEquals(tester.findCols(testRow4), 5);
        assertEquals(tester.findCols(testRow5), 2);
        assertEquals(tester.findCols(testRow6), 8);
        assertEquals(tester.findCols(testRow7), 6);
        assertEquals(tester.findCols(testRow8), 1);
        assertEquals(tester.findCols(testRow9), 1);
        assertEquals(tester.findCols(testRow10), 1);
        assertEquals(tester.findCols(testRow11), 0);
        assertEquals(tester.findCols(testRow12), 0);
        assertEquals(tester.findCols(testRow13), 0);
    }
    @Test
     public void findStringTest() {
        Square tester = new Square(33);
        assertEquals(tester.findString(8, 1), "b9");
        assertEquals(tester.findString(0, 1), "b1");
        assertEquals(tester.findString(0, 0), "a1");
        assertEquals(tester.findString(1, 1), "b2");
        assertEquals(tester.findString(9, 9), "j10");
        assertEquals(tester.findString(3, 2), "c4");
        assertEquals(tester.findString(4, 9), "j5");
        assertEquals(tester.findString(5, 7), "h6");
        assertEquals(tester.findString(6, 4), "e7");
        assertEquals(tester.findString(7, 3), "d8");
        assertEquals(tester.findString(1, 5), "f2");
    }
    @Test
    public void constructorIndexTest() {
        Square pos1 = new Square(99);
        assertTrue(pos1.SQUARES[99].col() == 9);
        assertTrue(pos1.SQUARES[99].row() == 9);
        assertTrue(pos1.SQUARE_LIST.get(99).index() == 99);
        assertTrue(pos1.SQUARE_LIST.get(99).getStr().equals("j10"));
        assertTrue(pos1.SQUARE_LIST.get(14).getStr().equals("b5"));
        assertTrue(pos1.SQUARE_LIST.get(47).getStr().equals("e8"));
        assertTrue(pos1.SQUARE_LIST.get(33).getStr().equals("d4"));
        assertTrue(pos1.SQUARE_LIST.get(12).getStr().equals("b3"));
        assertTrue(pos1.SQUARE_LIST.get(19).getStr().equals("b10"));
        assertTrue(pos1.SQUARE_LIST.get(11).getStr().equals("b2"));
        assertTrue(pos1.SQUARE_LIST.get(2).getStr().equals("a3"));
        assertTrue(pos1.SQUARE_LIST.get(9).getStr().equals("a10"));
        assertTrue(pos1.SQUARE_LIST.get(3).getStr().equals("a4"));
        assertTrue(pos1.SQUARE_LIST.get(49).getStr().equals("e10")); }
    @Test
    public void directionTest() {

        Square directionchecker = new Square(17);
        assertTrue(directionchecker.direction(new Square(18)) == 0);
        assertTrue(directionchecker.direction(new Square(28)) == 1);
        assertTrue(directionchecker.direction(new Square(27)) == 2);
        assertTrue(directionchecker.direction(new Square(26)) == 3);
        assertTrue(directionchecker.direction(new Square(16)) == 4);
        assertTrue(directionchecker.direction(new Square(6)) == 5);
        assertTrue(directionchecker.direction(new Square(7)) == 6);
        assertTrue(directionchecker.direction(new Square(8)) == 7);
    }
    @Test
    public void diagCheckerTest() {
        Square diagchecker = new Square(99);
        assertTrue(diagchecker.isQueenMove(new Square(88)));
        assertTrue(diagchecker.isQueenMove(new Square(89)));
        assertTrue(diagchecker.isQueenMove(new Square(77)));
        assertTrue(diagchecker.isQueenMove(new Square(22)));
        assertTrue(diagchecker.isQueenMove(new Square(11)));
        assertTrue(diagchecker.isQueenMove(new Square(0)));
        assertTrue(diagchecker.isQueenMove(new Square(97)));
        assertTrue(diagchecker.isQueenMove(new Square(90)));
    }
}
