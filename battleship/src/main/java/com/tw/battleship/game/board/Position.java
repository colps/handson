package com.tw.battleship.game.board;

public class Position {

    private final char yCoordinate;
    private final int xCoordinate;
    private final String position;
    private static final int TOLERANCE = 2;

    public Position(String position) {
        if(position == null || position.trim().isEmpty()){
            throw new IllegalArgumentException("Position is null or empty");
        }
        if(position.trim().length() != 2){
            throw new IllegalArgumentException("Illegal format for position");
        }
        if(Character.isLetter(position.charAt(1))){
            throw new IllegalArgumentException("Illegal format for position");
        }
        this.position = position;
        this.yCoordinate = position.charAt(0);
        this.xCoordinate = Character.getNumericValue(position.charAt(1));
    }


    public boolean isXCoordinateInBounds (int lower, int upper) {
        return lower <= xCoordinate && xCoordinate <= upper;
    }

    public boolean isYCoordinateInBounds (char lower, char upper) {
        return lower <= yCoordinate && yCoordinate <= upper;
    }

    public Position relative(int xDelta, int yDelta){
        String x = String.valueOf(xCoordinate + xDelta);
        String y = String.valueOf((char)(yCoordinate + yDelta));
        return new Position(y+x);
    }

    public boolean isCloseTo (Position position) {
        return (xCoordinate == position.xCoordinate && Math.abs(yCoordinate - position.yCoordinate) <=2) ||
                (yCoordinate == position.yCoordinate && Math.abs(xCoordinate - position.xCoordinate) <=2);

    }



    private boolean xCordinateIsClose(Position position){
        return Math.abs(position.xCoordinate - this.xCoordinate) <= TOLERANCE &&
                this.yCoordinate == position.yCoordinate;
    }

    private boolean yCordinateIsClose(Position position){
        return Math.abs(position.yCoordinate - this.yCoordinate) <= TOLERANCE &&
                this.xCoordinate == position.xCoordinate;
    }

    @Override
    public String toString() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position1 = (Position) o;
        assert position != null && position1.position != null: "Field position cannot be null";
        return position.equals(position1.position);
    }

    @Override
    public int hashCode() {
        assert position != null : "Field position cannot be null";
        return position.hashCode();
    }


}
