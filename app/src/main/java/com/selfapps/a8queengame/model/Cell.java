package com.selfapps.a8queengame.model;



public class Cell {
    public final int row;
    public final int column;
    public final int id;
    public final Color cellColor;
    private Figure figure;
    private CellStatus cellStatus = CellStatus.FREE;
    private int whiteBlockingCount = 0; //Number of times with the Figure blocking
    private int blackBlockingCount = 0; //Number of times with the Figure blocking

    public Cell(int id, int row, int column, Color color) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.cellColor = color;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        if(figure == null){
            this.figure = figure;
            cellStatus = CellStatus.FREE;
        }else
        if(cellStatus == CellStatus.FREE){
            this.figure = figure;
            setCellStatus(CellStatus.OCCUPIED);
        }else {
            throw  new UnsupportedOperationException("Figure can't set up on this field");
        }

    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    public int getBlockingCount(Color color) {
        return color == Color.WHITE? whiteBlockingCount : blackBlockingCount;
    }

    public int increaseBlockingCount(Color color) {
        return color == Color.WHITE? ++this.whiteBlockingCount : ++this.blackBlockingCount;
    }

    public int decreaseBlockingCount(Color color) {
       int count = color == Color.WHITE? --this.whiteBlockingCount : --this.blackBlockingCount;
       if(count < 0) increaseBlockingCount(color);

       return getBlockingCount(color);
    }

    public Color getCellColor() {
        return cellColor;
    }

}
