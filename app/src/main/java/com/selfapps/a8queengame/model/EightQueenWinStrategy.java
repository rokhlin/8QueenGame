package com.selfapps.a8queengame.model;

import java.util.ArrayList;

public class EightQueenWinStrategy implements WinStrategy {
    ArrayList<Figure> figures = new ArrayList<>();
    FigureType figureType = FigureType.QUEEN;
    int winCount = 8;

    @Override
    public void init() {

    }

    @Override
    public boolean checkWin() {
        return figures.size() == winCount;
    }

    @Override
    public boolean updateData(Figure figure, Action action ) {
        if (action == Action.ADD) {
            if(figure.getType() == figureType)
                figures.add(figure);
        } else {
            if(figures.size() > 1)
                figures.remove(figures.size() - 1);
        }

        return checkWin();
    }

    @Override
    public boolean updateTimer(long timer) {
        return false;
    }

    @Override
    public boolean updateTimer(long timer, Color color) {
        return false;
    }

    @Override
    public boolean updateData(int count, Color color) {
        return false;
    }

    @Override
    public Iterable<Figure> getFigures() {
        return figures;
    }
}
