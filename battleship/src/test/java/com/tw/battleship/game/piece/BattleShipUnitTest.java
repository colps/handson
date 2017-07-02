package com.tw.battleship.game.piece;

import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.board.Position;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class BattleShipUnitTest {

    @Mock
    BattleArea battleArea;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldAttackSectionOnce() throws Exception {
        // given
        Position position = new Position("D3");
        BattleShip battleShip = new BattleShip(2,2, BattleShip.Type.P);
        given(battleArea.hasWithinBounds(any(Position.class))).willReturn(true);
        // when
        battleShip.deploy(battleArea, position);
        AttackResult result1 = battleShip.attack(1, position.toString());
        AttackResult result2 = battleShip.attack(1, position.toString());
        // then
        assertTrue(result1.isAHit());
        assertThat(result2, is(AttackResult.MISS));
    }

    @Test
    public void itShouldDeployBattleShipSections() throws Exception {
        // given
        Position position = new Position("D3");
        BattleShip battleShip = new BattleShip(2,2, BattleShip.Type.P);
        given(battleArea.hasWithinBounds(any(Position.class))).willReturn(true);
        // when
        battleShip.deploy(battleArea, position);
        // then
        assertThat(battleShip.occupiesPosition(new Position("D3")), is(true));
        assertThat(battleShip.occupiesPosition(new Position("D4")), is(true));
        assertThat(battleShip.occupiesPosition(new Position("E3")), is(true));
        assertThat(battleShip.occupiesPosition(new Position("E4")), is(true));

    }

    @Test
    public void pTypeShipIsDestroyedAfterOneHitPerSection() throws Exception {
        // given
        Position position = new Position("D3");
        BattleShip battleShip = new BattleShip(1,2, BattleShip.Type.P);
        given(battleArea.hasWithinBounds(any(Position.class))).willReturn(true);
        // when
        battleShip.deploy(battleArea, position);
        AttackResult result = battleShip.attack(1, "D3");
        assertTrue(result.isAHit());
        result = battleShip.attack(1, "E3");
        assertTrue(result.isAHit());
        // then
        assertTrue(battleShip.isDestroyed());
    }

    @Test
    public void qTypeShipIsDestroyedAfterTwoHitsPerSection() throws Exception {
        // given
        Position position = new Position("D3");
        BattleShip battleShip = new BattleShip(2,1, BattleShip.Type.Q);
        given(battleArea.hasWithinBounds(any(Position.class))).willReturn(true);
        // when
        battleShip.deploy(battleArea, position);
        AttackResult result1 = battleShip.attack(1, "D3");
        assertTrue(result1.isAHit());
        AttackResult result2 = battleShip.attack(1, "D3");
        assertTrue(result2.isAHit());

        result1 = battleShip.attack(1, "D4");
        assertTrue(result1.isAHit());
        result2 = battleShip.attack(1, "D4");
        assertTrue(result2.isAHit());
        // then
        assertTrue(battleShip.isDestroyed());
    }

    @Test
    public void  shouldCheckProximityForAllSections() {
        String position = "A1";
        BattleShip battleShip = new BattleShip(5,1, BattleShip.Type.Q);
        BattleArea area = new BattleArea(9, 'P');
        area.deploy(battleShip, position);
        assertThat(battleShip.isCloseTo(new Position("C2")), is(true));
    }

    @Test
    public void  shouldCheckDiagonalProximityForAllSections() {
        String position = "D4";
        BattleShip battleShip = new BattleShip(1,1, BattleShip.Type.Q);
        BattleArea area = new BattleArea(9, 'P');
        area.deploy(battleShip, position);
        assertThat(battleShip.isCloseTo(new Position("E3")), is(false));
    }

}