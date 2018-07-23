package com.selfapps.a8queengame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int EMPTY = 0;
    private static final int BLOCKED = 1;
    private static final int NUM_OF_SQUARES = 8;

    private CustomListAdapter adapter;
    private TextView queensCount,gameLog;
    private LinearLayout stat, returnLayout;
    private Button btnReturn;
    private Game game;
    int height;
    int width;
    int edge;

    private static final int margin = 16;
    int [][] field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        field = initEmptyField();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        edge = getEdge(height,width);

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

        adapter = new CustomListAdapter(this, field, edge);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                turn(position);
            }
        });

        updateQueenCount();
        updateLog("Game started");
    }

    private void updateLog(String s) {
        gameLog.setText(gameLog.getText()+"\n"+s);
    }

    public void updateQueenCount(){
        queensCount.setText((8- game.getQueenCount())+" queens to place");
    }

    private void turn(int position) {

        if(game.checkWin()){
            Toast.makeText(MainActivity.this, "game is finished!!",
                    Toast.LENGTH_SHORT).show();
        }

        int value = game.getValue(position, field);

        if(value == 1) {
            sayBlocked(position);
        }
        else if(value != 0){
            removeQueen(position);
        }
        else {
            if(addQueen(position)){
                updateLog("You Win!!!");
                showBackButton();
            }

        }
        adapter.notifyDataSetChanged(field);
        updateQueenCount();

        if(checkGameOver()){
            updateLog( "Game over!!!");
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
                if(field[i][j] == 0) countFree++;
            }
        }
        return countFree == 0;
    }

    private boolean addQueen(int position) {
        int row = game.getRow(position);
        int column = game.getColumn(position);
        updateLog( "Queen on row = "+row+", column = "+column);
        return game.addQueen(position,field);
    }

    private void removeQueen(int position) {
        updateLog("Queen Removed");
        game.deleteQueen(position, field);
    }

    private void sayBlocked(int position) {
        int row = game.getRow(position);
        int column = game.getColumn(position);
        updateLog( "Unable set up on row = "+row+", column = "+column);
        Toast.makeText(MainActivity.this, "You can't put Queen here" + position,
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
