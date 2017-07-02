package com.tw.battleship.game;


import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.piece.AttackResult;
import com.tw.battleship.game.piece.Missile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class TurnUnitTest {

    @Mock
    private Player player;
    @Mock
    private Player opponent;
    @Mock
    private Missile missile;

    @Before
    public void init () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldReturnTrueForAHit(){
        // given
        given(missile.seekAndAttack(any())).willReturn(AttackResult.HIT);
        given(player.getMissile()).willReturn(missile);
        // when
        boolean result = Turn.attack().execute(player, opponent);
        // then
        assertTrue(result);
    }

    @Test
    public void itShouldReturnFalseForAMiss(){
        // given
        given(missile.seekAndAttack(any())).willReturn(AttackResult.MISS);
        given(player.getMissile()).willReturn(missile);
        // when
        boolean result = Turn.attack().execute(player, opponent);
        // then
        assertFalse(result);
    }

    @Test
    public void itShouldReturnFalseForAPass(){
        // given
        given(missile.seekAndAttack(any(BattleArea.class))).willReturn(AttackResult.HIT);
        given(player.getMissile()).willReturn(missile);
        // when
        boolean result = Turn.pass().execute(player, opponent);
        // then
        assertFalse(result);
        verify(missile, never()).seekAndAttack(any(BattleArea.class));
        verify(player, never()).getMissile();
    }

    @Test
    public void itShouldReturnFalseForFinishGame(){
        // given
        given(missile.seekAndAttack(any(BattleArea.class))).willReturn(AttackResult.HIT);
        given(player.getMissile()).willReturn(missile);
        // when
        boolean result = Turn.finishGame().execute(player, opponent);
        // then
        assertFalse(result);
        verify(missile, never()).seekAndAttack(any(BattleArea.class));
        verify(player, never()).getMissile();
    }
}