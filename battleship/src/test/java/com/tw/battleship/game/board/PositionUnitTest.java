package com.tw.battleship.game.board;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PositionUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForNullArgument(){
        Position p = new Position(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForEmptyArgument(){
        Position p = new Position(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForArgumentFormatForLength(){
        Position p = new Position("D99");
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForArgumentFormatForDigit(){
        Position p = new Position("DD");
    }

    @Test
    public void itShouldReturnInRange(){
        Position p = new Position("D3");
        assertTrue(p.isXCoordinateInBounds(1, 9));
        assertTrue(p.isYCoordinateInBounds('A', 'D'));
    }

    @Test
    public void itShouldReturnIOutOfRange(){
        Position p = new Position("E0");
        assertFalse(p.isXCoordinateInBounds(1, 9));
        assertFalse(p.isYCoordinateInBounds('A', 'D'));
    }

    @Test
    public void itShouldReturnRelativePosition(){
        Position p = new Position("E0");
        Position expected = new Position("G1");
        Position relative = p.relative(1, 2);
        assertThat(relative, is(expected));
    }

    @Test
    public void  distanceOf2OnAnyAxisShouldReturnTrue() {
        Position p = new Position("D4");
        Position close = p.relative(0, 2);
        assertTrue(p.isCloseTo(close));
    }

    @Test
    public void  distanceOf3ShouldReturnFalse() {
        Position p = new Position("E0");
        Position close = p.relative(3, 2);
        assertFalse(p.isCloseTo(close));
    }

    @Test
    public void  diagonalDistanceOf1ShouldReturnFalse() {
        Position p = new Position("D4");
        Position close = p.relative(1, 1);
        assertFalse(p.isCloseTo(close));
    }


}