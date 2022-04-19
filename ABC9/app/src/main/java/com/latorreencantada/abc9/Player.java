package com.latorreencantada.abc9;

public class Player {

    String name;
    int bestScore;
    int userLevel;

    public Player(String name, int bestScore, int level) {
        this.name = name;
        this.bestScore = bestScore;
        this.userLevel = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setPlayerLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
