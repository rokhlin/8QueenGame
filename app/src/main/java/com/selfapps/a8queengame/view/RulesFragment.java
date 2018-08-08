package com.selfapps.a8queengame.view;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.selfapps.a8queengame.R;

import java.util.Locale;

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
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rules, container, false);

        WebView webView = (WebView) view.findViewById(R.id.web_view);
        webView.setBackgroundColor(R.drawable.transparent_bg);

        String locale = Locale.getDefault().getISO3Language();

        if(locale.equals("rus"))
            webView.loadUrl("https://queengame-1c283.firebaseapp.com/rus/index.html");
        else {
            webView.loadUrl("https://queengame-1c283.firebaseapp.com/index.html");
        }

        webView.setWebViewClient(new WebViewClient());
        //webView.setBackgroundColor(R.drawable.transparent_bg);
        webView.setBackgroundColor(0x00000000);
        webView.setWebChromeClient(new WebChromeClient());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMActivity().setCurrentItem(1);
            }
        });
        return view;
    }

    private MainActivity getMActivity(){
        return (MainActivity) getActivity();
    }


}
