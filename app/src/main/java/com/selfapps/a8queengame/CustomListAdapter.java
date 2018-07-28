package com.selfapps.a8queengame;

import android.content.Context;

import android.support.v4.content.ContextCompat;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.selfapps.a8queengame.model.Cell;

import com.selfapps.a8queengame.model.CellStatus;
import com.selfapps.a8queengame.model.Color;
import com.selfapps.a8queengame.presenter.GamePresenter;


public class CustomListAdapter extends BaseAdapter {
    private int simpleEdge;
    private Context context;
    private SparseArray<Cell> cells;
    private LayoutInflater mInflater;

    public CustomListAdapter(Context context, SparseArray<Cell> results, int edge) {
        this.context = context;
        cells = results;
        mInflater = LayoutInflater.from(context);
        simpleEdge = edge/8;
    }

    public int getCount() {
        return 64;
    }//TODO remove hardcoded value

    public Object getItem(int position) {
        return cells.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View  container = convertView;
        Cell cell = cells.get(position);

        if (container == null) {
            container = mInflater.inflate(R.layout.listview_item, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(simpleEdge, simpleEdge);
            container.setLayoutParams(layoutParams);
        }

        ImageView imageview = container.findViewById(R.id.imageView);

        // Draw squares
        drawSquares(container,cell.cellColor);

        // Put Figure on the board
        drawFigures(imageview,cell);


        return container;
    }

    private void drawFigures(ImageView imageview, Cell cell) {
        if ( cell.getCellStatus() == CellStatus.OCCUPIED){
            imageview.setPadding(4,4,4,4);
            imageview.setImageResource(cell.getFigure().getImg());
        }
        else if(GamePresenter.needHelp){//enable help
            if(cell.getCellStatus().equals(CellStatus.FREE)) imageview.setImageResource(R.drawable.transparent);
            else imageview.setImageResource(R.drawable.transparent_help);//TODO If you need add help for different colors start here
        }
        else
            imageview.setImageResource(R.drawable.transparent);
    }

    private void drawSquares(View container, Color cellColor) {
        if(cellColor.equals(Color.BLACK))
            container.setBackground(ContextCompat.getDrawable(context,R.drawable.black_square));
        else
            container.setBackground(ContextCompat.getDrawable(context,R.drawable.white_square));
    }


    public void notifyDataSetChanged(SparseArray<Cell> newData) {
        cells = newData;
        super.notifyDataSetChanged();
    }


}