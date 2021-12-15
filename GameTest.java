package org.cis120.matching;

import org.junit.jupiter.api.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testBoardSize() {
        Matching mg = new Matching();
        assertEquals(4, mg.getBoard().length);
        assertEquals(4, mg.getBoard()[0].length);
    }

    @Test
    public void testBoardStateSize() {
        Matching mg = new Matching();
        assertEquals(4, mg.getBoardState().length);
        assertEquals(4, mg.getBoardState()[0].length);
    }

    @Test
    public void testBoardWithinRange() {
        Matching mg = new Matching();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertFalse((mg.getCell(i, j) < 1) || (mg.getCell(i, j) > 8));
            }
        }
    }

    @Test
    public void testBoardEveryValueAppearedTwice() {
        Matching mg = new Matching();
        LinkedList<Integer> allValues = new LinkedList<Integer>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                allValues.add(mg.getCell(i, j));
            }
        }
        for (int i = 1; i < 9; i++) {
            assertEquals(2, Collections.frequency(allValues, i));
        }
    }

    @Test
    public void testBoardStateFalse() {
        Matching mg = new Matching();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertFalse(mg.getCellState(i, j));
            }
        }
    }

    @Test
    public void testBoardStateOneRevealed() {
        Matching mg = new Matching();
        mg.playTurn(0, 0);

        LinkedList<Boolean> allValues = new LinkedList<Boolean>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                allValues.add(mg.getCellState(i, j));
            }
        }
        assertEquals(1, Collections.frequency(allValues, true));
    }

    @Test
    public void testBoardStateTwoRevealed() {
        Matching mg = new Matching();
        mg.playTurn(0, 0);
        mg.playTurn(1, 0);

        LinkedList<Boolean> allValues = new LinkedList<Boolean>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                allValues.add(mg.getCellState(i, j));
            }
        }
        if (mg.isUnmatchingMove()) {
            // System.out.println("unmatching");
            assertEquals(0, Collections.frequency(allValues, true));
        } else {
            // System.out.println("matching");
            assertEquals(2, Collections.frequency(allValues, true));
        }

    }

    @Test
    public void testOddTurnWhenPlayTurnRepeated() {
        Matching mg = new Matching();
        assertTrue(mg.isOddTurn());
        mg.playTurn(0, 0);
        assertFalse(mg.isOddTurn());
        mg.playTurn(0, 0);
        assertFalse(mg.isOddTurn());
        // the next turn is still an even turn
        mg.playTurn(1, 0);
        assertTrue(mg.isOddTurn());
    }

    @Test
    public void testPlayTurnOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Matching mg = new Matching();
            mg.playTurn(0, 4);
        });
    }

    @Test
    public void testOddTurn() {
        Matching mg = new Matching();
        assertTrue(mg.isOddTurn());
        mg.playTurn(0, 0);
        assertFalse(mg.isOddTurn());
        mg.playTurn(1, 0);
        assertTrue(mg.isOddTurn());
        mg.playTurn(2, 0);
        assertFalse(mg.isOddTurn());
    }

    @Test
    public void testCurrentCoordinates() {
        Matching mg = new Matching();
        mg.playTurn(0, 2);
        mg.playTurn(1, 3);
        assertEquals(0, mg.getOddr());
        assertEquals(2, mg.getOddc());
        assertEquals(1, mg.getEvenr());
        assertEquals(3, mg.getEvenc());
    }

    @Test
    public void testCurrentCoordinatesRepeated() {
        Matching mg = new Matching();
        mg.playTurn(0, 0);
        assertEquals(0, mg.getOddr());
        assertEquals(0, mg.getOddc());
        mg.playTurn(0, 0);
        assertEquals(0, mg.getOddr());
        assertEquals(0, mg.getOddc());
        // the next turn is still an even turn
        mg.playTurn(1, 0);
        assertEquals(1, mg.getEvenr());
        assertEquals(0, mg.getEvenc());
    }

    @Test
    public void testUnmatchingMove() {
        Matching mg = new Matching();
        // false by default
        assertFalse(mg.isUnmatchingMove());

        Random rand = new Random();
        while (!mg.isUnmatchingMove()) {
            mg.playTurn(rand.nextInt(4), rand.nextInt(4));
        }

        // eventually becomes true when two cards picked have different values
        assertTrue(mg.isUnmatchingMove());
    }

    @Test
    public void testHasWon() {
        Matching mg = new Matching();
        assertFalse(mg.hasWon());
    }

    @Test
    public void testUndoOddTurn() {
        Matching mg = new Matching();
        assertTrue(mg.isOddTurn());
        mg.playTurn(0, 0);
        assertFalse(mg.isOddTurn());
        mg.playTurn(1, 0);
        assertTrue(mg.isOddTurn());
        mg.playTurn(2, 0);
        assertFalse(mg.isOddTurn());
        mg.undo();
        assertTrue(mg.isOddTurn());
        mg.undo();
        assertFalse(mg.isOddTurn());
    }

    @Test
    public void testUndoCurrentCoordinates() {
        Matching mg = new Matching();
        mg.playTurn(0, 2);
        mg.playTurn(1, 3);
        assertEquals(0, mg.getOddr());
        assertEquals(2, mg.getOddc());
        assertEquals(1, mg.getEvenr());
        assertEquals(3, mg.getEvenc());
        mg.undo();
        assertEquals(0, mg.getOddr());
        assertEquals(2, mg.getOddc());
        assertEquals(0, mg.getEvenr());
        assertEquals(0, mg.getEvenc());
    }
}