package com.tw.battleship.game;


import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.piece.AttackResult;
import com.tw.battleship.game.piece.Target;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class PlayerUnitTest {

    @Mock
    private BattleArea area;
    @Mock
    private Target target;

    private String [] validTargets = {"A1", "B1", "D1"};

    @Before
    public void init () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initialStateShouldBeActiveWithValidTargets () {
        Player player = new Player("P1", area, validTargets);
        assertTrue(player.isActive());
    }

    @Test
    public void initialStateShouldBeActiveWithEmptyTargets () {
        String [] empty = {};
        Player player = new Player("P1", area, empty);
        assertTrue(player.isOutOfMoves());
    }

    @Test
    public void gameStateWillBeInProgressUntilOpponentSurrenders() {
        // given
        Player player = new Player("P1", area, validTargets);
        Player opponent = new Player("P2", area, validTargets);
        given(target.attack(anyInt(), anyString())).willReturn(AttackResult.HIT);
        given(area.seek(anyString())).willReturn(target);
        given(area.areAllTargetsDestroyed()).willReturn(false);

        //when
        Game.State state = player.play(opponent);

        // then
        assertTrue(state.inProgress());
        assertTrue(opponent.isActive());
        assertTrue(player.isOutOfMoves());
    }

    @Test
    public void gameStateWillBeDrawnWhenBothPlayersAreOutOfMoves() {
        // given
        Player player = new Player("P1", area, validTargets);
        Player opponent = new Player("P2", area, validTargets);
        given(target.attack(anyInt(), anyString())).willReturn(AttackResult.HIT);
        given(area.seek(anyString())).willReturn(target);
        given(area.areAllTargetsDestroyed()).willReturn(false);
        //when
        Game.State state = player.play(opponent);
        assertTrue(state.inProgress());
        state = opponent.play(player);

        // then
        assertTrue(state.isDrawn());
        assertTrue(player.isOutOfMoves());
        assertTrue(opponent.isOutOfMoves());
    }

    @Test
    public void gameStateWillBeOverWhenOnePlayerWins() {
        // given
        Player player = new Player("P1", area, validTargets);
        Player opponent = new Player("P2", area, validTargets);
        given(target.attack(anyInt(), anyString())).willReturn(AttackResult.HIT);
        given(area.seek(anyString())).willReturn(target);
        given(area.areAllTargetsDestroyed()).willReturn(true);
        //when
        Game.State state = player.play(opponent);
        // then
        assertTrue(state.isOver());
        assertTrue(player.hasWon());
    }
}