package com.selfapps.a8queengame;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.GridView;

public interface GameContract {
    interface GameView extends MvpView{
        void notifyAdapter(int[][] newData);
        void startTimer();
        void stopTimer();
        CustomListAdapter initAdapter(GridView gridView, int[][] boarderField, int edge);
        void updateLog(String s);
        void updateLog(int textId);
        void updateQueenCount(int count);
        void showBackButton();
        GridView initBoardContainer(int columnWidth);
        DisplayMetrics getDisplayMetrics();
        String getStringFromId(int id);
        void showToast(int textId);
        void showToast(String text);
    }

    interface Presenter<V extends MvpView> extends MvpPresenter<V>{
        int getEdge(int height, int width);
        void turn(int position);
        void sayBlocked(int position);
        int[][] getData();
        void menuSelected(MenuItem item);
        void setElapsedTime(long elapsedTime);
        int getQueenCount();
        boolean addFigure(FigureType figureType,int position);
        void removeFigure(int position);
    }

    interface EightQueensGame extends MvpModel{
        int[][] getData();
        boolean addFigure(FigureType figureType,int position);
        void removeFigure(int position);
        int[][] initEmptyField();
        boolean checkGameOver();
        boolean checkWin();
        void startGame();
        void stopGame();
        void restartGame();
}
}
