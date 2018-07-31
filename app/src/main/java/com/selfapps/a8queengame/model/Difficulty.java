package com.selfapps.a8queengame.model;

import com.selfapps.a8queengame.R;

public class Difficulty {
    private int level;
    private int name;//resourceId
    private int help;
    private long timeLimit;
    private int removes;
    private int lifes;

    public Difficulty(int level) {
        this.level = level;
        initFields(level);
    }

    private void initFields(int level) {
        if (level == 0) {
            name = R.string.easy;
            help = Integer.MAX_VALUE;
            timeLimit = Long.MAX_VALUE;
            removes = Integer.MAX_VALUE;
            lifes = 4;

        } else if (level == 1) {
            name = R.string.normal;
            help = 3;
            timeLimit = Long.MAX_VALUE;
            removes = 3;
            lifes = 3;

        } else if (level == 2) {
            name = R.string.hard;
            help = 0;
            timeLimit = 120000;
            removes = 1;
            lifes = 2;

        } else if (level == 3) {
            name = R.string.profi;
            help = 0;
            timeLimit = 60000;
            removes = 0;
            lifes = 1;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getName() {
        return name;
    }

    public int getHelp() {
        return help;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public int getRemoves() {
        return removes;
    }

    public int getLifes() {
        return lifes;
    }

    public boolean isHelpAvailable(int used){
        return help - used > 0;
    }

    public boolean isRemoveAvailable(int used){
        return removes - used > 0;
    }

    public boolean isLifeAvailable(int used){
        return lifes - used >= 0;
    }

    public boolean isTimeValid(long played){
        return timeLimit - played > 0;
    }


    @Override
    public String toString() {
        return "Difficulty{" +
                "level=" + level +
                ", name=" + name +
                ", help=" + help +
                ", timeLimit=" + timeLimit +
                ", removes=" + removes +
                ", lifes=" + lifes +
                '}';
    }
}
