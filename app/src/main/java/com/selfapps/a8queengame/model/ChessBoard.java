package com.selfapps.a8queengame.model;

import android.util.SparseArray;

import com.selfapps.a8queengame.GameUtils;

import java.util.Iterator;


public class ChessBoard {
    private int numOfSquares;
    int edge;
    public static final char[] columnLetters = {'a','b', 'c','d', 'e', 'f','g','h','i', 'j','k'};
    private SparseArray<Cell> cells;




    public ChessBoard(int numOfSquares) {
        this.numOfSquares = numOfSquares;
        generateEmptyCells(numOfSquares);
    }

    private void generateEmptyCells(int edge) {
        this.edge = edge;
        int cellsAmount = edge * edge;
        cells = new SparseArray<>(cellsAmount);
        int rowCount = 0;
        int colCount = 0;
        //boolean isBlack = true;

        for (int i = 0; i < cellsAmount; i++) {
            cells.put(i, new Cell(i,
                                    rowCount,
                                    colCount++,
                                    GameUtils.isBlack(i,false)? Color.BLACK : Color.WHITE));
           // isBlack = !isBlack;
            if(colCount == edge){
                rowCount++;
                colCount = 0;
            }
        }
    }

    public SparseArray<Cell> getCells() {
        return cells;
    }

    public void updateCell(Cell cell,int position){
        cells.put(position,cell);
    }

    public Cell getCellById(int id){
        return cells.get(id);
    }

    public Cell getCell(int position) {
        return cells.get(position);
    }

    public Cell getCell(int row, int column){
        if(row > edge-1) throw new IndexOutOfBoundsException("Row "+row+" is out of range");
        if(column > edge-1) throw new IndexOutOfBoundsException("Column "+column+" is out of range");

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if(cell.row == row && cell.column == column)
                return cell;
        }
        return null;
    }
}
