package com.tw.battleship.game;


import com.tw.battleship.game.piece.AttackResult;
import com.tw.battleship.game.piece.Missile;

@FunctionalInterface
public interface Turn {

    public boolean execute(Player player, Player opponent);

    public static Turn attack(){
        return (player, opponent) -> {
            Missile missile = player.getMissile();
            AttackResult result = missile.seekAndAttack(opponent.getBattleArea());
            System.out.println(String.format("%s fires a %s which got %s", player.getName(), missile, result));
            return result.isAHit();
        };
    }

    public static Turn pass(){
        return (player, opponent) -> {
            System.out.println(String.format("%s has no more missiles left to launch", player.getName()));
            return false;
        };
    }

    public static Turn finishGame(){
        return (player, opponent) -> {
            System.out.println(String.format("%s won the battle", player.getName()));
            return false;
        };
    }
}
