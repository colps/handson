package com.tw.battleship.game.piece;


import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.board.Position;

import java.util.HashMap;
import java.util.Map;

public class BattleShip implements Target {

    public enum Type {
        P(1), Q(2);

        private int hitPonts;

        Type(int hitPonts) {
            this.hitPonts = hitPonts;
        }

        public int getHitPonts() {
            return hitPonts;
        }
    }

    private class Section {

        private int hitPoints;

        public Section(int hitPoints) {
            this.hitPoints = hitPoints;
        }

        public boolean isDestroyed() {
            return hitPoints == 0;
        }

        private AttackResult attack(int damage){
            if(isDestroyed()){
                return AttackResult.MISS;
            }
            reduceHitPoints(damage);
            return AttackResult.HIT;
        }

        private void reduceHitPoints(int damage){
            this.hitPoints = hitPoints > damage ? hitPoints - damage : 0;
        }

    }

    private final int width;
    private final int height;
    private final Type type;
    private final Map<Position, Section> sections = new HashMap<>();


    public BattleShip(int width, int height, Type type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }


    @Override
    public AttackResult attack(int damage, String position) {
        Section section = sections.get(new Position(position));
        return section.attack(damage);
    }

    @Override
    public void deploy(BattleArea area, Position position) {
        for(int xDelta = 0 ; xDelta < width; xDelta ++){
            for(int yDelta = 0; yDelta < height; yDelta ++){
                deploySection(area, position.relative(xDelta, yDelta));
            }
        }
    }

    @Override
    public boolean isCloseTo(Position position) {
        Position found = sections.keySet().stream().filter(sectionPosition ->
            sectionPosition.isCloseTo(position)
        ).findAny().orElse(null);
        return found != null;
    }


    private void deploySection(BattleArea area, Position position) {
        Section section = new Section(type.getHitPonts());
        if(!area.hasWithinBounds(position)){
            throw new IllegalArgumentException(String.format("Invalid position %s for deploying target", position));
        }
        sections.put(position, section);
    }

    @Override
    public boolean isDestroyed() {
        return sections.values().stream().allMatch(Section::isDestroyed);
    }

    @Override
    public boolean occupiesPosition(Position position) {
        return sections.containsKey(position);
    }


}
