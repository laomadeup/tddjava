package com.loeng.tddjava.tdd;

import com.loeng.tddjava.Direction;
import com.loeng.tddjava.connect4.tradition.Connect4;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Connect4TDD {

    private static final String RED = "R";

    private static final String GREEN = "G";

    private static final int ROWS = 6;

    private static final int COLUMN = 7;

    private static final String DELIMITER ="|";

    private static final String EMPTY =" ";

    private String [][] board = new String[ROWS][COLUMN];

    private String currentPlayer = RED;

    private PrintStream outputChannel ;

    private static final int DISC_TO_WIN = 4;

    private String winner ="";




    public Connect4TDD(){
        for (String[] row : board){
            Arrays.fill(row,EMPTY);
        }
    }

    public Connect4TDD(PrintStream out) {

        outputChannel = out;
        for (String[] row : board){
            Arrays.fill(row,EMPTY);
        }
    }


    public int getNumberOfDiscs() {
        return IntStream.range(0,COLUMN).map(this::getNumberOfDiscsInColumn).sum();
    }

    public int putDiscInColumn(int column) {
       checkColumn(column);
        int row = (int) getNumberOfDiscsInColumn(column);
        checkPositionToInsert(row,column);
        board[row][column] = currentPlayer;
        checkWinner(row,column);
        printBoard();
        switchPlayer();
        return row;

    }


    private void printBoard(){
        for (int row = ROWS-1;row >=0;row --){
            StringJoiner stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
            Stream.of(board[row]).forEachOrdered(stringJoiner::add);
            outputChannel.println(stringJoiner.toString());
        }
    }


    private void switchPlayer() {
        if(RED.equals(currentPlayer)){
            currentPlayer = GREEN;
        }else {
            currentPlayer = RED;
        }
    }

    private void checkPositionToInsert(int row, int column) {
        if(row < 0 || row >= ROWS){
            throw new RuntimeException("No more room in column "+column);
        }
    }

    private void checkColumn(int column){
        if(column <0 || column >= COLUMN){
            throw new RuntimeException("Invalid column "+ column);
        }
    }

    private int getNumberOfDiscsInColumn(int column){

        return (int)IntStream.range(0, ROWS).filter(row -> !EMPTY.equals(board[row][column])).count();

    }


    public String getCurrentPlayer() {
        outputChannel.printf("Player %s turn%n",currentPlayer);
        return currentPlayer;
    }

    public boolean isFinished() {
        return getNumberOfDiscs() == ROWS * COLUMN;
    }

    public String getWinner() {
        return this.winner;
    }

    private void checkWinner(int row,int column){

        if(winner.isEmpty()){
            String colour = board[row][column];
            Pattern winPattern = Pattern.compile(".*" + colour + "{" + DISC_TO_WIN + "}.*");
            String vertical = IntStream.range(0, ROWS).mapToObj(r -> board[r][column]).reduce(String::concat).get();
            if(winPattern.matcher(vertical).matches()){
                winner = colour;
            }


            String horizontal = Stream.of(board[row]).reduce(String::concat).get();
            if(winPattern.matcher(horizontal).matches()){
                winner = colour;
            }

            int startOffset = Math.min(column, row);
            int myColumn = column - startOffset,myRow = row - startOffset;
            StringJoiner stringJoiner = new StringJoiner("");
            do{
                stringJoiner.add(board[myRow++][myColumn++]);
            }while (myColumn < COLUMN && myRow < ROWS);

            if(winPattern.matcher(stringJoiner.toString()).matches()){
                winner = currentPlayer;
            }

            startOffset = Math.min(column, ROWS - 1 - row);
            myColumn = column - startOffset;
            myRow = row + startOffset;
            stringJoiner = new StringJoiner("");
            do {
                stringJoiner.add(board[myRow--][myColumn++]);
            }while (myColumn < COLUMN && myRow >= 0);

            if(winPattern.matcher(stringJoiner.toString()).matches()){
                winner = currentPlayer;
            }


        }
    }

}
