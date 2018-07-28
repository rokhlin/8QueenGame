package com.selfapps.a8queengame;

import android.util.Log;

public class GameUtils {

    public static boolean isBlack(int position,boolean moved) {
        if(position == 0) return false;
        else if(position == 1) return  true;
        else if(moved) return position % 2 != 0;
        else if(position >= 56) return isBlack(position+1,true);
        else if( 48 > position && position >= 40) return isBlack(position+1,true);
        else if( 32 > position && position >= 24) return isBlack(position+1,true);
        else if( 16 > position && position >= 8)  return isBlack(position+1,true);
        else return isBlack(position,true);
    }

    public static int getValue(int position, int[][] boardData) {
        int row = getRow(position);
        int column = getColumn(position);
        return boardData[row][column];
    }

    public static int getColumn(int position) {
        if (position == 0) return 0;
        Log.d("TAG", "for "+position+" getColumn= "+(position % 8));
        return position % 8;
    }

    public static int getRow(int position) {
        if (position == 0) return 0;
        Log.d("TAG", "for "+position+" getRow= "+(position / 8));
        return position / 8;
    }

    public static int getPosition(int row, int column){
        return row * 8 + column;
    }

    public static String getCellName(int position){
        char[] columnNames = {'a','b','c','d','e','f','g','h'};
        return "" + columnNames[getColumn(position)] + getRow(position);
    }

}
