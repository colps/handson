package com.tw.battleship.game;

import com.tw.battleship.game.board.BattleArea;
import com.tw.battleship.game.piece.Missile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Player {

    private enum State {
        ACTIVE {
            @Override
            Turn getTurn() {
                return Turn.attack();
            }

            @Override
            Game.State getGameState(Player player, Player opponent) {
                return Game.State.IN_PROGRESS;
            }

            @Override
            void transition(Player player, Player opponent) {
                if(opponent.hasSurrendered()){
                    player.switchState(WON);
                } else if (player.isOutOfMissiles()){
                    player.switchState(OUT_OF_MOVES);
                }
            }
        }, OUT_OF_MOVES {
            @Override
            Turn getTurn() {
                return Turn.pass();
            }

            @Override
            Game.State getGameState(Player player, Player opponent) {
                if(player.isOutOfMissiles() && opponent.isOutOfMissiles()){
                    return Game.State.DRAW;
                }
                return Game.State.IN_PROGRESS;
            }

        }, WON {
            @Override
            Turn getTurn() {
                return Turn.finishGame();
            }

            @Override
            Game.State getGameState(Player player, Player opponent) {
                return Game.State.OVER;
            }
        };

        abstract Turn getTurn();
        abstract Game.State getGameState(Player player, Player opponent);

        void transition(Player player, Player opponent){
            // do nothing
        }
    }

    private final String name;
    private final BattleArea battleArea;
    private final Queue<String> targets = new LinkedList<>();
    private State state;

    public Player(String name, BattleArea area, String[] targets) {
        this.name = name;
        this.battleArea = area;
        this.targets.addAll(Arrays.asList(targets));
        this.state = this.targets.isEmpty() ? State.OUT_OF_MOVES : State.ACTIVE;
    }

    public Game.State play(Player opponent){
        boolean repeat = false;
        do {
            Turn turn = state.getTurn();
            repeat = turn.execute(this, opponent);
            state.transition(this, opponent);
        } while (repeat);
        return state.getGameState(this, opponent);
    }

    public boolean hasSurrendered(){
        return battleArea.areAllTargetsDestroyed();
    }

    public BattleArea getBattleArea() {
        return battleArea;
    }

    public Missile getMissile(){
        String target = targets.poll();
        return new Missile(target);
    }

    public String getName() {
        return name;
    }

    private boolean isOutOfMissiles(){
        return targets.isEmpty();
    }

    private void switchState(State state){
        this.state = state;
    }

    public boolean isActive() {
        return state == State.ACTIVE;
    }

    public boolean isOutOfMoves() {
        return state == State.OUT_OF_MOVES;
    }

    public boolean hasWon() {
        return state == State.WON;
    }

}
