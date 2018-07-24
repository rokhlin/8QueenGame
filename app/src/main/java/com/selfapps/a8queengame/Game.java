package com.selfapps.a8queengame;

import java.util.HashMap;
import java.util.HashSet;

public class Game implements GameContract.EightQueensGame {
    private static final int BLOCKED = 1;
    private static final int QUEEN_STEP = 10;
    private static final int EMPTY = 0;

    private HashMap<Integer, HashSet<Integer>> queens = new HashMap<>();
    private int[][] boarderField;

    public Game() {
        initEmptyField();
    }
    

    @Override
    public int[][] getData() {
        return boarderField;
    }

    @Override
    public boolean addFigure(FigureType figureType, int position) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);
        int queenId = QUEEN_STEP * (queens.size()+1);

        markField(position,boarderField,queenId);
        markRow(column,queenId,boarderField,row);
        markColumn(row,queenId,boarderField,column);
        markDownLeft(queenId,boarderField,column,row);
        markDownRight(queenId,boarderField,column,row);
        markUpLeft(queenId,boarderField,column,row);
        markUpRight(queenId,boarderField,column,row);
        return checkWin();
    }

    @Override
    public void removeFigure(int position) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);
        int queenId = boarderField[row][column];
        HashSet<Integer> qBlock = queens.get(queenId);

        for (Integer pos : queens.get(queenId)) {
            markField(pos,boarderField,EMPTY);
        }

        queens.remove(queenId);
    }

    @Override
    public int[][] initEmptyField() {
        boarderField = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boarderField[i][j] = 0;
            }
        }
        return boarderField;
    }

    @Override
    public boolean checkGameOver() {
        int countFree = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(boarderField[i][j] == 0) countFree++;
            }
        }
        return countFree == 0;
    }

    public  boolean checkWin(){
        return queens.size() == 8;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void stopGame() {

    }

    @Override
    public void restartGame() {
        initEmptyField();
    }

    public void markField(int position, int[][] field, int toValue) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);
        field[row][column] = toValue;
        if(toValue >9) {
            HashSet<Integer> values = new HashSet<>();
            values.add(position);
            queens.put(toValue,values);
        }
    }

    public  void markRow(int queenPosition,int QueenId, int[][] field, int row) {
        HashSet<Integer> qBlock = queens.get(QueenId);

        if(qBlock == null) qBlock = new HashSet<>();

        for (int column = 0; column < 8 ; column++) {

            if(column == queenPosition) field[row][column] = QueenId;
            else  field[row][column] = BLOCKED;
            qBlock.add(getPosition(row,column));
        }
        queens.put(QueenId, qBlock);
    }

    public void markColumn(int queenPosition,int QueenId, int[][] field, int column) {
        HashSet<Integer> qBlock = queens.get(QueenId);

        if(qBlock == null) qBlock = new HashSet<>();

        for (int row = 0; row < 8 ; row++) {

            if(row == queenPosition) field[row][column] = QueenId;
            else  field[row][column] = BLOCKED;
            qBlock.add(getPosition(row,column));
        }
        queens.put(QueenId, qBlock);
    }


    public  void markDownRight(int QueenId, int[][] field, int column, int row) {
        HashSet<Integer> qBlock = queens.get(QueenId);

        if(qBlock == null) qBlock = new HashSet<>();

        for(int r = row + 1,c = column + 1 ; r < 8 && c < 8 ; r++,c++){
            field[r][c] = BLOCKED;
            qBlock.add(getPosition(r,c));
        }
        queens.put(QueenId, qBlock);
    }

    public  void markUpRight(int QueenId, int[][] field, int column, int row) {
        HashSet<Integer> qBlock = queens.get(QueenId);

        if(qBlock == null) qBlock = new HashSet<>();

        for(int r = row - 1,c = column + 1 ; r >= 0 && c < 8 ; r--,c++) {
            field[r][c] = BLOCKED;
            qBlock.add(getPosition(r,c));
        }
        queens.put(QueenId, qBlock);
    }

    public  void markUpLeft(int QueenId, int[][] field, int column, int row) {
        HashSet<Integer> qBlock = queens.get(QueenId);

        if(qBlock == null) qBlock = new HashSet<>();

        for(int r = row - 1,c = column - 1 ; r >= 0 && c >= 0 ; r--,c--)  {
            field[r][c] = BLOCKED;
            qBlock.add(getPosition(r,c));
        }
        queens.put(QueenId, qBlock);
    }

    public void markDownLeft(int QueenId, int[][] field, int column, int row) {
        HashSet<Integer> qBlock = queens.get(QueenId);

        if (qBlock == null) qBlock = new HashSet<>();

        for (int r = row + 1, c = column - 1; r < 8 && c >= 0; r++, c--) {
            {
                field[r][c] = BLOCKED;
                qBlock.add(getPosition(r, c));
            }
            queens.put(QueenId, qBlock);
        }

    }

    private  Integer getPosition(int row, int column) {
        return row*8 + column;
    }

    public int getQueenCount() {
        return queens.size();
    }
}
