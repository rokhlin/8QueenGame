package com.selfapps.a8queengame.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.selfapps.a8queengame.R;
import com.selfapps.a8queengame.model.Cell;
import com.selfapps.a8queengame.presenter.GameContract;
import com.selfapps.a8queengame.presenter.GamePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChessBoardFragment extends Fragment implements GameContract.ChessBoardView {
    private static ChessBoardFragment instance;
    private CustomListAdapter adapter;
    private TextView queensCount,gameLog, finish_message, timerInfo, helpInfo, removeInfo;
    private LinearLayout gameStatContainer;
    private ImageView finish_pic;
    private GridView gridview;
    private Button btnReturn, btnShowLog,btnHideLog ;
    private LinearLayout statQueenContainer, returnContainer, gameFinishContainer;
    private Chronometer chronometer;



    public static ChessBoardFragment getInstance(){
        if(instance==null){
            instance = new ChessBoardFragment();
        }
        return instance;
    }

    private MainActivity getMActivity(){
        return (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_chess_board, container, false);

        initFields(view);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMActivity().returnToStartActivity();

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




        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                getMActivity().presenter.setElapsedTime(SystemClock.elapsedRealtime()
                        - chronometer.getBase());
            }
        });

        getMActivity().presenter.viewIsReady();
        return view;
    }

    private void initFields(View view) {
        gridview = view.findViewById(R.id.boardLayout);
        chronometer = view.findViewById(R.id.chronometer);
        finish_pic = view.findViewById(R.id.iv_finish_picture);

        //TextViews
        finish_message = view.findViewById(R.id.tv_finish_message);
        queensCount = view.findViewById(R.id.tv_free_queens);
        removeInfo = view.findViewById(R.id.tv_removes_info);
        helpInfo = view.findViewById(R.id.tv_helps_info);
        timerInfo = view.findViewById(R.id.tv_timer_info);
        gameLog = view.findViewById(R.id.tv_log);

        //Layouts
        statQueenContainer = view.findViewById(R.id.ll_stat);
        returnContainer = view.findViewById(R.id.ll_return);
        gameFinishContainer = view.findViewById(R.id.ll_game_finish);
        gameStatContainer = view.findViewById(R.id.ll_game_stat);

        //Buttons
        btnReturn = view.findViewById(R.id.btn_back);
        btnShowLog = view.findViewById(R.id.btn_show_log);
        btnHideLog = view.findViewById(R.id.btn_hide_log);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.game_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_help);
        if(menuItem != null) getMActivity().presenter.checkHelpStatus(menuItem);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_help){
            getMActivity().presenter.menuSelected(item);
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
        adapter = new CustomListAdapter(getContext(), cells, edge);
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

        gridview.setColumnWidth(columnWidth);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                getMActivity().presenter.turn(position);
            }
        });
        return gridview;
    }
}
