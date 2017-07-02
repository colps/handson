package com.tw.battleship.game.board;

import com.tw.battleship.game.piece.Target;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

public class BattleAreaUnitTest {

    @Mock
    private Target target;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForWidthRange () {
        BattleArea area = new BattleArea(10, 'P');
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionForHeightRange () {
        BattleArea area = new BattleArea(4, 'x');
    }

    @Test
    public void itShouldCreateBattleAreaWithGivenBounds () {
        BattleArea battleArea = new BattleArea(4, 'E');
        assertNotNull(battleArea);
        String position = "E4";
        assertTrue(String.format("Expected position %s should be within %s and %s. Found otherwise ", position, 4, 'E'),
                battleArea.hasWithinBounds(position));
    }


    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionForDoubleDeployment() {
        // given
        Position p = new Position("D2");
        given(target.occupiesPosition(p)).willReturn(true);
        // when
        BattleArea area = new BattleArea(4, 'D');
        // deploy twice
        area.deploy(target, "D2");
        area.deploy(target, "D2");
        // then exception
    }

    @Test
    public void itShouldSeekDeployedTarget() {
        // given
        Position p = new Position("D2");
        given(target.occupiesPosition(p)).willReturn(true);
        // when
        BattleArea area = new BattleArea(4, 'D');
        area.deploy(target, "D2");
        // then
        assertThat(area.seek("D2"), is(target));
    }

}