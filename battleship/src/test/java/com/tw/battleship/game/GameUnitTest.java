package com.tw.battleship.game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class GameUnitTest {

    @Mock
    Player playerA;

    @Mock
    Player playerB;
    
    @Before
    public void init () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldBreakOnFirstGameOver(){
        // given
        given(playerA.play(eq(playerB)))
                .willReturn(Game.State.IN_PROGRESS)
                .willReturn(Game.State.IN_PROGRESS)
                .willReturn(Game.State.IN_PROGRESS);
        given(playerB.play(eq(playerA)))
                .willReturn(Game.State.IN_PROGRESS)
                .willReturn(Game.State.OVER);
        // when
        Game game = new Game(playerA, playerB);
        game.start();
        // then
        verify(playerA, times(2)).play(eq(playerB));
        verify(playerB, times(2)).play(eq(playerA));
        assertTrue(game.isOver());
    }

    @Test
    public void itShouldBreakOnFirstDraw(){
        // given
        given(playerA.play(eq(playerB)))
                .willReturn(Game.State.IN_PROGRESS)
                .willReturn(Game.State.IN_PROGRESS)
                .willReturn(Game.State.DRAW);
        given(playerB.play(eq(playerA)))
                .willReturn(Game.State.IN_PROGRESS);
        // when
        Game game = new Game(playerA, playerB);
        game.start();
        // then
        verify(playerA, times(3)).play(eq(playerB));
        verify(playerB, times(2)).play(eq(playerA));
        assertTrue(game.isDrawn());
    }


    public static void main(String[] args) {
        try{
            System.exit(1);
        }finally {
            System.out.println("bb");
        }
    }
}