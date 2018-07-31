package com.selfapps.a8queengame.model;

public class Player {
    private String name;
    private Color figureColor;
    private long gameTime =0;
    private int helpUsed = 0;
    private int removesUsed = 0;
    private int lifes = 0;
    private int turnsCount = 0;

    public Player(String name, Color figureColor) {
        this.name = name;
        this.figureColor = figureColor;
    }

    public Player(Color figureColor) {
        this.figureColor = figureColor;
        name = figureColor.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public int getHelpUsed() {
        return helpUsed;
    }

    public void increaseHelpUsed() {
        this.helpUsed++;
    }

    public int getRemovesUsed() {
        return removesUsed;
    }

    public void increaseRemoves() {
        this.removesUsed++;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getTurnsCount() {
        return turnsCount;
    }

    public void increaseTurnsCount() {
        this.turnsCount++;
    }

    public Color getFigureColor() {
        return figureColor;
    }


}
