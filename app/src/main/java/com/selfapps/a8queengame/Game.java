package com.selfapps.a8queengame;

import android.util.SparseArray;

import com.selfapps.a8queengame.model.Action;
import com.selfapps.a8queengame.model.Cell;
import com.selfapps.a8queengame.model.CellStatus;
import com.selfapps.a8queengame.model.ChessBoard;
import com.selfapps.a8queengame.model.Color;
import com.selfapps.a8queengame.model.Difficulty;
import com.selfapps.a8queengame.model.EightQueenWinStrategy;
import com.selfapps.a8queengame.model.Figure;
import com.selfapps.a8queengame.model.FigureType;
import com.selfapps.a8queengame.model.Player;
import com.selfapps.a8queengame.presenter.GameContract;

import java.util.ArrayList;

public class Game implements GameContract.EightQueensGame {
    private ChessBoard board;
    private EightQueenWinStrategy gameStrategy;
    private int difficulty;


    public Game(int difficulty) {
        this.difficulty = difficulty;
        initEmptyField(difficulty);
    }
    

    @Override
    public SparseArray<Cell> getData() {
        return board.getCells();
    }

    @Override
    public boolean addFigure(Figure figure, int position) {
        setFigureOnCell(position,new Figure(FigureType.QUEEN, Color.BLACK));
        boolean isBlocking = true;
        markRow(Color.BLACK, position, isBlocking);
        markColumn(Color.BLACK,position, isBlocking);
        markDownLeft(Color.BLACK,position, isBlocking);
        markDownRight(Color.BLACK,position, isBlocking);
        markUpLeft(Color.BLACK,position, isBlocking);
        markUpRight(Color.BLACK,position, isBlocking);
        return checkWin();
    }

    @Override
    public void removeFigure(int position) {
        boolean isBlocking = false;
        markRow(Color.BLACK, position, isBlocking);
        markColumn(Color.BLACK,position, isBlocking);
        markDownLeft(Color.BLACK,position, isBlocking);
        markDownRight(Color.BLACK,position, isBlocking);
        markUpLeft(Color.BLACK,position, isBlocking);
        markUpRight(Color.BLACK,position, isBlocking);
        removeFigureFromCell(position);
    }


    private void removeFigureFromCell(int position) {
        Cell cell  = board.getCell(position);
        cell.setFigure(null);
        board.updateCell(cell,position);
        gameStrategy.updateData(null, Action.REMOVE);
    }

    @Override
    public SparseArray<Cell> initEmptyField(int difficulty) {
        board = new ChessBoard(8);
        gameStrategy = new EightQueenWinStrategy(new Difficulty(difficulty));
        return board.getCells();
    }

    @Override
    public boolean checkGameOver() {
        int countFree = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                int curPosition = GameUtils.getPosition(r,c);
                if(board.getCell(curPosition).getCellStatus() == CellStatus.FREE) countFree++;
            }
        }
        return countFree == 0;
    }

    public  boolean checkWin(){
        return gameStrategy.checkWin();
    }

    @Override
    public Difficulty getDifficulty() {
        return gameStrategy.getDifficulty();
    }

    @Override
    public void startGame() {
    }

    @Override
    public void stopGame() {

    }

    @Override
    public void restartGame() {
        initEmptyField(difficulty);
    }

    public void setFigureOnCell(int position, Figure figure) {
        Cell cell  = board.getCell(position);

        if(cell.getCellStatus()!= CellStatus.FREE){
            throw new UnsupportedOperationException("Cell is not free");
        }

        cell.setFigure(figure);
        board.updateCell(cell,position);
        gameStrategy.updateData(figure, Action.ADD);

    }

    public  void markRow(Color color, int position, boolean isBlocking) {
        int row = GameUtils.getRow(position);

        for (int column = 0; column < 8 ; column++) {
            int currentPos =GameUtils.getPosition(row,column);
            if(currentPos == position) continue;

            markFieldBlocked(currentPos,color,isBlocking);
        }
    }

    private void markFieldBlocked(int currentPos, Color color, boolean isBlocking) {
        Cell cell = board.getCell(currentPos);
        int count;
        if(isBlocking){
            count = cell.increaseBlockingCount(color);
        }else {
            count = cell.decreaseBlockingCount(color);
        }

        cell.setCellStatus(getCellStatus(color, isBlocking, count));
        board.updateCell(cell,currentPos);
    }

    private CellStatus getCellStatus(Color color, boolean isBlocking, int count) {
        if(!isBlocking && count == 0) return CellStatus.FREE;

        switch (color){
            case WHITE:
                return CellStatus.BLOCKED_BY_WHITES;
            default:
                return CellStatus.BLOCKED_BY_BLACKS;
        }
    }

    public  void markColumn(Color color, int position, boolean isBlocking) {
        int col = GameUtils.getColumn(position);

        for (int r = 0; r < 8 ; r++) {
            int currentPos =GameUtils.getPosition(r,col);
            if(currentPos == position) continue;

            markFieldBlocked(currentPos,color, isBlocking);
        }
    }

    public  void markDownRight(Color color, int position, boolean isBlocking) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);

        for(int r = row + 1,c = column + 1 ; r < 8 && c < 8 ; r++,c++){
            int currentPos =GameUtils.getPosition(r,c);
            if(currentPos == position) continue;

            markFieldBlocked(currentPos,color, isBlocking);
        }
    }

    public  void markUpRight(Color color, int position, boolean isBlocking) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);

        for(int r = row - 1,c = column + 1 ; r >= 0 && c < 8 ; r--,c++) {
            int currentPos =GameUtils.getPosition(r,c);
            if(currentPos == position) continue;

            markFieldBlocked(currentPos,color, isBlocking);
        }
    }

    public  void markUpLeft(Color color, int position, boolean isBlocking) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);

        for(int r = row - 1,c = column - 1 ; r >= 0 && c >= 0 ; r--,c--)  {
            int currentPos =GameUtils.getPosition(r,c);
            if(currentPos == position) continue;

            markFieldBlocked(currentPos,color, isBlocking);
        }
    }

    public void markDownLeft(Color color, int position, boolean isBlocking) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);

        for (int r = row + 1, c = column - 1; r < 8 && c >= 0; r++, c--) {
            int currentPos =GameUtils.getPosition(r,c);
            if(currentPos == position) continue;

            markFieldBlocked(currentPos,color, isBlocking);
        }
    }

    public int getQueenCount() {
        return ((ArrayList<Figure>)gameStrategy.getFigures()).size();
    }
}
