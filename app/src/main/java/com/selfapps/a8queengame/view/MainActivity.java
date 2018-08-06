package com.selfapps.a8queengame.view;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.selfapps.a8queengame.Constants;
import com.selfapps.a8queengame.CustomListAdapter;
import com.selfapps.a8queengame.model.Cell;
import com.selfapps.a8queengame.presenter.GameContract;
import com.selfapps.a8queengame.presenter.GamePresenter;
import com.selfapps.a8queengame.R;

import static com.selfapps.a8queengame.view.GamePagerAdapter.getChessBoardFragment;

public class MainActivity extends AppCompatActivity implements GameContract.GameView {
    private ViewPager mViewPager;
    private GamePagerAdapter gamePagerAdapter;
    public GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(gamePagerAdapter);


        Intent intent = getIntent();
        int difficulty = intent.getIntExtra(Constants.EXTRA_DIFFICULTY_LEVEL,1);

        presenter = new GamePresenter(difficulty);
        presenter.attachView(this);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.game_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.menu_help);
//        if(menuItem != null) presenter.checkHelpStatus(menuItem);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.menu_help){
//            presenter.menuSelected(item);
//            return true;
//        } else
//            return super.onOptionsItemSelected(item);
//    }


    @Override
    public void notifyAdapter(SparseArray<Cell> newData) {
        getChessBoardFragment().notifyAdapter(newData);
    }

    @Override
    public void startTimer() {
        getChessBoardFragment().startTimer();
    }

    @Override
    public void stopTimer() {
        getChessBoardFragment().stopTimer();
    }


    @Override
    public CustomListAdapter initAdapter(GridView gridView, SparseArray<Cell> cells, int edge) {
        return getChessBoardFragment().initAdapter(gridView,cells,edge);
    }

    @Override
    public void updateLog(String s) {
        getChessBoardFragment().updateLog(s);
    }

    @Override
    public void updateLog(int textId) {
        getChessBoardFragment().updateLog(textId);
    }

    @Override
    public void updateGameStat(String timer, String help, String removes) {
        getChessBoardFragment().updateGameStat(timer,help,removes);
    }

    @Override
    public void updateQueenCount(int count){
        getChessBoardFragment().updateQueenCount(count);
    }

    @Override
    public void showBackButton(String btnText, int imgRes, String message) {
        getChessBoardFragment().showBackButton(btnText,imgRes,message);
    }

    @Override
    public void blockBoard() {
        getChessBoardFragment().blockBoard();
    }

    @Override
    public GridView initBoardContainer(int columnWidth) {
        return getChessBoardFragment().initBoardContainer(columnWidth);
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    @Override
    public String getStringFromId(int id) {
        return getString(id);
    }

    @Override
    public void showToast(int textId) {
        Toast.makeText(MainActivity.this, textId,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(MainActivity.this, text,
                Toast.LENGTH_SHORT).show();
    }

    public void returnToStartActivity() {
        Intent intent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }

    public void setCurrentItem(int item){
        mViewPager.setCurrentItem(item);
    }
}
