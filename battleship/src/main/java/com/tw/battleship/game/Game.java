package com.tw.battleship.game;


public class Game {

    public enum State{
        OVER("Game Over"){
            @Override
            public boolean isOver(){
                return true;
            }
        },
        DRAW("Peace Declared"){
            @Override
            public boolean isDrawn(){
                return true;
            }
        },
        IN_PROGRESS("In Progress"){
            @Override
            public boolean inProgress() {
                return true;
            }
        };

        State(String message) {
            this.message = message;
        }

        private final String message;

        public String getMessage() {
            return message;
        }

        public boolean inProgress(){
            return false;
        }

        public boolean isDrawn(){
            return false;
        }

        public boolean isOver(){
            return false;
        }
    }

    private final Player playerA;
    private final Player playerB;
    private State state = State.IN_PROGRESS;

    public Game(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public void start(){
        do{
            state = playerA.play(playerB);
            if(!state.inProgress()){
                break;
            }
            state = playerB.play(playerA);
        } while (state.inProgress());
        System.out.println(state.getMessage());

    }

    public boolean isOver(){
        return state == State.OVER;
    }

    public boolean isInProgress(){
        return state.inProgress();
    }

    public boolean isDrawn(){
        return state == State.DRAW;
    }

}
