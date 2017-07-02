package com.tw.battleship.game.board;

import com.tw.battleship.game.piece.Target;

import java.util.ArrayList;
import java.util.List;

public class BattleArea {

    private enum Bound {
        LOWER(1, 'A'), UPPER(9, 'Z');

        private int width;
        private char height;

        Bound(int width, char height) {
            this.width = width;
            this.height = height;
        }

        public char getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

    }

    private final int width;
    private final char height;
    private final List<Target> targets = new ArrayList<>();

    public BattleArea(int width, char height) {
        if(Bound.LOWER.getWidth() > width  || width > Bound.UPPER.getWidth()) {
            throw new IllegalArgumentException("Width cannot  be outside range 1-9");
        }
        if(Bound.LOWER.getHeight() > height || height > Bound.UPPER.getHeight()) {
            throw new IllegalArgumentException("Height cannot  be outside range A-Z");
        }
        this.width = width;
        this.height = height;
    }

    public boolean hasWithinBounds (String position) {
        Position p = new Position(position);
        return hasWithinBounds(p);
    }

    public boolean hasWithinBounds (Position position) {
        return position.isXCoordinateInBounds(Bound.LOWER.getWidth(), width) &&
                position.isYCoordinateInBounds(Bound.LOWER.getHeight(), height);
    }

    public Target seek(String position){
        Position p = new Position(position);
        return targets.stream()
                .filter((target) -> target.occupiesPosition(p))
                .findFirst()
                .orElse(Target.EMPTY);
    }

    public void deploy(Target target, String position){
        Position pos = new Position(position);
        if(isOccupied(pos)){
            throw new IllegalStateException(
                    String.format("Cannot deploy target since position %s is already occupied", position));
        }
        target.deploy(this, pos);
        targets.add(target);
    }

    public boolean areAllTargetsDestroyed() {
        return targets.stream().allMatch(Target :: isDestroyed);
    }

    private boolean isOccupied(Position position){
        return targets.stream()
                .anyMatch((target) -> target.occupiesPosition(position));
    }
}
