package com.latorreencantada.abc9.Nivel;

public class Level {

    String[][] levelWords;

    public Level(String[][] levelWords){
        this.levelWords = levelWords;
    }

    public String[][] getLevelWords() {
        return levelWords;
    }

    public void setLevelWords(String[][] levelWords) {
        this.levelWords = levelWords;
    }
}
