package com.loeng.tddjava;

public class Direction {

    public static final Direction NORTH = new Direction('N');
    public static final Direction WEST = new Direction('W');



    private char direction ='\0';

    public Direction(char n) {
        this.direction = n;
    }

    public static Direction getFromShortName(char n) {
        return Direction.NORTH;
    }


}
