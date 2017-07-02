package com.tw.battleship.game;

import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.piece.BattleShip;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameIntTest {

    @Test
    public void testGame() {
        BattleArea areaA = new BattleArea(5, 'E');
        BattleShip shipA1 = new BattleShip(1,1, BattleShip.Type.Q);
        BattleShip shipA2 = new BattleShip(2,1, BattleShip.Type.P);
        //BattleShip shipA3 = new BattleShip(1,1, BattleShip.Type.Q);
        areaA.deploy(shipA1, "A1");
        areaA.deploy(shipA2, "D4");
        //areaA.deploy(shipA3, "A4");
        Player playerA = new Player("Player-1", areaA, "A1 B2 B2 B3".split("\\s+"));


        BattleArea areaB = new BattleArea(5, 'E');
        BattleShip shipB1 = new BattleShip(1,1, BattleShip.Type.Q);
        BattleShip shipB2 = new BattleShip(2,1, BattleShip.Type.P);
        areaB.deploy(shipB1, "B2");
        areaB.deploy(shipB2, "C3");
        Player playerB = new Player("Player-2", areaB, "A1 B2 B3 A1 D1 E1 D4 D4 D5 D5".split("\\s+"));

        Game game = new Game(playerA, playerB);
        game.start();
        assertTrue(game.isOver());
        assertTrue(playerB.hasWon());
        assertTrue(playerA.hasSurrendered());
        assertTrue(playerA.isOutOfMoves());

    }
}
