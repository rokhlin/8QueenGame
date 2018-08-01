package com.selfapps.a8queengame.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selfapps.a8queengame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RulesFragment extends Fragment {
    public static RulesFragment instance;



    public static RulesFragment getInstance(){
        if(instance==null){
            instance = new RulesFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules, container, false);
    }

}
