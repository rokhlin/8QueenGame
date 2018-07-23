package com.selfapps.a8queengame;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Game {
    private static final int BLOCKED = 1;
    private static final int QUEEN_STEP = 10;
    private static final int EMPTY = 0;

    private HashMap<Integer, HashSet<Integer>> queens = new HashMap<>();

    public  void deleteQueen(int position, int[][] field) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);
        int queenId = field[row][column];
        HashSet<Integer> qBlock = queens.get(queenId);

        for (Integer pos : queens.get(queenId)) {
            markField(pos,field,EMPTY);
        }

        queens.remove(queenId);
    }

    public  boolean checkWin(){
        return queens.size() == 8;
    }

    public  boolean addQueen(int position, int[][] field) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);
        int queenId = QUEEN_STEP * (queens.size()+1);

        markField(position,field,queenId);
        markRow(column,queenId,field,row);
        markColumn(row,queenId,field,column);
        markDownLeft(queenId,field,column,row);
        markDownRight(queenId,field,column,row);
        markUpLeft(queenId,field,column,row);
        markUpRight(queenId,field,column,row);
        return checkWin();
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
