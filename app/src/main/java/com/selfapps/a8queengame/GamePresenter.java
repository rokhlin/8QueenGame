package com.selfapps.a8queengame;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.GridView;

public class GamePresenter implements GameContract.Presenter<GameContract.GameView> {
    private static final int NUM_OF_SQUARES = 8;
    private static final int NUM_OF_QUEENS = 8;
    public static boolean needHelp = false;
    private boolean isStarted = false;
    private long elapsedMillis;

    private GameContract.GameView view;
    private Game game;

    public GamePresenter() {
        this.game = new Game();
    }

    @Override
    public int getEdge(int height, int width) {
        int res = height > width? width : height ;
        res = res - (res % NUM_OF_SQUARES);
        return res;
    }


    @Override
    public void turn(int position) {
        if(!isStarted){
            view.startTimer();
            isStarted = true;
        }

        if(game.checkWin()) view.showToast(R.string.game_inished);

        int value = GameUtils.getValue(position, getData());//TODO move it from here

        if(value == 1) {
            sayBlocked(position);
        }
        else if(value != 0){
            game.removeFigure(position);
        }
        else {
            if(addFigure(FigureType.QUEEN,position)){
                view.stopTimer();
                view.updateLog(R.string.you_win);
                view.showBackButton();
            }

        }
        view.notifyAdapter(getData());
        view.updateQueenCount(getQueenCount());

        if(game.checkGameOver()){
            view.stopTimer();
            view.updateLog(R.string.game_over);
            view.showBackButton();
        }
    }

    @Override
    public boolean addFigure(FigureType type, int position) {
        view.updateLog( view.getStringFromId(R.string.queen_on_cell) +" "+ GameUtils.getCellName(position));
        return game.addFigure(type,position);
    }

    @Override
    public void removeFigure(int position) {
        view.updateLog(R.string.queen_removed);
        game.removeFigure(position);
    }

    @Override
    public void sayBlocked(int position) {
        view.updateLog( view.getStringFromId(R.string.unable_turn) +" "+ GameUtils.getCellName(position));
        view.showToast(view.getStringFromId(R.string.unable_turn2) +" "+ GameUtils.getCellName(position));
    }

    @Override
    public int[][] getData() {
        return game.getData();
    }

    @Override
    public void menuSelected(MenuItem item) {
        if(needHelp)
            item.setTitle(view.getStringFromId(R.string.help));
        else
            item.setTitle(view.getStringFromId(R.string.hide_help));
        needHelp = !needHelp;
        view.notifyAdapter(game.getData());
    }

    @Override
    public void setElapsedTime(long elapsedTime) {
        elapsedMillis = elapsedTime;
    }

    @Override
    public void attachView(GameContract.GameView mvpView) {
        view = mvpView;
    }

    @Override
    public void viewIsReady() {
        DisplayMetrics metrics = view.getDisplayMetrics();
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        int edge = getEdge(height,width);
        GridView gridView = view.initBoardContainer(edge / NUM_OF_SQUARES);

        view.initAdapter(gridView,game.getData(), edge);
        view.updateQueenCount(getQueenCount());
        view.updateLog(view.getStringFromId(R.string.game_started));
    }

    @Override
    public int getQueenCount() {
        return NUM_OF_QUEENS - game.getQueenCount();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() {
        detachView();
        //Clean Resources here
    }
}
