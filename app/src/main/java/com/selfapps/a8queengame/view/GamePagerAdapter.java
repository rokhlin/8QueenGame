package com.selfapps.a8queengame.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class GamePagerAdapter extends FragmentPagerAdapter {

    public GamePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RulesFragment.getInstance();
            case 1:
                return ChessBoardFragment.getInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public static RulesFragment getRulesFragment(){
        return RulesFragment.getInstance();
    }

    public static ChessBoardFragment getChessBoardFragment(){
        return ChessBoardFragment.getInstance();
    }

}