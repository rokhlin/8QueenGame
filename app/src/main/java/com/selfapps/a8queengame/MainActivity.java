package com.selfapps.a8queengame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_OF_SQUARES = 8;
    private static final int NUM_OF_QUEENS = 8;
    public static boolean needHelp = false;
    private CustomListAdapter adapter;
    private TextView queensCount,gameLog;
    private LinearLayout stat, returnLayout;
    private Button btnReturn;
    private Game game;

    private static final int margin = 0;
    int [][] boarderField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boarderField = initEmptyField();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        int edge = getEdge(height,width);

        game = new Game();
        GridView gridview = findViewById(R.id.boardLayout);
        gridview.setColumnWidth(edge/NUM_OF_SQUARES);

        stat = findViewById(R.id.ll_stat);
        returnLayout = findViewById(R.id.ll_rerurn);

        btnReturn = findViewById(R.id.btn_back);
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

        adapter = new CustomListAdapter(this, boarderField, edge);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                turn(position);
            }
        });

        updateQueenCount();
        updateLog(getString(R.string.game_started));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_help){
            if(needHelp)
                item.setTitle(getString(R.string.help));
            else
                item.setTitle(getString(R.string.hide_help));
            needHelp = !needHelp;
            adapter.notifyDataSetChanged(boarderField);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    private void updateLog(String s) {
        gameLog.setText(gameLog.getText()+"\n"+s);
    }

    public void updateQueenCount(){
        queensCount.setText(getQueenCount() +" "+ getString(R.string.queens_to_place));
    }

    private int getQueenCount() {
        return NUM_OF_QUEENS - game.getQueenCount();
    }

    private void turn(int position) {

        if(game.checkWin()){
            Toast.makeText(MainActivity.this, R.string.game_inished,
                    Toast.LENGTH_SHORT).show();
        }

        int value = GameUtils.getValue(position, boarderField);

        if(value == 1) {
            sayBlocked(position);
        }
        else if(value != 0){
            removeQueen(position);
        }
        else {
            if(addQueen(position)){
                updateLog(getString(R.string.you_win));
                showBackButton();
            }

        }
        adapter.notifyDataSetChanged(boarderField);
        updateQueenCount();

        if(checkGameOver()){
            updateLog( getString(R.string.game_over));
            showBackButton();
        }
    }

    private void showBackButton() {
        stat.setVisibility(View.GONE);
        returnLayout.setVisibility(View.VISIBLE);
    }

    private boolean checkGameOver() {
        int countFree = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(boarderField[i][j] == 0) countFree++;
            }
        }
        return countFree == 0;
    }

    private boolean addQueen(int position) {
        updateLog( getString(R.string.queen_on_cell) +" "+ GameUtils.getCellName(position));
        return game.addQueen(position, boarderField);
    }

    private void removeQueen(int position) {
        updateLog(getString(R.string.queen_removed));
        game.deleteQueen(position, boarderField);
    }

    private void sayBlocked(int position) {
        updateLog( getString(R.string.unable_turn) +" "+ GameUtils.getCellName(position));
        Toast.makeText(MainActivity.this, getString(R.string.unable_turn2) +" "+ GameUtils.getCellName(position),
                Toast.LENGTH_SHORT).show();
    }

    private int[][] initEmptyField() {
        int [][] field = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                field[i][j] = 0;
            }
        }
        return field;
    }

    private int getEdge(int height, int width) {
        int res = height > width? width - margin : height - margin;
        res = res - (res % NUM_OF_SQUARES);
        return res;
    }


}
