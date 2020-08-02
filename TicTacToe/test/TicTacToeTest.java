import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.eugenpolytechnic.tictactoe.*;

class TicTacToeTest {
    TicTacToe testField = new TicTacToe(9);
    TicTacToe smallField = new TicTacToe();
    TicTacToe newTestField = new TicTacToe(9);

    @BeforeEach
    void init() {
        testField.clearField();
        smallField.clearField();
        newTestField.clearField();
    }

    @Test
    void setCrossAt() { //setZeroAt() работает аналогично
        assertTrue(testField.setCrossAt(1,2));
        assertTrue(testField.setZeroAt(2,2));
        assertEquals(TicTacToe.CellState.CROSS, testField.getFieldAt(1, 2));
        assertFalse(testField.setCrossAt(15,15));
        assertFalse(testField.setCrossAt(1,2));
        assertFalse(testField.setCrossAt(2,2));
    }

    @Test
    void clearFieldAt() {
        testField.setZeroAt(1,1);
        testField.setCrossAt(2,2);
        assertTrue(testField.clearFieldAt(1, 1));
        assertTrue(testField.clearFieldAt(2, 2));
        assertFalse(testField.clearFieldAt(0,0));
        assertEquals(TicTacToe.CellState.EMPTY, testField.getFieldAt(1, 1));
        assertEquals(TicTacToe.CellState.EMPTY, testField.getFieldAt(2, 2));
        assertEquals(TicTacToe.CellState.EMPTY, testField.getFieldAt(0, 0));
        assertFalse(testField.clearFieldAt(15,15));
    }

    @Test
    void longestSequenceHorizontal() {
        assertEquals(0, testField.longestCrossSequence());
        testField.setCrossAt(1,1);
        testField.setCrossAt(1,2);
        testField.setCrossAt(1,3);
        testField.setCrossAt(1,4);
        testField.setCrossAt(1,5);
        testField.setCrossAt(1,6);
        testField.setCrossAt(0,1);
        testField.setCrossAt(2,1);
        testField.setCrossAt(0,0);
        testField.setCrossAt(2,2);
        assertEquals(6, testField.longestCrossSequence());
        testField.clearFieldAt(1,2);
        assertEquals(4, testField.longestCrossSequence());
    }

    @Test
    void longestSequenceVertical() {
        testField.setCrossAt(0,1);
        testField.setCrossAt(1,1);
        testField.setCrossAt(2,1);
        testField.setCrossAt(3,1);
        testField.setCrossAt(4,1);
        testField.setCrossAt(5,1);
        testField.setCrossAt(1,2);
        testField.setCrossAt(1,0);
        assertEquals(6, testField.longestCrossSequence());
        testField.clearFieldAt(1,1);
        assertEquals(4, testField.longestCrossSequence());
    }

    @Test
    void longestSequenceLeftDiagonal() {
        testField.setCrossAt(0,0);
        testField.setCrossAt(1,1);
        testField.setCrossAt(2,2);
        testField.setCrossAt(3,3);
        testField.setCrossAt(4,4);
        testField.setCrossAt(5,5);
        assertEquals(6, testField.longestCrossSequence());
        testField.clearFieldAt(3,3);
        assertEquals(3, testField.longestCrossSequence());
    }

    @Test
    void longestSequenceRightDiagonal() {
        testField.setCrossAt(0,8);
        testField.setCrossAt(1,7);
        testField.setCrossAt(2,6);
        testField.setCrossAt(3,5);
        testField.setCrossAt(4,4);
        testField.setCrossAt(5,3);
        assertEquals(6, testField.longestCrossSequence());
        testField.clearFieldAt(1,7);
        assertEquals(4, testField.longestCrossSequence());
    } //longestZeroSequence() не тестируется, т.к. работает аналогично longestCrossSequence()

    @Test
    void equals() {
        assertNotEquals(testField, smallField);
        testField.setZeroAt(4,4);
        assertNotEquals(testField, newTestField);
        newTestField.setZeroAt(4,4);
        assertEquals(testField, newTestField);
    }

    @Test
    void testHashCode() {
        assertEquals(testField.hashCode(), newTestField.hashCode());
        newTestField.setCrossAt(3,3);
        assertNotEquals(testField.hashCode(), newTestField.hashCode());
        assertNotEquals(testField.hashCode(), smallField.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(".   .   .   " + System.lineSeparator() +
                              ".   .   .   " + System.lineSeparator() +
                              ".   .   .   " + System.lineSeparator(), smallField.toString());
        smallField.setCrossAt(2,2);
        assertEquals(".   .   .   " + System.lineSeparator() +
                              ".   .   .   " + System.lineSeparator() +
                              ".   .   X   " + System.lineSeparator(), smallField.toString());
        smallField.setZeroAt(0,1);
        assertEquals(".   O   .   " + System.lineSeparator() +
                              ".   .   .   " + System.lineSeparator() +
                              ".   .   X   " + System.lineSeparator(), smallField.toString());
    }
}

