package com.tw.battleship.game.piece;


public enum AttackResult {
    HIT {
        @Override
        public boolean isAHit() {
            return true;
        }
    },
    MISS {
        @Override
        public boolean isAHit() {
            return false;
        }
    },
    OUT_OF_MISSILES{
        @Override
        public boolean isAHit() {
            return false;
        }
    };

    public abstract boolean isAHit();

}
