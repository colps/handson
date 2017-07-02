package com.tw.battleship.game.piece;


import com.tw.battleship.game.board.BattleArea;

public class Missile {

    private static final int DAMAGE = 1;
    private final String position;

    public Missile(String position) {
        this.position = position;
    }

    public AttackResult seekAndAttack(BattleArea area){
        Target target = area.seek(position);
        assert target != null : "Target cannot be null";
        return target.attack(DAMAGE, position);
    }

    @Override
    public String toString() {
        return String.format("missile with target %s", position);
    }
}
