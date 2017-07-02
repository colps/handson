package com.tw.battleship.game.piece;


import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.board.Position;

public interface Target {

    Target EMPTY = new Target() {
        @Override
        public AttackResult attack(int damage, String position) {
            return AttackResult.MISS;
        }

        @Override
        public void deploy(BattleArea area, Position position) {
            // nop
        }

        @Override
        public boolean isDestroyed() {
            return false;
        }

        @Override
        public boolean occupiesPosition(Position position) {
            return false;
        }

        @Override
        public boolean isCloseTo(Position position) {
            return false;
        }
    };

    AttackResult attack(int damage, String position);
    void deploy(BattleArea area, Position position);
    boolean isDestroyed();
    boolean occupiesPosition(Position position);
    boolean isCloseTo(Position position);

}
