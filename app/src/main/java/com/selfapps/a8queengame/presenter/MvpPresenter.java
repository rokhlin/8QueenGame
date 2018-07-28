package com.selfapps.a8queengame.presenter;

import com.selfapps.a8queengame.view.MvpView;

public interface MvpPresenter<V extends MvpView> {
    void attachView(V mvpView);

    void viewIsReady();

    void detachView();

    void destroy();
}
