package com.selfapps.a8queengame.presenter;

import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.GridView;

import com.selfapps.a8queengame.CustomListAdapter;
import com.selfapps.a8queengame.model.Cell;
import com.selfapps.a8queengame.model.Difficulty;
import com.selfapps.a8queengame.model.Figure;
import com.selfapps.a8queengame.model.FigureType;
import com.selfapps.a8queengame.model.MvpModel;
import com.selfapps.a8queengame.view.MvpView;

public interface GameContract {
    interface GameView extends MvpView {
        void notifyAdapter(SparseArray<Cell> newData);
        void startTimer();
        void stopTimer();
        CustomListAdapter initAdapter(GridView gridView, SparseArray<Cell> cells, int edge);
        void updateLog(String s);
        void updateLog(int textId);
        void updateGameStat(String timer, String help, String removes);
        void updateQueenCount(int count);
        void showBackButton(String btnText, int imgRes, String message);
        void blockBoard();
        GridView initBoardContainer(int columnWidth);
        DisplayMetrics getDisplayMetrics();
        String getStringFromId(int id);
        void showToast(int textId);
        void showToast(String text);
    }

    interface RulesView extends MvpView{

    }

    interface ChessBoardView extends MvpView{
        void notifyAdapter(SparseArray<Cell> newData);
        void startTimer();
        void stopTimer();
        CustomListAdapter initAdapter(GridView gridView, SparseArray<Cell> cells, int edge);
        void updateLog(String s);
        void updateLog(int textId);
        void updateGameStat(String timer, String help, String removes);
        void updateQueenCount(int count);
        void showBackButton(String btnText, int imgRes, String message);
        void blockBoard();
        GridView initBoardContainer(int columnWidth);

    }

    interface Presenter<V extends MvpView> extends MvpPresenter<V> {
        int getEdge(int height, int width);
        void turn(int position);
        void sayBlocked(int position);
        SparseArray<Cell> getData();
        void menuSelected(MenuItem item);
        void setElapsedTime(long elapsedTime);
        void checkHelpStatus(MenuItem item);
        int getQueenCount();
        boolean addFigure(FigureType figureType, int position);
        void removeFigure(int position);
        void updateGameStat();
    }

    interface EightQueensGame extends MvpModel {
        SparseArray<Cell> getData();
        boolean addFigure(Figure figure, int position);
        void removeFigure(int position);
        SparseArray<Cell> initEmptyField(int difficulty);
        boolean checkGameOver();
        boolean checkWin();
        Difficulty getDifficulty();
        void startGame();
        void stopGame();
        void restartGame();
}
}
