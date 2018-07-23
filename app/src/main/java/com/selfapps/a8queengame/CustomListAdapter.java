package com.selfapps.a8queengame;

import android.content.Context;

import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class CustomListAdapter extends BaseAdapter {
    private int simpleEdge;
    private Context context;
    private int[][] boardData;
    private LayoutInflater mInflater;

    public CustomListAdapter(Context context, int[][] results, int edge) {
        this.context = context;
        boardData = results;
        mInflater = LayoutInflater.from(context);
        simpleEdge = edge/8;
    }

    public int getCount() {
        return 64;
    }

    public Object getItem(int position) {
        int row = GameUtils.getRow(position);
        int column = GameUtils.getColumn(position);
        return boardData[row][column];
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View  container = convertView;
        int cellValue = GameUtils.getValue(position,boardData);

        if (container == null) {
            container = mInflater.inflate(R.layout.listview_item, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(simpleEdge, simpleEdge);
            container.setLayoutParams(layoutParams);
        }

        ImageView imageview = container.findViewById(R.id.imageView);

        // Draw squares
        boolean isBlack = GameUtils.isBlack(position,false);
        if(isBlack)
            container.setBackground(ContextCompat.getDrawable(context,R.drawable.black_square));
        else
            container.setBackground(ContextCompat.getDrawable(context,R.drawable.white_square));

        // Put Figure on the board
        if ( cellValue > 9){
            imageview.setPadding(4,4,4,4);
            imageview.setImageResource(R.drawable.queen);
        }
        else if(MainActivity.needHelp){//enable help
            if(cellValue == 0) imageview.setImageResource(R.drawable.transparent);
            else imageview.setImageResource(R.drawable.transparent_help);
        }
        else
            imageview.setImageResource(R.drawable.transparent);

        return container;
    }


    public void notifyDataSetChanged(int[][] newData) {
        boardData = newData;
        super.notifyDataSetChanged();
    }


}