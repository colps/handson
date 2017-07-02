package com.tw.battleship.game.piece;


import com.tw.battleship.game.board.BattleArea;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class MissileUnitTest {

    @Mock
    private BattleArea battleArea;
    @Mock
    private Target target;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldReturnMissedAttackResult () {
        // given
        given(battleArea.seek(anyString())).willReturn(Target.EMPTY);
        // when
        Missile missile = new Missile("D3");
        AttackResult result = missile.seekAndAttack(battleArea);
        // then
        assertThat(result, is(AttackResult.MISS));
    }

    @Test
    public void itShouldReturnHitAttackResult () {
        // given
        given(target.attack(anyInt(), anyString())).willReturn(AttackResult.HIT);
        given(battleArea.seek(anyString())).willReturn(target);
        // when
        Missile missile = new Missile("D3");
        AttackResult result = missile.seekAndAttack(battleArea);
        // then
        assertThat(result, is(AttackResult.HIT));
    }


}