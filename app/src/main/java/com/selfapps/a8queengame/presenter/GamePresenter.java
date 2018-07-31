package com.selfapps.a8queengame.presenter;

import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.GridView;

import com.selfapps.a8queengame.Game;
import com.selfapps.a8queengame.GameUtils;
import com.selfapps.a8queengame.R;
import com.selfapps.a8queengame.model.Cell;
import com.selfapps.a8queengame.model.CellStatus;
import com.selfapps.a8queengame.model.Color;
import com.selfapps.a8queengame.model.Difficulty;
import com.selfapps.a8queengame.model.Figure;
import com.selfapps.a8queengame.model.FigureType;
import com.selfapps.a8queengame.model.Player;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GamePresenter implements GameContract.Presenter<GameContract.GameView> {
    private static final int NUM_OF_SQUARES = 8;
    private static final int NUM_OF_QUEENS = 8;
    public static boolean needHelp = false;
    private boolean isStarted = false;
    private boolean isFinished = false;
    private long elapsedMillis;
    private Player player;
    private MenuItem item;

    private GameContract.GameView view;
    private Game game;

    public GamePresenter(int difficulty) {
        this.game = new Game(difficulty);
        player = new Player(Color.BLACK);
    }

    @Override
    public int getEdge(int height, int width) {
        int res = height > width? width : height ;
        res = res - (res % NUM_OF_SQUARES);
        return res;
    }


    @Override
    public void turn(int position) {
        if(isFinished) {
            view.showToast(R.string.game_finished);
            return;
        }
        if(!isStarted){
            view.startTimer();
            isStarted = true;
        }

        if(needHelp){
            if(item!=null){
                item.setTitle(view.getStringFromId(R.string.help));
                needHelp = !needHelp;
                checkHelpStatus(item);

            }
        }

        if(game.checkWin()) view.showToast(R.string.game_finished);


        Cell cell = game.getData().get(position);//TODO move it from here

        if(cell.getCellStatus().equals(CellStatus.BLOCKED_BY_BLACKS)) {
            sayBlocked(position);
        }
        else if(cell.getCellStatus().equals(CellStatus.OCCUPIED)){
            if(game.getDifficulty().isRemoveAvailable(player.getRemovesUsed())){
                game.removeFigure(position);
                player.increaseRemoves();
                player.increaseTurnsCount();
            }else {
                view.showToast("You can't remove this figure.");
            }

        }
        else {
            if(addFigure(FigureType.QUEEN,position)){
                showWin();
            }
        }
        view.notifyAdapter(getData());
        view.updateQueenCount(getQueenCount());

        if(!isFinished && game.checkGameOver()){
            String message = getQueenCount() +" "+
                    view.getStringFromId(getQueenCount() <= 1? R.string.queen_single : R.string.queen_multiple)
                    +" "+
                    view.getStringFromId(getQueenCount() <= 1? R.string.queen_left_simple : R.string.queen_left_multiple);
            showGameOver(message);
        }

        updateGameStat();
    }

    @Override
    public void checkHelpStatus(MenuItem item) {
        if(!game.getDifficulty().isHelpAvailable(player.getHelpUsed()))
            //item.setEnabled(false);
            item.setVisible(false);
    }

    private void showWin() {
        isFinished = true;
        view.blockBoard();
        view.stopTimer();
        view.updateLog(R.string.you_win);
        view.showBackButton(view.getStringFromId(R.string.back),
                R.mipmap.winner,
                view.getStringFromId(R.string.congrats));
    }

    private void showGameOver(String message) {
        isFinished = true;
        view.stopTimer();
        view.updateLog(R.string.game_over);
        view.showBackButton(view.getStringFromId(R.string.return1),
                R.mipmap.game_over, message);
    }

    @Override
    public boolean addFigure(FigureType type, int position) {
        view.updateLog( view.getStringFromId(R.string.queen_on_cell) +" "+ GameUtils.getCellName(position));
        player.increaseTurnsCount();
        return game.addFigure(new Figure(type,Color.BLACK),position);
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
    public SparseArray<Cell> getData() {
        return game.getData();
    }

    @Override
    public void menuSelected(MenuItem item) {
        this.item = item;
        if(needHelp) {
            item.setTitle(view.getStringFromId(R.string.help));
        } else{
            player.increaseHelpUsed();
            item.setTitle(view.getStringFromId(R.string.hide_help));
        }

        needHelp = !needHelp;
        view.notifyAdapter(game.getData());
    }

    @Override
    public void setElapsedTime(long elapsedTime) {
        elapsedMillis = elapsedTime;
        player.setGameTime(elapsedTime);
        if(!game.getDifficulty().isTimeValid(player.getGameTime())){
            showGameOver(view.getStringFromId(R.string.time_expired));
        }
        updateGameStat();
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

    public void updateGameStat(){
        Difficulty difficulty =game.getDifficulty();
        String timer = difficulty.getTimeLimit() > 10000000? view.getStringFromId(R.string.unlimited) : getTime(difficulty.getTimeLimit());
        String helps = difficulty.getHelp() > 100?
                view.getStringFromId(R.string.unlimited) :
                (difficulty.getHelp() == 0 ?
                        view.getStringFromId(R.string.not_allowed) :
                        String.valueOf(difficulty.getHelp() - player.getHelpUsed()));
        String removes = difficulty.getRemoves() > 100?
                view.getStringFromId(R.string.unlimited) :
                (difficulty.getRemoves() == 0 ?
                        view.getStringFromId(R.string.not_allowed) :
                        String.valueOf(difficulty.getRemoves() - player.getRemovesUsed()));
        view.updateGameStat(timer,helps,removes);
    }

    private String getTime(long timeLimit) {
        long millis = timeLimit - elapsedMillis;
        String dateString = DateFormat.format("mm:ss", new Date(millis)).toString();
        return dateString;
    }
}
