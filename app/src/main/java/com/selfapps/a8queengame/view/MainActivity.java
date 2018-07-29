package com.selfapps.a8queengame.view;

import android.content.Intent;
import android.os.SystemClock;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.selfapps.a8queengame.CustomListAdapter;
import com.selfapps.a8queengame.Game;
import com.selfapps.a8queengame.model.Cell;
import com.selfapps.a8queengame.model.Figure;
import com.selfapps.a8queengame.presenter.GameContract;
import com.selfapps.a8queengame.presenter.GamePresenter;
import com.selfapps.a8queengame.R;

public class MainActivity extends AppCompatActivity implements GameContract.GameView {
    private CustomListAdapter adapter;
    private TextView queensCount,gameLog;
    private GridView gridview;
    private LinearLayout stat, returnLayout;
    private Game game;
    private Chronometer chronometer;
    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnReturn = findViewById(R.id.btn_back);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        queensCount = findViewById(R.id.tv_free_queens);
        gameLog = findViewById(R.id.tv_log);
        stat = findViewById(R.id.ll_stat);
        returnLayout = findViewById(R.id.ll_return);

        presenter = new GamePresenter();
        presenter.attachView(this);
        presenter.viewIsReady();

        chronometer = findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                presenter.setElapsedTime(SystemClock.elapsedRealtime()
                        - chronometer.getBase());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_help){
            presenter.menuSelected(item);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyAdapter(SparseArray<Cell> newData) {
        adapter.notifyDataSetChanged(newData);

    }

    @Override
    public void startTimer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    public void stopTimer() {
        chronometer.stop();
    }


    @Override
    public CustomListAdapter initAdapter(GridView gridView, SparseArray<Cell> cells, int edge) {
        adapter = new CustomListAdapter(this, cells, edge);
        gridView.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void updateLog(String s) {
        gameLog.setText(gameLog.getText()+"\n"+s);
    }

    @Override
    public void updateLog(int textId) {
        gameLog.setText(gameLog.getText()+"\n" + getString(textId));
    }

    @Override
    public void updateQueenCount(int count){
        queensCount.setText( count +" "+ getString(R.string.queens_to_place));
    }

    public void showBackButton() {
        stat.setVisibility(View.GONE);
        returnLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void blockBoard() {
        if(gridview != null){
            gridview.setEnabled(false);
        }
    }

    @Override
    public GridView initBoardContainer(int columnWidth) {
        gridview = findViewById(R.id.boardLayout);
        gridview.setColumnWidth(columnWidth);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                presenter.turn(position);
            }
        });
        return gridview;
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
}
