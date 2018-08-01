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

public class MainActivity extends AppCompatActivity implements GameContract.GameView {
    private CustomListAdapter adapter;
    private TextView queensCount,gameLog, finish_message, timerInfo, helpInfo, removeInfo;
    private LinearLayout gameStatContainer;
    private ImageView finish_pic;
    private GridView gridview;
    private Button btnReturn, btnShowLog,btnHideLog ;
    private LinearLayout statQueenContainer, returnContainer, gameFinishContainer;
    private Chronometer chronometer;
    private GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra(Constants.EXTRA_DIFFICULTY_LEVEL,1);
        initFields();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnShowLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStatContainer.setVisibility(View.GONE);
                gameLog.setVisibility(View.VISIBLE);
                btnHideLog.setVisibility(View.VISIBLE);
                btnShowLog.setVisibility(View.GONE);

            }
        });

        btnHideLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStatContainer.setVisibility(View.VISIBLE);
                gameLog.setVisibility(View.GONE);
                btnHideLog.setVisibility(View.GONE);
                btnShowLog.setVisibility(View.VISIBLE);
            }
        });

        presenter = new GamePresenter(difficulty);
        presenter.attachView(this);
        presenter.viewIsReady();


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                presenter.setElapsedTime(SystemClock.elapsedRealtime()
                        - chronometer.getBase());
            }
        });
    }

    private void initFields() {
        chronometer = findViewById(R.id.chronometer);
        finish_pic = findViewById(R.id.iv_finish_picture);

        //TextViews
        finish_message = findViewById(R.id.tv_finish_message);
        queensCount = findViewById(R.id.tv_free_queens);
        removeInfo = findViewById(R.id.tv_removes_info);
        helpInfo = findViewById(R.id.tv_helps_info);
        timerInfo = findViewById(R.id.tv_timer_info);
        gameLog = findViewById(R.id.tv_log);

        //Layouts
        statQueenContainer = findViewById(R.id.ll_stat);
        returnContainer = findViewById(R.id.ll_return);
        gameFinishContainer = findViewById(R.id.ll_game_finish);
        gameStatContainer = findViewById(R.id.ll_game_stat);

        //Buttons
        btnReturn = findViewById(R.id.btn_back);
        btnShowLog = findViewById(R.id.btn_show_log);
        btnHideLog = findViewById(R.id.btn_hide_log);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_help);
        if(menuItem != null) presenter.checkHelpStatus(menuItem);
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
    public void updateGameStat(String timer, String help, String removes) {
        timerInfo.setText(getString(R.string.game_duration) +" "+timer);
        helpInfo.setText(getString(R.string.help_requests)+" "+help);
        removeInfo.setText(getString(R.string.fig_removes)+" "+removes);
    }

    @Override
    public void updateQueenCount(int count){
        queensCount.setText( count +" "+ getString(R.string.queens_to_place));
    }

    @Override
    public void showBackButton(String btnText, int imgRes, String message) {
        statQueenContainer.setVisibility(View.GONE);
        returnContainer.setVisibility(View.VISIBLE);
        btnReturn.setText(btnText);
        gameLog.setVisibility(View.GONE);
        btnHideLog.setVisibility(View.GONE);

        gameFinishContainer.setVisibility(View.VISIBLE);
        finish_pic.setImageResource(imgRes);
        finish_message.setText(message);
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
