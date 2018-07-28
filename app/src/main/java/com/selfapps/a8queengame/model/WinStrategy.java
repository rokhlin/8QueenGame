package com.selfapps.a8queengame.model;

interface WinStrategy {
    void init();
    boolean checkWin();
    boolean updateData(Figure figure,Action action);
    boolean updateTimer(long timer);
    boolean updateTimer(long timer,Color color);
    boolean updateData(int count, Color color);
    Iterable<Figure> getFigures();


}
